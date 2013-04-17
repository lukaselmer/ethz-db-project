package ch.ethz.inf.dbproject.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import ch.ethz.inf.dbproject.model.access.FundingAmountAccess;

/**
 * Object that represents a funding amount.
 */
public class Fund implements ComboInterface {

	private final int id;
	private final int user_id;
	private final int funding_amount_id;
	
	public Fund(final int id, final int user_id, final int funding_amount_id) {
		this.id = id;
		this.user_id = user_id;
		this.funding_amount_id = funding_amount_id;
	}
	
	public Fund (final ResultSet rs) throws SQLException {
		this.id = rs.getInt("id");
		this.user_id = rs.getInt("user_id");
		this.funding_amount_id = rs.getInt("funding_amount_id");
	}

	public int getUserId() {
		return user_id;
	}

	public int getFundingAmountId() {
		return funding_amount_id;
	}
	
	public int getId() {
		return id;
	}
	
	public int getProjectId() {
		return FundingAmountAccess.getInstance().getFundingAmountById(funding_amount_id).getProjectId();
	}
	
	public String getFundingAmount() {
		return FundingAmountAccess.getInstance().getFundingAmountById(funding_amount_id).toString();
	}
}
