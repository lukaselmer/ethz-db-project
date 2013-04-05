package ch.ethz.inf.dbproject.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ch.ethz.inf.dbproject.database.DatabaseHelper;
import ch.ethz.inf.dbproject.database.DatabaseSeeder;
import ch.ethz.inf.dbproject.database.MySQLConnection;
import ch.ethz.inf.dbproject.exceptions.InvalidStateException;

/**
 * This class should be the interface between the web application and the
 * database. Keeping all the data-access methods here will be very helpful for
 * part 2 of the project.
 */
public final class DatastoreInterface {

	static {
		try {
			try {
				// TODO make this better?
				if (DatabaseHelper.getTables().size() == 0 || DatabaseHelper.getCount("project") == 0)
					DatabaseSeeder.resetAndSeed();
			} catch (SQLException e) {
				e.printStackTrace();
				DatabaseSeeder.resetAndSeed();
			} catch (InvalidStateException e) {
				e.printStackTrace();
				DatabaseSeeder.resetAndSeed();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private MySQLConnection sqlConnection;
	private PreparedStatement pstmt_getCommentsOfProject;
	private PreparedStatement pstmt_getProjectById;
	private PreparedStatement pstmt_getUserById;

	
	//dont get it why all prepared statements should be in the cTor.. but was a hint in this file..
	public DatastoreInterface() {
		this.sqlConnection = MySQLConnection.getInstance();
		try {
			pstmt_getProjectById = this.sqlConnection.getConnection().prepareStatement("select * from project where id = ?");
			pstmt_getCommentsOfProject = this.sqlConnection.getConnection().prepareStatement("select * from comment where project_id = ?");
			pstmt_getUserById = this.sqlConnection.getConnection().prepareStatement("select * from user where id = ?");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public final Project getProjectById(final int id) {
		Project p = null;

		try {
			pstmt_getProjectById.setInt(1, id);
			final ResultSet rs = pstmt_getProjectById.executeQuery();
			if (rs.next())
				p = new Project(rs);
			rs.close();
		} catch (final SQLException ex) {
			ex.printStackTrace();
		}
		return p;
	}

	public final User getUserById(final int id) {
		User u = null;

		try {
			pstmt_getUserById.setInt(1, id);
			ResultSet rs = pstmt_getUserById.executeQuery();
			if (rs.next())
				u = new User(rs);
			rs.close();
		} catch (final SQLException ex) {
			ex.printStackTrace();
		}
		return u;
	}

	public final List<Project> getAllProjects() {
		final List<Project> projects = new ArrayList<Project>();
		try {
			final Statement stmt = this.sqlConnection.getConnection().createStatement();
			final ResultSet rs = stmt.executeQuery("select * from project");

			while (rs.next()) {
				projects.add(new Project(rs));
			}

			rs.close();
			stmt.close();
		} catch (final SQLException ex) {
			ex.printStackTrace();
		}

		return projects;

	}

	public final List<Comment> getCommentsByProjectId(final int id) {
		final List<Comment> comments = new ArrayList<Comment>();

		try {

			pstmt_getCommentsOfProject.setInt(1, id);

			final ResultSet rs = pstmt_getCommentsOfProject.executeQuery();
			while (rs.next()) {
				comments.add(new Comment(rs));
			}
			rs.close();
		} catch (final SQLException ex) {
			ex.printStackTrace();
		}
		return comments;
	}

}
