package ch.ethz.inf.dbproject.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import ch.ethz.inf.dbproject.model.access.CategoryAccess;
import ch.ethz.inf.dbproject.model.access.CityAccess;
import ch.ethz.inf.dbproject.model.access.UserAccess;

public final class Project {

	// TODO memeber for foreignkeys (category, city, user)
	private final int id;
	private final int user_id;
	private final int category_id;
	private final int city_id;
	private final String title;
	private final String description;
	private final BigDecimal goal;
	private final Date start;
	private final Date end;
	private int count;
	private BigDecimal sum;

	/**
	 * Construct a new project.
	 * 
	 * @param name
	 *            The name of the project
	 */
	public Project(final int id, final int user_id, final int category_id, final int city_id, final String title, final String description, final BigDecimal goal, final Date start,
			final Date end) {
		this.id = id;
		this.user_id = user_id;
		this.category_id = category_id;
		this.city_id = city_id;
		this.title = title;
		this.description = description;
		this.goal = goal;
		this.start = start;
		this.end = end;
	}

	public Project(final ResultSet rs) throws SQLException {
		this.id = rs.getInt("id");
		this.user_id = rs.getInt("user_id");
		this.category_id = rs.getInt("category_id");
		this.city_id = rs.getInt("city_id");
		this.title = rs.getString("title");
		this.description = rs.getString("description");
		this.goal = rs.getBigDecimal("goal");
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

	public BigDecimal getGoal() {
		return goal;
	}

	public int getUser_id() {
		return user_id;
	}
	
	public String getOwner(){
		return UserAccess.getInstance().getUserById(user_id).getName();
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public BigDecimal getSum() {
		return sum;
	}

	public void setSum(BigDecimal sum) {
		this.sum = sum;
	}
	
	public String getCategory() {
		return CategoryAccess.getInstance().getCategoryById(category_id).getName();
	}
	
	public String getCity() {
		return CityAccess.getInstance().getCityById(city_id).getName();
	}

}