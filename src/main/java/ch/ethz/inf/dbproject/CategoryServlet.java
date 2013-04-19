package ch.ethz.inf.dbproject;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ch.ethz.inf.dbproject.model.Category;
import ch.ethz.inf.dbproject.model.User;
import ch.ethz.inf.dbproject.model.access.CategoryAccess;
import ch.ethz.inf.dbproject.util.UserManagement;
import ch.ethz.inf.dbproject.util.html.BeanTableHelper;

@WebServlet(description = "Page that displays the Categories", urlPatterns = { "/Category" })
public final class CategoryServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private final CategoryAccess dbInterface = CategoryAccess.getInstance();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CategoryServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected final void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {

		final HttpSession session = request.getSession(true);
		User loggedUser = UserManagement.getCurrentlyLoggedInUser(session);
		
		final String action = request.getParameter("action");
		if (action != null && loggedUser != null) { 

			final String name = request.getParameter("name");
			
			// insert category
			if (action.trim().equals("new")) {
				dbInterface.insertCategory(name);
			
			// update category
			} else if (action.trim().equals("edit")) {
				final int id = Integer.parseInt(request.getParameter("id"));
				dbInterface.updateCategory(id, name);
			
			}
		}

		// Show all categories
		request.setAttribute("categoriesTable", createCategoriesTable());
		this.getServletContext().getRequestDispatcher("/category/categories.jsp").forward(request, response);
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected final void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {

		final HttpSession session = request.getSession(true);
		User loggedUser = UserManagement.getCurrentlyLoggedInUser(session);
		
		final String action = request.getParameter("action");
		if (action != null && loggedUser != null) { 
			
			// Show empty form
			if (action.trim().equals("new")) {
				this.getServletContext().getRequestDispatcher("/category/form.jsp").forward(request, response);
				return;

			// Show filled form
			} else if (action.trim().equals("edit")) {
				int id = Integer.parseInt(request.getParameter("id"));
				request.setAttribute("category", dbInterface.getCategoryById(id));
				this.getServletContext().getRequestDispatcher("/category/form.jsp").forward(request, response);
				return;
			
			// Delete category
			} else if (action.trim().equals("delete")) {

				int id = Integer.parseInt(request.getParameter("id"));
				dbInterface.deleteCategory(id);
			}
		} 

		// Show alle categories
		request.setAttribute("categoriesTable", createCategoriesTable());
		
		this.getServletContext().getRequestDispatcher("/category/categories.jsp").forward(request, response);
	}
	
	/*******************************************************
	 * Construct a table to present all categories
	 *******************************************************/
	private String createCategoriesTable () {
		final BeanTableHelper<Category> table = new BeanTableHelper<Category>(
				"categories" 		/* The table html id property */,
				"categoriesTable" /* The table html class property */,
				Category.class 	/* The class of the objects (rows) that will be displayed */
		);
	
		// Add columns to the new table
	
		table.addBeanColumn("Name", "name");
		table.addLinkColumn("", "Delete", "Category?action=delete&id=", "id");
		table.addLinkColumn("", "Edit", "Category?action=edit&id=", "id");
		
		table.addObjects(dbInterface.getAllCategories());
		return table.generateHtmlCode();
	}

}
