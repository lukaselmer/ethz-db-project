package ch.ethz.inf.dbproject.model;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Object that represents a stretch goal
 */
public class StretchGoal implements ComboInterface {

	private final int id;
	private final int project_id;
	private final double amount;
	private final String bonus;
	
	public StretchGoal(final int id, final int project_id, final double amount, final String bonus) {
		this.id = id;
		this.project_id = project_id;
		this.amount = amount;
		this.bonus = bonus;
	}
	
	public StretchGoal (final ResultSet rs) throws SQLException {
		this.id = rs.getInt("id");
		this.project_id = rs.getInt("project_id");
		this.amount = rs.getDouble("amount");
		this.bonus = rs.getString("bonus");
	}

	public double getAmount() {
		return amount;
	}

	public String getBonus() {
		return bonus;
	}

	public int getId() {
		return id;
	}

	public int getProjectId() {
		return project_id;
	}
	
	public String toString() {;
		return "[" + amount + "]: " + bonus;
	}
}
