package db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import domain.Person;
import domain.Role;

public class PersonRepositoryInDatabase implements PersonRepository {

	private Connection		con	= null;

	private Properties		properties;

	private final String	selectAllSyntax;
	private final String	selectSyntax;
	private final String	insertSyntax;
	private final String	updateSyntax;
	private final String	deleteSyntax;

	public PersonRepositoryInDatabase(Properties props) {
		properties = props;
		String scheme = props.getProperty("scheme_person");
		selectAllSyntax = "select * from " + scheme;
		selectSyntax = "select * from " + scheme + " where userid=?";
		insertSyntax = "insert into " + scheme + " values(?,?,?,?,?,?,?,?)";
		updateSyntax = "update " + scheme
			+ " set email=?,firstname=?,lastname=?,password=?,salt=?,role=?,length=? where userid=?";
		deleteSyntax = "delete from " + scheme + " where userid=?";
		connect();
	}

	public void connect() {
		try {
			if (con != null && con.isValid(3)) return;
			Class.forName("org.postgresql.Driver");
			String url = properties.getProperty("url");
			con = DriverManager.getConnection(url, properties);
			System.out.println("Got database connection");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Person get(String personId) {
		try {
			PreparedStatement a = con.prepareStatement(selectSyntax);
			a.setString(1, personId);
			ResultSet set = a.executeQuery();
			if ( !set.next()) return null;
			String userid = set.getString("userid");
			String email = set.getString("email");
			String password = set.getString("password");
			String firstName = set.getString("firstName");
			String lastName = set.getString("lastName");
			String salt = set.getString("salt");
			Role role = Role.valueOf(set.getString("role"));
			int length = set.getInt("length");
			return new Person(userid, email, password, firstName, lastName, length, role, salt,
				true);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Person> getAll() {
		connect();
		try {
			PreparedStatement a = con.prepareStatement(selectAllSyntax);
			ResultSet set = a.executeQuery();
			ArrayList<Person> list = new ArrayList<Person>();
			while (set.next()) {
				String userid = set.getString("userid");
				String email = set.getString("email");
				String password = set.getString("password");
				String firstName = set.getString("firstName");
				String lastName = set.getString("lastName");
				String salt = set.getString("salt");
				Role role = Role.valueOf(set.getString("role"));
				int length = set.getInt("length");
				list.add(new Person(userid, email, password, firstName, lastName, length, role,
					salt, true));
			}
			return list;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void add(Person person) {
		connect();
		try {
			PreparedStatement a = con.prepareStatement(insertSyntax);
			a.setString(1, person.getUserid());
			a.setString(2, person.getEmail());
			a.setString(3, person.getFirstName());
			a.setString(4, person.getLastName());
			a.setString(5, person.getPasswordHashed());
			a.setString(6, person.getSalt());
			a.setString(7, person.getRole().name());
			a.setInt(8, person.getLength());
			if (a.executeUpdate() != 1)
				throw new RuntimeException("Didn't alter exactly 1 row");
		} catch (SQLException e) {
			if (e.getMessage() != null && e.getMessage().contains("duplicate key"))
				throw new RuntimeException("A person with the given userid already exists");
			throw new RuntimeException(e);
		}
	}

	@Override
	public void update(Person person) {
		connect();
		try {
			PreparedStatement a = con.prepareStatement(updateSyntax);
			a.setString(1, person.getEmail());
			a.setString(2, person.getFirstName());
			a.setString(3, person.getLastName());
			a.setString(4, person.getPasswordHashed());
			a.setString(5, person.getSalt());
			a.setString(6, person.getRole().name());
			a.setInt(7, person.getLength());
			a.setString(8, person.getUserid());
			if (a.executeUpdate() != 1)
				throw new RuntimeException("Didn't alter exactly 1 row");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void delete(String personId) {
		connect();
		try {
			PreparedStatement a = con.prepareStatement(deleteSyntax);
			a.setString(1, personId);
			if (a.executeUpdate() != 1)
				throw new RuntimeException("Didn't alter exactly 1 row");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void close() throws Throwable {
		con.close();
	}
}
