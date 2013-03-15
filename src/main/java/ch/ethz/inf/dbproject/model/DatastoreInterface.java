package ch.ethz.inf.dbproject.model;

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
 * This class should be the interface between the web application
 * and the database. Keeping all the data-access methods here
 * will be very helpful for part 2 of the project.
 */
public final class DatastoreInterface {

	static {
		try {
			// TODO make this better?
			if(DatabaseHelper.getTables().size() == 0 || DatabaseHelper.getCount("projects") == 0)
			DatabaseSeeder.resetAndSeed();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InvalidStateException e) {
			e.printStackTrace();
		}
	}
	
	private MySQLConnection sqlConnection;

	public DatastoreInterface() {
		this.sqlConnection = MySQLConnection.getInstance();
	}
	
	public final Project getProjectById(final int id) {
		Project p = null;
		
		try {
			final Statement stmt = this.sqlConnection.getConnection().createStatement();
			final ResultSet rs = stmt.executeQuery("select * from projects where id = " + id);

			if (rs.next())
				p = new Project(rs);

			rs.close();
			stmt.close();

		} catch (final SQLException ex) {
			ex.printStackTrace();
		}
		return p;
	}
	
	public final List<Project> getAllProjects() {
		final List<Project> projects = new ArrayList<Project>(); 
		try {
			final Statement stmt = this.sqlConnection.getConnection().createStatement();
			final ResultSet rs = stmt.executeQuery("select * from projects");
		
			while (rs.next()) {
				projects.add(new Project(rs));
			}
			
			rs.close();
			stmt.close();
		} catch (final SQLException ex) {			
			ex.printStackTrace();		
		}
		
		return projects;
		
		
		
		// If you chose to use PreparedStatements instead of statements, you should prepare them in the constructor of DatastoreInterface.
		
	}
	
	//TODO Implement all missing data access methods

}