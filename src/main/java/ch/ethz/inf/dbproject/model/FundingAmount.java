package ch.ethz.inf.dbproject.model;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Object that represents a funding amount.
 */
public class FundingAmount implements ComboInterface {

	private final int id;
	private final int project_id;
	private final double amount;
	private final String reward;
	
	public FundingAmount(final int id, final int project_id, final double amount, final String reward) {
		this.id = id;
		this.project_id = project_id;
		this.amount = amount;
		this.reward = reward;
	}
	
	public FundingAmount (final ResultSet rs) throws SQLException {
		this.id = rs.getInt("id");
		this.project_id = rs.getInt("project_id");
		this.amount = rs.getDouble("amount");
		this.reward = rs.getString("reward");
	}

	public double getAmount() {
		return amount;
	}

	public String getReward() {
		return reward;
	}

	public int getId() {
		return id;
	}

	public int getProjectId() {
		return project_id;
	}
	
	public String toString() {;
		return "[" + amount + "]: " + reward;
	}
}
