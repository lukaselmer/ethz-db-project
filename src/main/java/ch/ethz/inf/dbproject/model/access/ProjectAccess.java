package ch.ethz.inf.dbproject.model.access;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import ch.ethz.inf.dbproject.model.Project;

public class ProjectAccess extends AbstractAccess {

	private static ProjectAccess instance = null;
	public static synchronized ProjectAccess getInstance() {
		if (instance == null) {
			instance = new ProjectAccess();
		}
		return instance;
	}

	private PreparedStatement pstmt_insertProject;
	
	private PreparedStatement pstmt_getProjectsByCategory;
	private PreparedStatement pstmt_getProjectById;
	
	private PreparedStatement pstmt_searchProjectByTitle;
	private PreparedStatement pstmt_searchProjectByCategory;
	private PreparedStatement pstmt_searchProjectByCity;

	protected void initStatements() throws SQLException {
		pstmt_insertProject = this.sqlConnection.getConnection().prepareStatement("insert into project (user_id, city_id, category_id, title, description, goal, start, end) values (?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
		
		pstmt_getProjectById = this.sqlConnection.getConnection().prepareStatement("select * from project where id = ?");
		pstmt_getProjectsByCategory = this.sqlConnection.getConnection().prepareStatement("select * from project where category_id = ?");
		
		pstmt_searchProjectByTitle = this.sqlConnection.getConnection().prepareStatement("select * from project where title like ?");
		pstmt_searchProjectByCategory = this.sqlConnection.getConnection().prepareStatement("select * from project left join(category) on (project.category_id = category.id) where category.name like ?");
		pstmt_searchProjectByCity = this.sqlConnection.getConnection().prepareStatement("select * from project left join(city) on (project.city_id = city.id) where city.name like ?");
	}
	
	public final int insertProject (final int user_id, final int city_id, final int category_id, final String title, final String description, final double goal, final Date start,
			final Date end) {
		
		try {
			pstmt_insertProject.setInt(1, user_id);
			pstmt_insertProject.setInt(2, city_id);
			pstmt_insertProject.setInt(3, category_id);
			pstmt_insertProject.setString(4, title);
			pstmt_insertProject.setString(5, description);
			pstmt_insertProject.setDouble(6, goal);
			pstmt_insertProject.setObject(7, new Timestamp(start.getTime()));
			pstmt_insertProject.setObject(8, new Timestamp(end.getTime()));
			
			pstmt_insertProject.execute();

		    ResultSet rs = pstmt_insertProject.getGeneratedKeys();
			rs.next();
			
			return rs.getInt(1);
			
		} catch (final SQLException ex) {
			ex.printStackTrace();
		}
		return -1;
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


	public final List<Project> getSoonEndingProjects() {
		final List<Project> projects = new ArrayList<Project>();
		try {
			final Statement stmt = this.sqlConnection.getConnection().createStatement();
			final ResultSet rs = stmt.executeQuery("select * from project where end > NOW() order by end asc limit 2");

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
	
	public final List<Project> getMostFundedProjects() {
		final List<Project> projects = new ArrayList<Project>();
		try {
			final Statement stmt = this.sqlConnection.getConnection().createStatement();
			final ResultSet rs = stmt.executeQuery("select project_id from fund left join (funding_amount) on (fund.funding_amount_id = funding_amount.id) group by project_id order by sum(amount) desc limit 2");

			while (rs.next()) {
				projects.add(getProjectById(rs.getInt("project_id")));
			}

			rs.close();
			stmt.close();
		} catch (final SQLException ex) {
			ex.printStackTrace();
		}

		return projects;

	}
	
	public final List<Project> getMostPopularProjects() {
		final List<Project> projects = new ArrayList<Project>();
		try {
			final Statement stmt = this.sqlConnection.getConnection().createStatement();
			final ResultSet rs = stmt.executeQuery("select project_id from fund left join (funding_amount) ON (fund.funding_amount_id = funding_amount.id) group by project_id order by count(*) desc limit 2");

			while (rs.next()) {
				projects.add(getProjectById(rs.getInt("project_id")));
			}

			rs.close();
			stmt.close();
		} catch (final SQLException ex) {
			ex.printStackTrace();
		}

		return projects;

	}
	
	public final List<Project> getProjectsByCategory (final int category_id) {
		final List<Project> projects = new ArrayList<Project>();

		try {

			pstmt_getProjectsByCategory.setInt(1, category_id);

			final ResultSet rs = pstmt_getProjectsByCategory.executeQuery();
			while (rs.next()) {
				projects.add(new Project(rs));
			}
			rs.close();
		} catch (final SQLException ex) {
			ex.printStackTrace();
		}
		return projects;
		
	}
	
	public final List<Project> searchProjectByCity (final String city) {
		final List<Project> projects = new ArrayList<Project>();

		try {

			pstmt_searchProjectByCity.setString(1, city);

			final ResultSet rs = pstmt_searchProjectByCity.executeQuery();
			while (rs.next()) {
				projects.add(new Project(rs));
			}
			rs.close();
		} catch (final SQLException ex) {
			ex.printStackTrace();
		}
		return projects;
	}

	public final List<Project> searchProjectByCategory (final String category) {
		final List<Project> projects = new ArrayList<Project>();

		try {

			pstmt_searchProjectByCategory.setString(1, category);

			final ResultSet rs = pstmt_searchProjectByCategory.executeQuery();
			while (rs.next()) {
				projects.add(new Project(rs));
			}
			rs.close();
		} catch (final SQLException ex) {
			ex.printStackTrace();
		}
		return projects;
	}
	
	public final List<Project> searchProjectByTitle (final String title) {
		final List<Project> projects = new ArrayList<Project>();

		try {

			pstmt_searchProjectByTitle.setString(1, title);

			final ResultSet rs = pstmt_searchProjectByTitle.executeQuery();
			while (rs.next()) {
				projects.add(new Project(rs));
			}
			rs.close();
		} catch (final SQLException ex) {
			ex.printStackTrace();
		}
		return projects;
	}
	

}
