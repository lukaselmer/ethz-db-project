package ch.ethz.inf.dbproject;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
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
import ch.ethz.inf.dbproject.model.Project;
import ch.ethz.inf.dbproject.model.StretchGoal;
import ch.ethz.inf.dbproject.model.User;
import ch.ethz.inf.dbproject.model.access.CategoryAccess;
import ch.ethz.inf.dbproject.model.access.CityAccess;
import ch.ethz.inf.dbproject.model.access.CommentAccess;
import ch.ethz.inf.dbproject.model.access.FundAccess;
import ch.ethz.inf.dbproject.model.access.FundingAmountAccess;
import ch.ethz.inf.dbproject.model.access.ProjectAccess;
import ch.ethz.inf.dbproject.model.access.StretchGoalAccess;
import ch.ethz.inf.dbproject.util.UserManagement;
import ch.ethz.inf.dbproject.util.html.BeanTableHelper;
import ch.ethz.inf.dbproject.util.html.ComboHelper;

/**
 * Servlet implementation class of Project Details Page
 */
@WebServlet(description = "Displays a specific project.", urlPatterns = { "/Project" })
public final class ProjectServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private final ProjectAccess dbInterface = ProjectAccess.getInstance();

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
			
			int project_id = -1;
			
			// New Project
			if (action.trim().equals("new")) {
				int city_id = Integer.parseInt(request.getParameter("city_id"));
				int category_id = Integer.parseInt(request.getParameter("category_id"));
				String title = request.getParameter("title");
				String description = request.getParameter("description");
				BigDecimal goal = new BigDecimal(request.getParameter("goal"));
				Date start = Date.valueOf(request.getParameter("start"));
				Date end = Date.valueOf(request.getParameter("end"));
				
				project_id = dbInterface.insertProject(loggedUser.getId(), city_id, category_id, title, description, goal, start, end);
				
			// New Comment
			} else if (action.trim().equals("add_comment")) {
				String comment = request.getParameter("comment");
				project_id = Integer.parseInt(request.getParameter("project_id"));
				
				CommentAccess.getInstance().insertComment(loggedUser.getId(), project_id, comment);
			
			// New Fund
			} else if (action.trim().equals("fund")) {
				int funding_amount_id = Integer.parseInt(request.getParameter("funding_amount_id"));
				
				FundAccess.getInstance().insertFund(loggedUser.getId(), funding_amount_id);
				
				project_id = FundingAmountAccess.getInstance().getFundingAmountById(funding_amount_id).getProjectId();
				
			// New Funding Amount
			} else if (action.trim().equals("new_funding_amount")) {
				String reward = request.getParameter("reward");
				BigDecimal amount = new BigDecimal(request.getParameter("amount"));
				project_id = Integer.parseInt(request.getParameter("project_id"));
				
				FundingAmountAccess.getInstance().insertFundingAmount(project_id, amount, reward);
			
			// New Stretch Goal
			} else if (action.trim().equals("new_stretch_goal")) {
				String bonus = request.getParameter("bonus");
				BigDecimal amount = new BigDecimal(request.getParameter("amount"));
				project_id = Integer.parseInt(request.getParameter("project_id"));
				
				StretchGoalAccess.getInstance().insertStretchGoal(project_id, amount, bonus);
			}
		
			// Show project
			if (project_id == -1)
				return;
			
			prepareProjectDetails(request, project_id);
			this.getServletContext().getRequestDispatcher("/project/project.jsp").forward(request, response);
			return;
		}
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected final void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		
		final HttpSession session = request.getSession(true);

		final String action = request.getParameter("action");
		final User loggedUser = UserManagement.getCurrentlyLoggedInUser(session);
		
		if (action != null && loggedUser != null) { 
			
			// Show empty form
			if (action.trim().equals("new")) {
				
				final ComboHelper categories = new ComboHelper ("category_id", (List<ComboInterface>)(List<?>)CategoryAccess.getInstance().getAllCategories());
				final ComboHelper cities = new ComboHelper ("city_id", (List<ComboInterface>)(List<?>)CityAccess.getInstance().getAllCities());

				request.setAttribute("cities_combo" , cities);
				request.setAttribute("categories_combo" , categories);
				
				this.getServletContext().getRequestDispatcher("/project/form.jsp").forward(request, response);
				return;
				
			// Show filled form
			} else if (action.trim().equals("edit")) {

				final int project_id = Integer.parseInt(request.getParameter("id"));
				Project project = dbInterface.getProjectById(project_id);

				final ComboHelper categories = new ComboHelper ("category_id", (List<ComboInterface>)(List<?>)CategoryAccess.getInstance().getAllCategories());
				final ComboHelper cities = new ComboHelper ("city_id", (List<ComboInterface>)(List<?>)CityAccess.getInstance().getAllCities());

				request.setAttribute("cities_combo" , cities);
				request.setAttribute("categories_combo", categories);
				request.setAttribute("project", project);
				
				this.getServletContext().getRequestDispatcher("/project/form.jsp").forward(request, response);
				return;
			}
		}

		// Show project details
		final int project_id = Integer.parseInt(request.getParameter("id"));
		prepareProjectDetails(request, project_id);
		
		this.getServletContext().getRequestDispatcher("/project/project.jsp").forward(request, response);
	}
	
	
	// Prepare request for showing project details
	private void prepareProjectDetails(HttpServletRequest request, int project_id) {
		request.setAttribute("project_id", project_id);
		request.setAttribute("project", dbInterface.getProjectById(project_id));
		
		request.setAttribute("projectTable", createProjectDetailTable(project_id));			
		request.setAttribute("goalsTable", createStretchGoalTable(project_id));			
		request.setAttribute("amountTable", createFundingAmountTable(project_id));
		request.setAttribute("commentTable", createCommentsTable(project_id));
		
		final ComboHelper fundingAmountCombo = new ComboHelper ("funding_amount_id", (List<ComboInterface>)(List<?>)FundingAmountAccess.getInstance().getFundingAmountsOfProject(project_id));
		request.setAttribute("fundingAmountCombo", fundingAmountCombo);
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
		final List<Comment> comments = CommentAccess.getInstance().getCommentsOfProject(project_id);
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
		List<FundingAmount> fundingAmounts = FundingAmountAccess.getInstance().getFundingAmountsOfProject(project_id);
		
		final BeanTableHelper<FundingAmount> fundingAmountsTable = new BeanTableHelper<FundingAmount>(
				"fundingAmounts" 		/* The table html id property */,
				"fundingAmountsTable" /* The table html class property */,
				FundingAmount.class 	/* The class of the objects (rows) that will be displayed */
		);

		// Add columns to the new table
		fundingAmountsTable.addBeanColumn("Reward", "reward");
		fundingAmountsTable.addBeanColumn("Amount", "amount");
		
		fundingAmountsTable.addObjects(fundingAmounts);
		
		return fundingAmountsTable.generateHtmlCode();
	}
	
	/*******************************************************
	 * Construct a table for all payment amounts
	 *******************************************************/
	private String createStretchGoalTable (final int project_id) {
		List<StretchGoal> stretchGoals = StretchGoalAccess.getInstance().getStretchGoalsOfProject(project_id);
		
		final BeanTableHelper<StretchGoal> stretchGoalsTable = new BeanTableHelper<StretchGoal>(
				"stretchGoals" 		/* The table html id property */,
				"stretchGoalsTable" /* The table html class property */,
				StretchGoal.class 	/* The class of the objects (rows) that will be displayed */
		);

		// Add columns to the new table
		stretchGoalsTable.addBeanColumn("Bonus", "bonus");
		stretchGoalsTable.addBeanColumn("Amount", "amount");
		
		stretchGoalsTable.addObjects(stretchGoals);
		
		return stretchGoalsTable.generateHtmlCode();
	}
}