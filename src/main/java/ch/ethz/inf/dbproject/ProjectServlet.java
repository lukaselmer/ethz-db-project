package ch.ethz.inf.dbproject;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ch.ethz.inf.dbproject.model.Amount;
import ch.ethz.inf.dbproject.model.Comment;
import ch.ethz.inf.dbproject.model.DatastoreInterface;
import ch.ethz.inf.dbproject.model.Project;
import ch.ethz.inf.dbproject.util.UserManagement;
import ch.ethz.inf.dbproject.util.html.BeanTableHelper;

/**
 * Servlet implementation class of Project Details Page
 */
@WebServlet(description = "Displays a specific project.", urlPatterns = { "/Project" })
public final class ProjectServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private final DatastoreInterface dbInterface = new DatastoreInterface();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ProjectServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected final void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {

		final HttpSession session = request.getSession(true);

		final String idString = request.getParameter("id");
		if (idString == null) {
			this.getServletContext().getRequestDispatcher("/Projects").forward(request, response);
		}

		try {

			final Integer id = Integer.parseInt(idString);
			final Project project = this.dbInterface.getProjectById(id);

			
			/*******************************************************
			 * Construct a table to present all properties of a project
			 *******************************************************/
			final BeanTableHelper<Project> table = new BeanTableHelper<Project>(
					"projects" 		/* The table html id property */,
					"projectTable" /* The table html class property */,
					Project.class 	/* The class of the objects (rows) that will be displayed */
			);

			// Add columns to the new table

			/*
			 * Column 1: The name of the item (This will probably have to be changed)
			 */
			table.addBeanColumn("Project Name", "name");

			/*
			 * Columns 2 & 3: Some random fields. These should be replaced by i.e. funding progress, or time remaining
			 */
			table.addBeanColumn("Test Field2", "field2");
			table.addBeanColumn("Test Integer Field 3", "field3");

			table.addObject(project);
			table.setVertical(true);			

			session.setAttribute("projectTable", table);			
			
			/*******************************************************
			 * Construct a table for all payment amounts
			 *******************************************************/
			//TODO implement this
			//List<Amount> comments = this.dbInterface.getAmountsOfProject(id);
			//Create a table to display the amounts the same way as above
			//The table needs to have a link column the allows a registered user to fund that amount
			//We need to catch the click a store the funding in the database
			//session.setAttribute("amountTable", table);
			
			/***
			 * Check if we new to add a comment
			 */
			final String action = request.getParameter("action");
			if (action != null && action.trim().equals("add_comment")) {
				String username = UserManagement.getCurrentlyLoggedInUser(session).getUsername();
				String comment = request.getParameter("comment");
				Comment commentObj = new Comment(username, comment);
				
				//TODO implement this
				//this.dbInterface.addCommentForProject(id, commentObj);
			}
			
			/*******************************************************
			 * Construct a table to present all comments
			 *******************************************************/
			//TODO implement this
			//List<Comment> comments = this.dbInterface.getCommentsOfProject(id);
			//Create a table to display the comments the same way as above	
			//session.setAttribute("commentTable", table);
			
		} catch (final Exception ex) {
			ex.printStackTrace();
			this.getServletContext().getRequestDispatcher("/Projects.jsp").forward(request, response);
		}

		this.getServletContext().getRequestDispatcher("/Project.jsp").forward(request, response);
	}
}