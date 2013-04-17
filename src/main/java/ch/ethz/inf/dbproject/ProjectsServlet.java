package ch.ethz.inf.dbproject;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ch.ethz.inf.dbproject.model.Project;
import ch.ethz.inf.dbproject.model.access.ProjectAccess;
import ch.ethz.inf.dbproject.util.html.BeanTableHelper;

/**
 * Servlet implementation class of Project list page
 */
@WebServlet(description = "Page that displays the projects based on different filtering criteria.", urlPatterns = { "/Projects" })
public final class ProjectsServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private final ProjectAccess dbInterface = ProjectAccess.getInstance();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ProjectsServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected final void doGet(final HttpServletRequest request, final HttpServletResponse response) 
			throws ServletException, IOException {

		final BeanTableHelper<Project> table = new BeanTableHelper<Project>(
				"projects" 		/* The table html id property */,
				"projectsTable" /* The table html class property */,
				Project.class 	/* The class of the objects (rows) that will bedisplayed */
		);

		// Add columns to the new table
		table.addBeanColumn("Project Title", "title");
		table.addBeanColumn("Start", "start");
		table.addBeanColumn("End", "end");
		table.addLinkColumn("", "View Project", "Project?id=", "id");
		
		List<Project> projects = null;

		// The filter parameter defines what to show on the Projects page
		final String filter = request.getParameter("filter");
		final String category = request.getParameter("category");

		if (filter == null && category == null) {
			// If no filter is specified, then we display all the projects!
			projects = this.dbInterface.getAllProjects();

		} else if (category != null) {
			projects = this.dbInterface.getProjectsByCategory(Integer.parseInt(category));
			
		} else if (filter != null) {
		
			if(filter.equals("popular")) {
				table.addBeanColumn("Number fundings", "count");
				projects = this.dbInterface.getMostPopularProjects();

			} else if (filter.equals("funded")) {
				table.addBeanColumn("Funding sum", "sum");
				projects = this.dbInterface.getMostFundedProjects();

			} else if (filter.equals("ending")) {
				projects = this.dbInterface.getSoonEndingProjects();
			}
			
		} else {
			throw new RuntimeException("Code should not be reachable!");
		}
		
		if (projects != null) {
			table.addObjects(projects);
			request.setAttribute("projects", table.generateHtmlCode());
		}

		// Finally, proceed to the Projects.jsp page which will render the Projects
		this.getServletContext().getRequestDispatcher("/project/projects.jsp").forward(request, response);
	}
}