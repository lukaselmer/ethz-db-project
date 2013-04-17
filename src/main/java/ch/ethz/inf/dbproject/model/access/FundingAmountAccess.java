package ch.ethz.inf.dbproject.model.access;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ch.ethz.inf.dbproject.model.FundingAmount;

public class FundingAmountAccess extends AbstractAccess {

	private static FundingAmountAccess instance = null;
	public static synchronized FundingAmountAccess getInstance() {
		if (instance == null) {
			instance = new FundingAmountAccess();
		}
		return instance;
	}
	
	private PreparedStatement pstmt_deleteFundingAmount;
	private PreparedStatement pstmt_insertFundingAmount;
	private PreparedStatement pstmt_updateFundingAmount;
	private PreparedStatement pstmt_getFundingAmountById;
	private PreparedStatement pstmt_getFundingAmountsOfProject;
	
	protected void initStatements() throws SQLException {
		pstmt_insertFundingAmount = this.sqlConnection.getConnection().prepareStatement("insert into funding_amount (project_id, amount, reward) values (?, ?, ?)");
		pstmt_deleteFundingAmount = this.sqlConnection.getConnection().prepareStatement("delete from funding_amount where id = ?");
		pstmt_updateFundingAmount = this.sqlConnection.getConnection().prepareStatement("update funding_amount set project_id = ?, amount = ?, reward = ? where id = ?");
		pstmt_getFundingAmountById = this.sqlConnection.getConnection().prepareStatement("select * from funding_amount where id = ?");
		pstmt_getFundingAmountsOfProject = this.sqlConnection.getConnection().prepareStatement("select * from funding_amount where project_id = ?");
	}

	public final void insertFundingAmount (final int project_id, final BigDecimal amount, final String reward) {
		
		try {
			pstmt_insertFundingAmount.setInt(1, project_id);
			pstmt_insertFundingAmount.setBigDecimal(2, amount);
			pstmt_insertFundingAmount.setString(3, reward);
			
			pstmt_insertFundingAmount.execute();
			
			// TODO: error-handling
		} catch (final SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public final void updateFundingAmount (final int id, final int project_id, final BigDecimal amount, final String reward) {
		
		try {
			pstmt_updateFundingAmount.setInt(1, project_id);
			pstmt_updateFundingAmount.setBigDecimal(2, amount);
			pstmt_updateFundingAmount.setString(3, reward);
			pstmt_updateFundingAmount.setInt(4, id);
			
			pstmt_updateFundingAmount.execute();
			
			// TODO: error-handling
		} catch (final SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public final FundingAmount getFundingAmountById(final int id) {
		FundingAmount c = null;

		try {
			pstmt_getFundingAmountById.setInt(1, id);
			ResultSet rs = pstmt_getFundingAmountById.executeQuery();
			if (rs.next())
				c = new FundingAmount(rs);
			rs.close();
		} catch (final SQLException ex) {
			ex.printStackTrace();
		}
		return c;
	}

	public final void deleteFundingAmount(final int id) {	
		try {
			pstmt_deleteFundingAmount.setInt(1, id);
			pstmt_deleteFundingAmount.execute();
		} catch (final SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public final List<FundingAmount> getAllFundingAmounts() {
		final List<FundingAmount> fundingAmounts = new ArrayList<FundingAmount>();
		try {
			final Statement stmt = this.sqlConnection.getConnection().createStatement();
			final ResultSet rs = stmt.executeQuery("select * from funding_amount");

			while (rs.next()) {
				fundingAmounts.add(new FundingAmount(rs));
			}

			rs.close();
			stmt.close();
		} catch (final SQLException ex) {
			ex.printStackTrace();
		}

		return fundingAmounts;
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
 
}
