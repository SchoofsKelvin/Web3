package domain;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Person {

	private String	userid;
	private String	email;
	private String	password;
	private String	firstName;
	private String	lastName;
	private int		length;
	private String	salt;
	private Role	role;

	public Person(String userid, String email, String password, String firstName,
		String lastName, int length, Role role, String salt) {
		setSalt(salt);
		setUserid(userid);
		setEmail(email);
		setPassword(password);
		setFirstName(firstName);
		setLastName(lastName);
		setLength(length);
		setRole(role);
	}

	public Person(String userid, String email, String password, String firstName,
		String lastName, int length, Role role) {
		this(userid, email, password, firstName, lastName, length, role, generateSalt());
	}

	private static String generateSalt() {
		byte[] bytes = new byte[32];
		SecureRandom r = new SecureRandom();
		for (int i = 0; i < 32; i++) {
			bytes[i] = (byte) (65 + r.nextInt(26));
		}
		return new String(bytes);
	}

	public Person() {}

	public Person(String userid, String email, String password, String firstName,
		String lastName, int length, Role role, String salt, boolean hashed) {
		this(userid, email, password, firstName, lastName, length, role, salt);
		if (hashed) {
			setPasswordHashed(password);
		}
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		if (userid == null || userid.isEmpty())
			throw new IllegalArgumentException("No userid given");
		this.userid = userid;
	}

	public void setEmail(String email) {
		if (email == null || email.isEmpty())
			throw new IllegalArgumentException("No email given");
		String USERID_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		Pattern p = Pattern.compile(USERID_PATTERN);
		Matcher m = p.matcher(email);
		if ( !m.matches()) throw new IllegalArgumentException("Email not valid");
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public boolean isCorrectPassword(String password) {
		if (password == null || password.isEmpty())
			throw new IllegalArgumentException("No password given");
		return getPasswordHashed().equals(hashPassword(password));
	}

	public void setPassword(String password) {
		if (password == null || password.isEmpty())
			throw new IllegalArgumentException("No password given");
		setPasswordHashed(hashPassword(password));
	}

	public void setPasswordHashed(String password) {
		if (password == null || password.isEmpty())
			throw new IllegalArgumentException("No password given");
		this.password = password;
	}

	public String getPasswordHashed() {
		return password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		if (firstName == null || firstName.isEmpty())
			throw new IllegalArgumentException("No firstname given");
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		if (lastName == null || lastName.isEmpty())
			throw new IllegalArgumentException("No last name given");
		this.lastName = lastName;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		if (length < 0) throw new IllegalArgumentException("Length can't be negative");
		this.length = length;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		if (salt == null || salt.isEmpty())
			throw new IllegalArgumentException("No salt given");
		this.salt = salt;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		if (role == null) throw new IllegalArgumentException("No role given");
		this.role = role;
	}

	private String hashPassword(String pass) {
		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(salt.getBytes());
			crypt.update(pass.getBytes());
			return new BigInteger(crypt.digest()).toString(16);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean equals(Object obj) {
		return obj.getClass().equals(getClass())
			&& ((Person) obj).getUserid().equals(getUserid());
	}
}
