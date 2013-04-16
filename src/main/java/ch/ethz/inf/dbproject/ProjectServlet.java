package ch.ethz.inf.dbproject;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ch.ethz.inf.dbproject.model.ComboInterface;
import ch.ethz.inf.dbproject.model.FundingAmount;
import ch.ethz.inf.dbproject.model.Comment;
import ch.ethz.inf.dbproject.model.DatastoreInterface;
import ch.ethz.inf.dbproject.model.Project;
import ch.ethz.inf.dbproject.model.User;
import ch.ethz.inf.dbproject.util.UserManagement;
import ch.ethz.inf.dbproject.util.html.BeanTableHelper;
import ch.ethz.inf.dbproject.util.html.ComboHelper;

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
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected final void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {

		final HttpSession session = request.getSession(true);

		final String action = request.getParameter("action");
		final User loggedUser = UserManagement.getCurrentlyLoggedInUser(session);
		
		if (action != null && loggedUser != null) { 
			
			// New Project
			if (action.trim().equals("new")) {
				int city_id = Integer.parseInt(request.getParameter("city_id"));
				int category_id = Integer.parseInt(request.getParameter("category_id"));
				String title = request.getParameter("title");
				String description = request.getParameter("description");
				double goal = Double.parseDouble(request.getParameter("goal"));
				Date start = Date.valueOf(request.getParameter("start"));
				Date end = Date.valueOf(request.getParameter("end"));
				
				this.dbInterface.insertProject(loggedUser.getId(), city_id, category_id, title, description, goal, start, end);
			
			// New Comment
			} else if (action.trim().equals("add_comment")) {
		
				String comment = request.getParameter("comment");
				int project_id = Integer.parseInt(request.getParameter("project_id"));
				
				this.dbInterface.insertComment(loggedUser.getId(), project_id, comment);
			
			// New Fund
			} else if (action.trim().equals("fund")) {

				int funding_amount_id = Integer.parseInt(request.getParameter("funding_amount_id"));
				
				this.dbInterface.insertFund(loggedUser.getId(), funding_amount_id);
			}
		}
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected final void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {

		final String idString = request.getParameter("id");
		if (idString == null) {
			this.getServletContext().getRequestDispatcher("/Projects").forward(request, response);
		}

		try {

			final int project_id = Integer.parseInt(idString);
			request.setAttribute("project_id", project_id);

			// Create tables and store them in the request
			request.setAttribute("projectTable", createProjectDetailTable(project_id));			
			request.setAttribute("amountTable", createFundingAmountTable(project_id));
			request.setAttribute("commentTable", createCommentsTable(project_id));
			
			final ComboHelper fundingAmountCombo = new ComboHelper ("funding_amount_id", (List<ComboInterface>)(List<?>)dbInterface.getFundingAmountsOfProject(project_id));
			request.setAttribute("fundingAmountCombo", fundingAmountCombo);
			
		} catch (final Exception ex) {
			ex.printStackTrace();
			this.getServletContext().getRequestDispatcher("/Projects.jsp").forward(request, response);
		}

		this.getServletContext().getRequestDispatcher("/Project.jsp").forward(request, response);
	}

	
	/*******************************************************
	 * Construct a table to present all properties of a project
	 *******************************************************/
	private String createProjectDetailTable (final int project_id) {
		final Project project = this.dbInterface.getProjectById(project_id);
		final BeanTableHelper<Project> table = new BeanTableHelper<Project>(
				"projects" 		/* The table html id property */,
				"projectTable" /* The table html class property */,
				Project.class 	/* The class of the objects (rows) that will be displayed */
		);
	
		// Add columns to the new table
	
		table.addBeanColumn("Project Title", "title");
		table.addBeanColumn("Description", "description");
		table.addBeanColumn("Owner", "owner");
		table.addBeanColumn("Goal", "goal");
		table.addBeanColumn("Start", "start");
		table.addBeanColumn("End", "end");
	
		table.addObject(project);
		table.setVertical(true);
		
		return table.generateHtmlCode();
	}
	
	
	/*******************************************************
	 * Construct a table to present all comments
	 *******************************************************/
	private String createCommentsTable (final int project_id) {
		final List<Comment> comments = this.dbInterface.getCommentsByProjectId(project_id);
		final BeanTableHelper<Comment> commentTable = new BeanTableHelper<Comment>(
				"comment" 		/* The table html id property */,
				"commentTable" /* The table html class property */,
				Comment.class 	/* The class of the objects (rows) that will be displayed */
		);
		
		commentTable.addBeanColumn("User", "username");
		commentTable.addBeanColumn("Comment", "text");
		commentTable.addBeanColumn("Created", "date");
		
		commentTable.addObjects(comments);
		
		return commentTable.generateHtmlCode();
	}
	

	/*******************************************************
	 * Construct a table for all payment amounts
	 *******************************************************/
	private String createFundingAmountTable(final int project_id) {
		List<FundingAmount> fundingAmounts = this.dbInterface.getFundingAmountsOfProject(project_id);
		
		final BeanTableHelper<FundingAmount> fundingAmountsTable = new BeanTableHelper<FundingAmount>(
				"fundingAmounts" 		/* The table html id property */,
				"fundingAmountsTable" /* The table html class property */,
				FundingAmount.class 	/* The class of the objects (rows) that will be displayed */
		);

		// Add columns to the new table
		fundingAmountsTable.addBeanColumn("Reward", "reward");
		fundingAmountsTable.addBeanColumn("Amount", "amount");
		//fundingAmountsTable.addLinkColumn("", "Fund", "Project?action=fund&funding_amount_id=", "id");

		fundingAmountsTable.addObjects(fundingAmounts);
		
		return fundingAmountsTable.generateHtmlCode();
	}
}