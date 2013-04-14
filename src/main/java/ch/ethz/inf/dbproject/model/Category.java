package ch.ethz.inf.dbproject.model;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Object that represents a category of project (i.e. Art, Music...) 
 */
public final class Category implements ComboInterface {

	private final int id;
	private final String name;

	public Category(final int id, final String name) {
		super();
		this.id = id;
		this.name = name;
	}
	

	public Category (final ResultSet rs) throws SQLException {
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
