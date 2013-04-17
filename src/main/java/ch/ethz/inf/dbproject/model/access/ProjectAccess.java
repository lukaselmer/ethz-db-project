package ch.ethz.inf.dbproject.model.access;

import java.math.BigDecimal;
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

	private final int limit = 2;
	
	private final String sql_soonEndingProjects = 
			"SELECT * FROM project " +
			"WHERE end > NOW() " +
			"ORDER BY end ASC " +
			"LIMIT " + limit;
	
	private final String sql_mostFundedProjects = 
			"SELECT p.*, sum(a.amount) AS sum FROM fund f " +
			"LEFT JOIN (funding_amount a) ON (f.funding_amount_id = a.id) " +
			"LEFT JOIN (project p) ON (a.project_id = p.id) " +
			"GROUP BY p.id " +
			"ORDER BY sum DESC " +
			"LIMIT " + limit;
	
	private final String sql_mostPopularProjects = 
			"SELECT p.*, count(*) AS count FROM fund f " +
			"LEFT JOIN (funding_amount a) ON (f.funding_amount_id = a.id) " +
			"LEFT JOIN (project p) ON (a.project_id = p.id) " +
			"GROUP BY p.id " +
			"ORDER BY count DESC " +
			"LIMIT " + limit;
	
	private final String sql_searchProjectByTitle = 
			"SELECT * FROM project " +
			"WHERE title like ?";
	
	private final String sql_searchProjectByCity =
			"SELECT * FROM project " +
			"LEFT JOIN(city) ON (project.city_id = city.id) " +
			"WHERE city.name like ?";
			
	private final String sql_searchProjectByCategory = 
			"SELECT * FROM project " +
			"LEFT JOIN(category) ON (project.category_id = category.id) " +
			"WHERE category.name like ?";

	private final String sql_insertProject = 
			"INSERT INTO project " +
			"(user_id, city_id, category_id, title, description, goal, start, end) " +
			"VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	
	private final String sql_getProjectById = 
			"SELECT * FROM project " +
			"WHERE id = ?";
	
	private final String sql_getProjectsByCategory =
			"SELECT * FROM project " +
			"WHERE category_id = ?";
	

	private PreparedStatement pstmt_insertProject;
	private PreparedStatement pstmt_getProjectsByCategory;
	private PreparedStatement pstmt_getProjectById;
	private PreparedStatement pstmt_searchProjectByTitle;
	private PreparedStatement pstmt_searchProjectByCategory;
	private PreparedStatement pstmt_searchProjectByCity;
	
	protected void initStatements() throws SQLException {
		pstmt_insertProject = this.sqlConnection.getConnection().prepareStatement(sql_insertProject, Statement.RETURN_GENERATED_KEYS);
		pstmt_getProjectById = this.sqlConnection.getConnection().prepareStatement(sql_getProjectById);
		pstmt_getProjectsByCategory = this.sqlConnection.getConnection().prepareStatement(sql_getProjectsByCategory);
		pstmt_searchProjectByTitle = this.sqlConnection.getConnection().prepareStatement(sql_searchProjectByTitle);
		pstmt_searchProjectByCategory = this.sqlConnection.getConnection().prepareStatement(sql_searchProjectByCategory);
		pstmt_searchProjectByCity = this.sqlConnection.getConnection().prepareStatement(sql_searchProjectByCity);
	}
	
	public final int insertProject (final int user_id, final int city_id, final int category_id, final String title, final String description, final BigDecimal goal, final Date start,
			final Date end) {
		
		try {
			pstmt_insertProject.setInt(1, user_id);
			pstmt_insertProject.setInt(2, city_id);
			pstmt_insertProject.setInt(3, category_id);
			pstmt_insertProject.setString(4, title);
			pstmt_insertProject.setString(5, description);
			pstmt_insertProject.setBigDecimal(6, goal);
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
			final ResultSet rs = stmt.executeQuery(sql_soonEndingProjects);

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
			final ResultSet rs = stmt.executeQuery(sql_mostFundedProjects);

			while (rs.next()) {
				Project p = new Project(rs);
				p.setSum(rs.getBigDecimal("sum"));
				projects.add(p);
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
			final ResultSet rs = stmt.executeQuery(sql_mostPopularProjects);

			while (rs.next()) {
				Project p = new Project(rs);
				p.setCount(rs.getInt("count"));
				projects.add(p);
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
