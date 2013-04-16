package ch.ethz.inf.dbproject.model.access;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ch.ethz.inf.dbproject.model.Comment;
import ch.ethz.inf.dbproject.model.FundingAmount;

/**
 * This class should be the interface between the web application and the
 * database. Keeping all the data-access methods here will be very helpful for
 * part 2 of the project.
 */
public final class DatastoreInterface extends AbstractAccess {
	
	private static DatastoreInterface instance = null;
	public static synchronized DatastoreInterface getInstance() {
		if (instance == null) {
			instance = new DatastoreInterface();
		}
		return instance;
	}


	private PreparedStatement pstmt_insertComment;
	private PreparedStatement pstmt_insertFund;
	
	private PreparedStatement pstmt_getCommentsOfProject;

	private PreparedStatement pstmt_getFundingAmountsOfProject;
	private PreparedStatement pstmt_getFundingAmountById;
	
		
	//dont get it why all prepared statements should be in the cTor.. but was a hint in this file..
	public void initStatements() throws SQLException {
		pstmt_insertFund = this.sqlConnection.getConnection().prepareStatement("insert into fund (user_id, funding_amount_id) values (?, ?)");
		pstmt_insertComment = this.sqlConnection.getConnection().prepareStatement("insert into comment (user_id, project_id, text, date) values (?, ?, ?, NOW())");
		pstmt_getFundingAmountsOfProject = this.sqlConnection.getConnection().prepareStatement("select * from funding_amount where project_id = ?");
		pstmt_getFundingAmountById = this.sqlConnection.getConnection().prepareStatement("select * from funding_amount where id = ?");
		pstmt_getCommentsOfProject = this.sqlConnection.getConnection().prepareStatement("select * from comment where project_id = ?");
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
	
	public final FundingAmount getFundingAmountById (final int id) {

		FundingAmount funding_amount = null;

		try {

			pstmt_getFundingAmountById.setInt(1, id);

			final ResultSet rs = pstmt_getFundingAmountById.executeQuery();
			if (rs.next())
				funding_amount = new FundingAmount(rs);
			rs.close();
		} catch (final SQLException ex) {
			ex.printStackTrace();
		}
		return funding_amount;
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

}
