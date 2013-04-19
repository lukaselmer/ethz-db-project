package ch.ethz.inf.dbproject;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ch.ethz.inf.dbproject.model.City;
import ch.ethz.inf.dbproject.model.User;
import ch.ethz.inf.dbproject.model.access.CityAccess;
import ch.ethz.inf.dbproject.util.UserManagement;
import ch.ethz.inf.dbproject.util.html.BeanTableHelper;

@WebServlet(description = "Page that displays the Cities", urlPatterns = { "/City" })
public final class CityServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private final CityAccess dbInterface = CityAccess.getInstance();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CityServlet() {
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
			
			// Insert city
			if (action.trim().equals("new")) {
				dbInterface.insertCity(name);
			
			// Update city
			} else if (action.trim().equals("edit")) {
				final int id = Integer.parseInt(request.getParameter("id"));
				dbInterface.updateCity(id, name);
			
			}
		}

		// Show all cities
		request.setAttribute("citiesTable", createCitiesTable());
		this.getServletContext().getRequestDispatcher("/city/cities.jsp").forward(request, response);
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected final void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {

		final HttpSession session = request.getSession(true);
		User loggedUser = UserManagement.getCurrentlyLoggedInUser(session);
		
		final String action = request.getParameter("action");
		if (action != null && loggedUser != null) { 
			
			// SHow empty form
			if (action.trim().equals("new")) {
				this.getServletContext().getRequestDispatcher("/city/form.jsp").forward(request, response);
				return;

			// Show filled form
			} else if (action.trim().equals("edit")) {
				int id = Integer.parseInt(request.getParameter("id"));
				request.setAttribute("city", dbInterface.getCityById(id));
				this.getServletContext().getRequestDispatcher("/city/form.jsp").forward(request, response);
				return;
			
			// Delete city
			} else if (action.trim().equals("delete")) {

				int id = Integer.parseInt(request.getParameter("id"));
				dbInterface.deleteCity(id);
			}
		} 

		// Show all cities
		request.setAttribute("citiesTable", createCitiesTable());
		
		this.getServletContext().getRequestDispatcher("/city/cities.jsp").forward(request, response);
	}
	
	/*******************************************************
	 * Construct a table to present all cities
	 *******************************************************/
	private String createCitiesTable () {
		final BeanTableHelper<City> table = new BeanTableHelper<City>(
				"cities" 		/* The table html id property */,
				"citiesTable" /* The table html class property */,
				City.class 	/* The class of the objects (rows) that will be displayed */
		);
	
		// Add columns to the new table
	
		table.addBeanColumn("Name", "name");
		table.addLinkColumn("", "Delete", "City?action=delete&id=", "id");
		table.addLinkColumn("", "Edit", "City?action=edit&id=", "id");
		
		table.addObjects(dbInterface.getAllCities());
		return table.generateHtmlCode();
	}

}
