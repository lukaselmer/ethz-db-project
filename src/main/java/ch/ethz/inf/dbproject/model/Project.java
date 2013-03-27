package ch.ethz.inf.dbproject.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public final class Project {

	private final int id;
	private final String title;
	private final String description;
	private final Date start;
	private final Date end;

	/**
	 * Construct a new project.
	 * 
	 * @param name
	 *            The name of the project
	 */
	public Project(final int id, final String title, final String description, final Date start, final Date end) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.start = start;
		this.end = end;
	}

	public Project(final ResultSet rs) throws SQLException {
		this.id = rs.getInt("id");
		this.title = rs.getString("title");
		this.description = rs.getString("description");
		this.start = rs.getDate("start");
		this.end = rs.getDate("end");
	}

	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public Date getStart() {
		return start;
	}

	public Date getEnd() {
		return end;
	}

}