package ch.ethz.inf.dbproject.model.access;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import ch.ethz.inf.dbproject.model.Comment;

public class CommentAccess extends AbstractAccess {

	private static CommentAccess instance = null;
	public static synchronized CommentAccess getInstance() {
		if (instance == null) {
			instance = new CommentAccess();
		}
		return instance;
	}
	
	private PreparedStatement pstmt_deleteComment;
	private PreparedStatement pstmt_insertComment;
	private PreparedStatement pstmt_updateComment;
	private PreparedStatement pstmt_getCommentById;
	private PreparedStatement pstmt_getCommentsOfProject;
	
	protected void initStatements() throws SQLException {
		pstmt_insertComment = this.sqlConnection.getConnection().prepareStatement("insert into comment (user_id, project_id, text, date) values (?, ?, ?, NOW())");
		pstmt_deleteComment = this.sqlConnection.getConnection().prepareStatement("delete from comment where id = ?");
		pstmt_updateComment = this.sqlConnection.getConnection().prepareStatement("update comment set user_id = ?, project_id = ?, text = ?, date = ? where id = ?");
		pstmt_getCommentById = this.sqlConnection.getConnection().prepareStatement("select * from comment where id = ?");
		pstmt_getCommentsOfProject = this.sqlConnection.getConnection().prepareStatement("select * from comment where project_id = ?");
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
	
	public final void updateComment (final int id, final int user_id, final int project_id, final String text, final Date date) {
		
		try {
			pstmt_updateComment.setInt(1, user_id);
			pstmt_updateComment.setInt(2, project_id);
			pstmt_updateComment.setString(3, text);
			pstmt_updateComment.setObject(4, new Timestamp(date.getTime()));
			pstmt_updateComment.setInt(5, id);
			
			pstmt_updateComment.execute();
			
			// TODO: error-handling
		} catch (final SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public final Comment getCommentById(final int id) {
		Comment c = null;

		try {
			pstmt_getCommentById.setInt(1, id);
			ResultSet rs = pstmt_getCommentById.executeQuery();
			if (rs.next())
				c = new Comment(rs);
			rs.close();
		} catch (final SQLException ex) {
			ex.printStackTrace();
		}
		return c;
	}

	public final void deleteComment(final int id) {	
		try {
			pstmt_deleteComment.setInt(1, id);
			pstmt_deleteComment.execute();
		} catch (final SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public final List<Comment> getAllComments() {
		final List<Comment> comments = new ArrayList<Comment>();
		try {
			final Statement stmt = this.sqlConnection.getConnection().createStatement();
			final ResultSet rs = stmt.executeQuery("select * from comment");

			while (rs.next()) {
				comments.add(new Comment(rs));
			}

			rs.close();
			stmt.close();
		} catch (final SQLException ex) {
			ex.printStackTrace();
		}

		return comments;
	}
	
	public final List<Comment> getCommentsOfProject (final int project_id) {

		final List<Comment> comments = new ArrayList<Comment>();

		try {

			pstmt_getCommentsOfProject.setInt(1, project_id);

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
