package ch.ethz.inf.dbproject.model;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class represents a City
 */
public final class City {
	private final int id;
	private final String name;

	public City(final int id, final String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public City(final ResultSet rs) throws SQLException {
		this.id = rs.getInt("id");
		this.name = rs.getString("name");
	}

	public final String getName() {
		return name;
	}

	public int getId() {
		return id;
	}
}