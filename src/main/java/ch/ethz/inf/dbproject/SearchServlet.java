package ch.ethz.inf.dbproject;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ch.ethz.inf.dbproject.model.Project;
import ch.ethz.inf.dbproject.model.access.ProjectAccess;
import ch.ethz.inf.dbproject.util.html.BeanTableHelper;

/**
 * Servlet implementation class Search
 */
@WebServlet(description = "The search page for projects", urlPatterns = { "/Search" })
public final class SearchServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private final ProjectAccess dbInterface = ProjectAccess.getInstance();
		
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		final HttpSession session = request.getSession(true);
		
		/*******************************************************
		 * Construct a table to present all our search results
		 *******************************************************/
		final BeanTableHelper<Project> table = new BeanTableHelper<Project>(
				"projects" 		/* The table html id property */,
				"projectsTable" /* The table html class property */,
				Project.class 	/* The class of the objects (rows) that will bedisplayed */
		);

		// Add columns to the new table
		table.addBeanColumn("Project Title", "title");
		table.addBeanColumn("Start", "start");
		table.addBeanColumn("End", "end");

		/*
		 * Column 4: This is a special column. It adds a link to view the
		 * Project. We need to pass the project identifier to the url.
		 */
		table.addLinkColumn(""	/* The header. We will leave it empty */,
				"View Project" 	/* What should be displayed in every row */,
				"Project?id=" 	/* This is the base url. The final url will be composed from the concatenation of this and the parameter below */, 
				"id" 			/* For every project displayed, the ID will be retrieved and will be attached to the url base above */);

		// Pass the table to the session. This will allow the respective jsp page to display the table.
		session.setAttribute("results", table);

		// The filter parameter defines what to show on the projects page
		final String filter = request.getParameter("filter");

		if (filter != null) {
			
			final String search = request.getParameter("search");
			
			if(filter.equals("title")) {
				table.addObjects(this.dbInterface.searchProjectByTitle(search));

			} else if (filter.equals("category")) {
				table.addObjects(this.dbInterface.searchProjectByCategory(search));

			} else if (filter.equals("city")) {
				table.addObjects(this.dbInterface.searchProjectByCity(search));
			}			
		}

		// Finally, proceed to the Seaech.jsp page which will render the search results
        this.getServletContext().getRequestDispatcher("/project/search.jsp").forward(request, response);	        
	}
}