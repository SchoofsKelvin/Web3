package db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import domain.Product;

public class ProductRepositoryInDatabase implements ProductRepository {

	private Connection		con	= null;

	private Properties		properties;

	private final String	selectAllSyntax;
	private final String	selectAllSortedSyntax;
	private final String	selectSyntax;
	private final String	insertSyntax;
	private final String	updateSyntax;
	private final String	deleteSyntax;

	public ProductRepositoryInDatabase(Properties props) {
		properties = props;
		String scheme = props.getProperty("scheme_product");
		selectAllSyntax = "select * from " + scheme;
		selectAllSortedSyntax = "select * from " + scheme + " order by productid";
		selectSyntax = "select * from " + scheme + " where productid=?";
		insertSyntax = "insert into " + scheme + " values(?,?,?)";
		updateSyntax = "update " + scheme + " set description=?,price=? where productid=?";
		deleteSyntax = "delete from " + scheme + " where productid=?";
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

	public Product get(String productId) {
		try {
			PreparedStatement a = con.prepareStatement(selectSyntax);
			a.setString(1, productId);
			ResultSet set = a.executeQuery();
			if ( !set.next()) return null;
			String productid = set.getString("productid");
			String description = set.getString("description");
			Double price = set.getDouble("price");
			return new Product(productid, description, price);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Product> getAll() {
		connect();
		try {
			PreparedStatement a = con.prepareStatement(selectAllSyntax);
			ResultSet set = a.executeQuery();
			ArrayList<Product> list = new ArrayList<Product>();
			while (set.next()) {
				String productid = set.getString("productid");
				String description = set.getString("description");
				Double price = set.getDouble("price");
				list.add(new Product(productid, description, price));
			}
			return list;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Product> getAllSorted() {
		connect();
		try {
			PreparedStatement a = con.prepareStatement(selectAllSortedSyntax);
			ResultSet set = a.executeQuery();
			ArrayList<Product> list = new ArrayList<Product>();
			while (set.next()) {
				String productid = set.getString("productid");
				String description = set.getString("description");
				Double price = set.getDouble("price");
				list.add(new Product(productid, description, price));
			}
			return list;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void add(Product product) {
		connect();
		try {
			PreparedStatement a = con.prepareStatement(insertSyntax);
			a.setString(1, product.getProductid());
			a.setString(2, product.getDescription());
			a.setDouble(3, product.getPrice());
			if (a.executeUpdate() != 1)
				throw new RuntimeException("Didn't alter exactly 1 row");
		} catch (SQLException e) {
			if (e.getMessage() != null && e.getMessage().contains("duplicate key"))
				throw new RuntimeException(
					"A product with the given productid already exists");
			throw new RuntimeException(e);
		}
	}

	public void update(Product product) {
		connect();
		try {
			PreparedStatement a = con.prepareStatement(updateSyntax);
			a.setString(1, product.getProductid());
			a.setString(2, product.getDescription());
			a.setDouble(3, product.getPrice());
			if (a.executeUpdate() != 1)
				throw new RuntimeException("Didn't alter exactly 1 row");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void delete(String productId) {
		connect();
		try {
			PreparedStatement a = con.prepareStatement(deleteSyntax);
			a.setString(1, productId);
			if (a.executeUpdate() != 1)
				throw new RuntimeException("Didn't alter exactly 1 row");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void close() throws Throwable {
		con.close();
	}
}
