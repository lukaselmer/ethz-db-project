package ch.ethz.inf.dbproject.model.access;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * This class should be the interface between the web application and the
 * database. Keeping all the data-access methods here will be very helpful for
 * part 2 of the project.
 */
public final class FundAccess extends AbstractAccess {
	
	private static FundAccess instance = null;
	public static synchronized FundAccess getInstance() {
		if (instance == null) {
			instance = new FundAccess();
		}
		return instance;
	}

	private PreparedStatement pstmt_insertFund;
		
	//dont get it why all prepared statements should be in the cTor.. but was a hint in this file..
	public void initStatements() throws SQLException {
		pstmt_insertFund = this.sqlConnection.getConnection().prepareStatement("insert into fund (user_id, funding_amount_id) values (?, ?)");
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
}
