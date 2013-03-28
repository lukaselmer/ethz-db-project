package ch.ethz.inf.dbproject.model;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Object that represents a registered in user.
 */
public final class User {

	private final int id;
	private final String name;
	private final String password;

	public User(final int id, final String name, final String password) {
		this.id = id;
		this.name = name;
		this.password = password;
	}

	public User(final ResultSet rs) throws SQLException {
		this.id = rs.getInt("id");
		this.name = rs.getString("name");
		this.password = rs.getString("password");
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

}
