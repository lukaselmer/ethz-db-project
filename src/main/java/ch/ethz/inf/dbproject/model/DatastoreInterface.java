package ch.ethz.inf.dbproject.model;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
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
	private PreparedStatement pstmt_insertProject;
	private PreparedStatement pstmt_insertComment;
	private PreparedStatement pstmt_insertFund;
	
	private PreparedStatement pstmt_getCommentsOfProject;
	private PreparedStatement pstmt_getProjectsByCategory;
	private PreparedStatement pstmt_getProjectById;
	private PreparedStatement pstmt_getUserById;
	private PreparedStatement pstmt_getUser;
	private PreparedStatement pstmt_getFundingAmountsOfProject;

	private PreparedStatement pstmt_searchProjectByTitle;
	private PreparedStatement pstmt_searchProjectByCategory;
	private PreparedStatement pstmt_searchProjectByCity;

	
	//dont get it why all prepared statements should be in the cTor.. but was a hint in this file..
	public DatastoreInterface() {
		this.sqlConnection = MySQLConnection.getInstance();
		try {
			pstmt_insertFund = this.sqlConnection.getConnection().prepareStatement("insert into fund (user_id, funding_amount_id) values (?, ?)");
			pstmt_insertComment = this.sqlConnection.getConnection().prepareStatement("insert into comment (user_id, project_id, text, date) values (?, ?, ?, NOW())");
			pstmt_insertProject = this.sqlConnection.getConnection().prepareStatement("insert into project (user_id, city_id, category_id, title, description, goal, start, end) values (?, ?, ?, ?, ?, ?, ?, ?)");
			
			pstmt_getProjectById = this.sqlConnection.getConnection().prepareStatement("select * from project where id = ?");
			pstmt_getProjectsByCategory = this.sqlConnection.getConnection().prepareStatement("select * from project where category_id = ?");
			pstmt_getFundingAmountsOfProject = this.sqlConnection.getConnection().prepareStatement("select * from funding_amount where project_id = ?");
			pstmt_getCommentsOfProject = this.sqlConnection.getConnection().prepareStatement("select * from comment where project_id = ?");
			pstmt_getUserById = this.sqlConnection.getConnection().prepareStatement("select * from user where id = ?");
			pstmt_getUser = this.sqlConnection.getConnection().prepareStatement("select * from user where name = ? and password = ?");

			pstmt_searchProjectByTitle = this.sqlConnection.getConnection().prepareStatement("select * from project where title like ?");
			pstmt_searchProjectByCategory = this.sqlConnection.getConnection().prepareStatement("select * from project left join(category) on (project.category_id = category.id) where category.name like ?");
			pstmt_searchProjectByCity = this.sqlConnection.getConnection().prepareStatement("select * from project left join(city) on (project.city_id = city.id) where city.name like ?");
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
	
	
	public final void insertFund (final int user_id, final int funding_amount_id) {
		
		try {
			pstmt_insertFund.setInt(1, user_id);
			pstmt_insertFund.setInt(2, funding_amount_id);
			
			pstmt_insertFund.execute();
			
			// TODO: error-handling
		} catch (final SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	
	public final void insertComment (final int user_id, final int project_id, final String text) {
		
		try {
			pstmt_insertComment.setInt(1, user_id);
			pstmt_insertComment.setInt(2, project_id);
			pstmt_insertComment.setString(3, text);
			
			pstmt_insertComment.execute();
			
			// TODO: error-handling
		} catch (final SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public final void insertProject (final int user_id, final int city_id, final int category_id, final String title, final String description, final double goal, final Date start,
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
			
			// TODO: error-handling
		} catch (final SQLException ex) {
			ex.printStackTrace();
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

	public final List<FundingAmount> getFundingAmountsOfProject (final int project_id) {

		final List<FundingAmount> funding_amounts = new ArrayList<FundingAmount>();

		try {

			pstmt_getFundingAmountsOfProject.setInt(1, project_id);

			final ResultSet rs = pstmt_getFundingAmountsOfProject.executeQuery();
			while (rs.next()) {
				funding_amounts.add(new FundingAmount(rs));
			}
			rs.close();
		} catch (final SQLException ex) {
			ex.printStackTrace();
		}
		return funding_amounts;
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
	
	public User getUser (final String name, final String password) {
		User u = null;
		
		try {
			pstmt_getUser.setString(1, name);
			pstmt_getUser.setString(2, password);
			ResultSet rs = pstmt_getUser.executeQuery();
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

	public final List<City> getAllCities() {
		final List<City> cities = new ArrayList<City>();
		try {
			final Statement stmt = this.sqlConnection.getConnection().createStatement();
			final ResultSet rs = stmt.executeQuery("select * from city");

			while (rs.next()) {
				cities.add(new City(rs));
			}

			rs.close();
			stmt.close();
		} catch (final SQLException ex) {
			ex.printStackTrace();
		}

		return cities;

	}
	
	public final List<Category> getAllCategories() {
		final List<Category> categories = new ArrayList<Category>();
		try {
			final Statement stmt = this.sqlConnection.getConnection().createStatement();
			final ResultSet rs = stmt.executeQuery("select * from category");

			while (rs.next()) {
				categories.add(new Category(rs));
			}

			rs.close();
			stmt.close();
		} catch (final SQLException ex) {
			ex.printStackTrace();
		}

		return categories;

	}
}
