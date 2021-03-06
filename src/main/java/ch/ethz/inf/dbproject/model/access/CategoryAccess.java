package ch.ethz.inf.dbproject.model.access;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ch.ethz.inf.dbproject.model.Category;

public class CategoryAccess extends AbstractAccess {

	private static CategoryAccess instance = null;
	public static synchronized CategoryAccess getInstance() {
		if (instance == null) {
			instance = new CategoryAccess();
		}
		return instance;
	}
	
	private PreparedStatement pstmt_deleteCategory;
	private PreparedStatement pstmt_insertCategory;
	private PreparedStatement pstmt_updateCategory;
	private PreparedStatement pstmt_getCategoryById;
	private PreparedStatement pstmt_getCategoryByName;
	
	protected void initStatements() throws SQLException {
		pstmt_insertCategory = this.sqlConnection.getConnection().prepareStatement("insert into category (name) values (?)", Statement.RETURN_GENERATED_KEYS);
		pstmt_deleteCategory = this.sqlConnection.getConnection().prepareStatement("delete from category where id = ?");
		pstmt_updateCategory = this.sqlConnection.getConnection().prepareStatement("update category set name = ? where id = ?");
		pstmt_getCategoryById = this.sqlConnection.getConnection().prepareStatement("select * from category where id = ?");
		pstmt_getCategoryByName = this.sqlConnection.getConnection().prepareStatement("select * from category where name = ?");
	}

	public final int insertCategory (final String name) {
		
		try {
			pstmt_insertCategory.setString(1, name);
			
			pstmt_insertCategory.execute();


		    ResultSet rs = pstmt_insertCategory.getGeneratedKeys();
			rs.next();
			
			return rs.getInt(1);
		} catch (final SQLException ex) {
			ex.printStackTrace();
		}
		return -1;
	}
	
	public final void updateCategory (final int id, final String name) {
		
		try {
			pstmt_updateCategory.setString(1, name);
			pstmt_updateCategory.setInt(2, id);
			
			pstmt_updateCategory.execute();
			
			// TODO: error-handling
		} catch (final SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public final Category getCategoryById(final int id) {
		Category c = null;

		try {
			pstmt_getCategoryById.setInt(1, id);
			ResultSet rs = pstmt_getCategoryById.executeQuery();
			if (rs.next())
				c = new Category(rs);
			rs.close();
		} catch (final SQLException ex) {
			ex.printStackTrace();
		}
		return c;
	}
	
	public final Category getCategoryByName(final String name) {
		Category c = null;

		try {
			pstmt_getCategoryByName.setString(1, name);
			ResultSet rs = pstmt_getCategoryByName.executeQuery();
			if (rs.next())
				c = new Category(rs);
			rs.close();
		} catch (final SQLException ex) {
			ex.printStackTrace();
		}
		return c;
	}

	public final void deleteCategory(final int id) {	
		try {
			pstmt_deleteCategory.setInt(1, id);
			pstmt_deleteCategory.execute();
		} catch (final SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public final List<Category> getAllCategories() {
		final List<Category> categories = new ArrayList<Category>();
		try {
			final Statement stmt = this.sqlConnection.getConnection().createStatement();
			final ResultSet rs = stmt.executeQuery("select * from category");

			while (rs.next()) {
				categories.add(new Category(rs));
			}

			rs.close();
			stmt.close();
		} catch (final SQLException ex) {
			ex.printStackTrace();
		}

		return categories;
	}

	public final List<String> getAllCategoryNames() {
		List<String> names = new ArrayList<String>();
		for (Category c : getAllCategories()) {
			names.add(c.getName());
		}
		return names;
	}
}
