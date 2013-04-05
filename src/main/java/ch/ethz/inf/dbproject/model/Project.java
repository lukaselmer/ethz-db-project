package ch.ethz.inf.dbproject.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public final class Project {

	// TODO memeber for foreignkeys (category, city, user)
	private final int id;
	private final int user_id;
	private final String title;
	private final String description;
	private final double goal;
	private final Date start;
	private final Date end;


	/**
	 * Construct a new project.
	 * 
	 * @param name
	 *            The name of the project
	 */
	public Project(final int id, final int user_id, final String title, final String description, final double goal, final Date start,
			final Date end) {
		this.id = id;
		this.user_id = user_id;
		this.title = title;
		this.description = description;
		this.goal = goal;
		this.start = start;
		this.end = end;
	}

	public Project(final ResultSet rs) throws SQLException {
		this.id = rs.getInt("id");
		this.user_id = rs.getInt("user_id");
		this.title = rs.getString("title");
		this.description = rs.getString("description");
		this.goal = rs.getDouble("goal");
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

	public double getGoal() {
		return goal;
	}

	public int getUser_id() {
		return user_id;
	}
	
	public String getOwner(){
		return new DatastoreInterface().getUserById(user_id).getName();
	}

}