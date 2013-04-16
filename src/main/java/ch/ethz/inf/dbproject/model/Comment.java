package ch.ethz.inf.dbproject.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import ch.ethz.inf.dbproject.model.access.UserAccess;

/**
 * Object that represents a user comment.
 */
public class Comment {
	
	private final int id;
	private final int user_id;
	private final int project_id;
	private final String text;
	private final Date date;
	
	
	public Comment(final int id, final int user_id, final int project_id, final String text, final Date date) {
		this.id = id;
		this.user_id = user_id;
		this.project_id = project_id;
		this.text = text;
		this.date = date;
		
	}
	
	public Comment(final ResultSet rs) throws SQLException {
		this.id =  rs.getInt("id");
		this.user_id = rs.getInt("user_id");
		this.project_id = rs.getInt("project_id");
		this.text =  rs.getString("text");
		this.date = rs.getDate("date");
	}

	public String getText() {
		return text;
	}

	public int getId() {
		return id;
	}

	public Date getDate() {
		return date;
	}

	public int getUser_id() {
		return user_id;
	}

	public int getProject_id() {
		return project_id;
	}	
	
	public String getUsername(){
		return UserAccess.getInstance().getUserById(user_id).getName();
	}
}
