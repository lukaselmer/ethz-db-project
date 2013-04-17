package ch.ethz.inf.dbproject.model.access;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ch.ethz.inf.dbproject.model.StretchGoal;

public class StretchGoalAccess extends AbstractAccess {

	private static StretchGoalAccess instance = null;
	public static synchronized StretchGoalAccess getInstance() {
		if (instance == null) {
			instance = new StretchGoalAccess();
		}
		return instance;
	}
	
	private PreparedStatement pstmt_deleteStretchGoal;
	private PreparedStatement pstmt_insertStretchGoal;
	private PreparedStatement pstmt_updateStretchGoal;
	private PreparedStatement pstmt_getStretchGoalById;
	private PreparedStatement pstmt_getStretchGoalsOfProject;
	
	protected void initStatements() throws SQLException {
		pstmt_insertStretchGoal = this.sqlConnection.getConnection().prepareStatement("insert into stretch_goal (project_id, amount, bonus) values (?, ?, ?)");
		pstmt_deleteStretchGoal = this.sqlConnection.getConnection().prepareStatement("delete from stretch_goal where id = ?");
		pstmt_updateStretchGoal = this.sqlConnection.getConnection().prepareStatement("update stretch_goal set project_id = ?, amount = ?, bonus = ? where id = ?");
		pstmt_getStretchGoalById = this.sqlConnection.getConnection().prepareStatement("select * from stretch_goal where id = ?");
		pstmt_getStretchGoalsOfProject = this.sqlConnection.getConnection().prepareStatement("select * from stretch_goal where project_id = ?");
	}

	public final void insertStretchGoal (final int project_id, final BigDecimal amount, final String bonus) {
		
		try {
			pstmt_insertStretchGoal.setInt(1, project_id);
			pstmt_insertStretchGoal.setBigDecimal(2, amount);
			pstmt_insertStretchGoal.setString(3, bonus);
			
			pstmt_insertStretchGoal.execute();
			
			// TODO: error-handling
		} catch (final SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public final void updateStretchGoal (final int id, final int project_id, final BigDecimal amount, final String bonus) {
		
		try {
			pstmt_updateStretchGoal.setInt(1, project_id);
			pstmt_updateStretchGoal.setBigDecimal(2, amount);
			pstmt_updateStretchGoal.setString(3, bonus);
			pstmt_updateStretchGoal.setInt(4, id);
			
			pstmt_updateStretchGoal.execute();
			
			// TODO: error-handling
		} catch (final SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public final StretchGoal getStretchGoalById(final int id) {
		StretchGoal c = null;

		try {
			pstmt_getStretchGoalById.setInt(1, id);
			ResultSet rs = pstmt_getStretchGoalById.executeQuery();
			if (rs.next())
				c = new StretchGoal(rs);
			rs.close();
		} catch (final SQLException ex) {
			ex.printStackTrace();
		}
		return c;
	}

	public final void deleteStretchGoal(final int id) {	
		try {
			pstmt_deleteStretchGoal.setInt(1, id);
			pstmt_deleteStretchGoal.execute();
		} catch (final SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public final List<StretchGoal> getAllStretchGoals() {
		final List<StretchGoal> stretchGoals = new ArrayList<StretchGoal>();
		try {
			final Statement stmt = this.sqlConnection.getConnection().createStatement();
			final ResultSet rs = stmt.executeQuery("select * from stretch_goal");

			while (rs.next()) {
				stretchGoals.add(new StretchGoal(rs));
			}

			rs.close();
			stmt.close();
		} catch (final SQLException ex) {
			ex.printStackTrace();
		}

		return stretchGoals;
	}
	
	public final List<StretchGoal> getStretchGoalsOfProject (final int project_id) {

		final List<StretchGoal> stretch_goals = new ArrayList<StretchGoal>();

		try {

			pstmt_getStretchGoalsOfProject.setInt(1, project_id);

			final ResultSet rs = pstmt_getStretchGoalsOfProject.executeQuery();
			while (rs.next()) {
				stretch_goals.add(new StretchGoal(rs));
			}
			rs.close();
		} catch (final SQLException ex) {
			ex.printStackTrace();
		}
		return stretch_goals;
	}
 
}
