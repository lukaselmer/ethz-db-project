package ch.ethz.inf.dbproject.model;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import ch.ethz.inf.dbproject.database.MySQLConnection;

/**
 * This class should be the interface between the web application
 * and the database. Keeping all the data-access methods here
 * will be very helpful for part 2 of the project.
 */
public final class DatastoreInterface {

	//FIXME This is a temporary list of projects that will be displayed until all the methods have been properly implemented
	private final static Project[] staticProjects = new Project[] { 
			new Project(0, "High-End Server", "1287.9%", 10000), 
			new Project(1, "The Next MacBook Air", "54.7%", 250000),
			new Project(2, "New Research Lab", "1.2%", 1000000),
			new Project(3, "Death Star", "0.0%", 1000000000),
		};
	private final static List<Project> staticProjectList = new ArrayList<Project>();
	static {
		for (int i = 0; i < staticProjects.length; i++) {
			staticProjectList.add(staticProjects[i]);
		}
	}
	
	@SuppressWarnings("unused")
	private Connection sqlConnection;

	public DatastoreInterface() {
		//TODO Uncomment the following line once MySQL is running
		//this.sqlConnection = MySQLConnection.getInstance().getConnection();
	}
	
	public final Project getProjectById(final int id) {
	
		/**
		 * TODO this method should return the project with the given id
		 */
		
		if (id < staticProjects.length) {
			return staticProjects[id];
		} else {
			return null;
		}
		
	}
	
	public final List<Project> getAllProjects() {

		/**
		 * TODO this method should return all the projects in the database
		 */
			
		/*
		//Code example for DB access
		try {
			
			final Statement stmt = this.sqlConnection.createStatement();
			final ResultSet rs = stmt.executeQuery("Select ...");
		
			final List<Project> projects = new ArrayList<Project>(); 
			while (rs.next()) {
				projects.add(new Project(rs));
			}
			
			rs.close();
			stmt.close();

			return projects;
			
		} catch (final SQLException ex) {			
			ex.printStackTrace();
			return null;			
		}
		
		*/
		
		// If you chose to use PreparedStatements instead of statements, you should prepare them in the constructor of DatastoreInterface.
		
		// For the time being, we return some bogus projects
		return staticProjectList;
	}
	
	//TODO Implement all missing data access methods


}
