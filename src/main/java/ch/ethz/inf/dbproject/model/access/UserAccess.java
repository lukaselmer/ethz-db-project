package ch.ethz.inf.dbproject.model.access;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ch.ethz.inf.dbproject.model.User;

public class UserAccess extends AbstractAccess {

	private static UserAccess instance = null;
	public static synchronized UserAccess getInstance() {
		if (instance == null) {
			instance = new UserAccess();
		}
		return instance;
	}
	
	private PreparedStatement pstmt_deleteUser;
	private PreparedStatement pstmt_insertUser;
	private PreparedStatement pstmt_updateUser;
	private PreparedStatement pstmt_getUserById;
	private PreparedStatement pstmt_findUser;
	
	
	protected void initStatements() throws SQLException {
		pstmt_insertUser = this.sqlConnection.getConnection().prepareStatement("insert into user (name, password) values (?, ?)", Statement.RETURN_GENERATED_KEYS);
		pstmt_deleteUser = this.sqlConnection.getConnection().prepareStatement("delete from user where id = ?");
		pstmt_updateUser = this.sqlConnection.getConnection().prepareStatement("update user set name = ?, password = ? where id = ?");
		pstmt_getUserById = this.sqlConnection.getConnection().prepareStatement("select * from user where id = ?");
		pstmt_findUser = this.sqlConnection.getConnection().prepareStatement("select * from user where name = ? and password = ?");
	}

	public final int insertUser (final String name, final String password) {
		
		try {
			pstmt_insertUser.setString(1, name);
			pstmt_insertUser.setString(2, password);
			
			pstmt_insertUser.execute();

		    ResultSet rs = pstmt_insertUser.getGeneratedKeys();
			rs.next();
			
			return rs.getInt(1);
		} catch (final SQLException ex) {
			ex.printStackTrace();
		}
		return -1;
	}
	
	public final void updateUser (final int id, final String name, final String password) {
		
		try {
			pstmt_updateUser.setString(1, name);
			pstmt_updateUser.setString(1, password);
			pstmt_updateUser.setInt(3, id);
			
			pstmt_updateUser.execute();
			
			// TODO: error-handling
		} catch (final SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public final User getUserById(final int id) {
		User c = null;

		try {
			pstmt_getUserById.setInt(1, id);
			ResultSet rs = pstmt_getUserById.executeQuery();
			if (rs.next())
				c = new User(rs);
			rs.close();
		} catch (final SQLException ex) {
			ex.printStackTrace();
		}
		return c;
	}

	public final void deleteUser(final int id) {	
		try {
			pstmt_deleteUser.setInt(1, id);
			pstmt_deleteUser.execute();
		} catch (final SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public final List<User> getAllUsers() {
		final List<User> users = new ArrayList<User>();
		try {
			final Statement stmt = this.sqlConnection.getConnection().createStatement();
			final ResultSet rs = stmt.executeQuery("select * from user");

			while (rs.next()) {
				users.add(new User(rs));
			}

			rs.close();
			stmt.close();
		} catch (final SQLException ex) {
			ex.printStackTrace();
		}

		return users;
	}
	
	public User findUser (final String name, final String password) {
		User u = null;
		
		try {
			pstmt_findUser.setString(1, name);
			pstmt_findUser.setString(2, password);
			ResultSet rs = pstmt_findUser.executeQuery();
			if (rs.next())
				u = new User(rs);
			rs.close();
		} catch (final SQLException ex) {
			ex.printStackTrace();
		}
		
		return u;
	}

}
