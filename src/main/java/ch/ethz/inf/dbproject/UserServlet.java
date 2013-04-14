package ch.ethz.inf.dbproject;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ch.ethz.inf.dbproject.model.ComboInterface;
import ch.ethz.inf.dbproject.model.DatastoreInterface;
import ch.ethz.inf.dbproject.model.User;
import ch.ethz.inf.dbproject.util.UserManagement;
import ch.ethz.inf.dbproject.util.html.BeanTableHelper;
import ch.ethz.inf.dbproject.util.html.ComboHelper;

@WebServlet(description = "Page that displays the user login / logout options.", urlPatterns = { "/User" })
public final class UserServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private final DatastoreInterface dbInterface = new DatastoreInterface();

	public final static String SESSION_USER_LOGGED_IN = "userLoggedIn";
	public final static String SESSION_USER_DETAILS = "userDetails";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected final void doGet(final HttpServletRequest request,
			final HttpServletResponse response) throws ServletException,
			IOException {

		final HttpSession session = request.getSession(true);
		User loggedUser = UserManagement.getCurrentlyLoggedInUser(session);
		
		
		final String action = request.getParameter("action");
		if (action != null) { 
			if (action.trim().equals("login") && loggedUser == null) {

				final String username = request.getParameter("username");
				// Note: It is really not safe to use HTML get method to send passwords.
				// However for this project, security is not a requirement.
				final String password = request.getParameter("password");
	
				loggedUser = dbInterface.getUser(username,  password);
				if (loggedUser != null) {
					UserManagement.setCurrentlyLoggedInUser(session, loggedUser);
					session.setAttribute(SESSION_USER_LOGGED_IN, true);
				}
				
			} else if (action.trim().equals("logout")) {
				UserManagement.logoutUser(session);
				session.setAttribute(SESSION_USER_LOGGED_IN, false);
				loggedUser = null;
			}
		}
		
		if (loggedUser == null) {
			session.setAttribute(SESSION_USER_LOGGED_IN, false);
			
		} else {
			// Logged in
			final BeanTableHelper<User> userDetails = new BeanTableHelper<User>("userDetails", "userDetails", User.class);
			//userDetails.addBeanColumn("Username", "username");
			userDetails.addBeanColumn("Name", "name");
			userDetails.addObject(loggedUser);
			

			final ComboHelper categories = new ComboHelper ("category_id", (List<ComboInterface>)(List<?>)dbInterface.getAllCategories());
			final ComboHelper cities = new ComboHelper ("city_id", (List<ComboInterface>)(List<?>)dbInterface.getAllCities());

			request.setAttribute("cities_combo" , cities);
			request.setAttribute("categories_combo" , categories);
			session.setAttribute(SESSION_USER_LOGGED_IN, true);
			session.setAttribute(SESSION_USER_DETAILS, userDetails);
		}

		// TODO display registration

		// Finally, proceed to the User.jsp page which will render the profile
		this.getServletContext().getRequestDispatcher("/User.jsp").forward(request, response);

	}

}
