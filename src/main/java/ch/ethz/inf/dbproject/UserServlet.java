package ch.ethz.inf.dbproject;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ch.ethz.inf.dbproject.model.User;
import ch.ethz.inf.dbproject.model.access.UserAccess;
import ch.ethz.inf.dbproject.util.UserManagement;
import ch.ethz.inf.dbproject.util.html.BeanTableHelper;

@WebServlet(description = "Page that displays the user login / logout options.", urlPatterns = { "/User" })
public final class UserServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private final UserAccess dbInterface = UserAccess.getInstance();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected final void doPost(final HttpServletRequest request,
			final HttpServletResponse response) throws ServletException,
			IOException {
	
		final HttpSession session = request.getSession(true);
		
		final String action = request.getParameter("action");
		if (action != null) { 

			final String name = request.getParameter("name");
			final String password = request.getParameter("password");
			int user_id = -1;
			
			// Insert user
			if (action.trim().equals("new")) {
				user_id = dbInterface.insertUser(name, password);
				User user = dbInterface.getUserById(user_id);
				UserManagement.setCurrentlyLoggedInUser(session, user);
			
			// Update user
			} else if (action.trim().equals("edit")) {
				user_id = Integer.parseInt(request.getParameter("id"));
				dbInterface.updateUser(user_id, name, password);
			}

			User user = dbInterface.getUserById(user_id);
			UserManagement.setCurrentlyLoggedInUser(session, user);

			// Show user
			prepareUserDetails(request, user);
			this.getServletContext().getRequestDispatcher("/user/user.jsp").forward(request, response);
		}
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
			
			// Login
			if (action.trim().equals("login") && loggedUser == null) {
				
				// Note: It is really not safe to use HTML get method to send passwords.
				// However for this project, security is not a requirement.
				final String username = request.getParameter("username");
				final String password = request.getParameter("password");
	
				loggedUser = dbInterface.findUser(username,  password);
				if (loggedUser != null) {
					UserManagement.setCurrentlyLoggedInUser(session, loggedUser);
				}
				
			// Logout
			} else if (action.trim().equals("logout")) {
				UserManagement.logoutUser(session);
				loggedUser = null;
			
			// Register
			} else if (action.trim().equals("new")) {
				
				this.getServletContext().getRequestDispatcher("/user/form.jsp").forward(request, response);
				return;
			}
		}
		
		if (loggedUser != null) {
			// Logged in
			prepareUserDetails(request, loggedUser);
		}

		// Finally, proceed to the User.jsp page which will render the profile
		this.getServletContext().getRequestDispatcher("/user/user.jsp").forward(request, response);
	}

	
	private void prepareUserDetails (HttpServletRequest request, User user) {

		final BeanTableHelper<User> userDetails = new BeanTableHelper<User>("userDetails", "userDetails", User.class);
		userDetails.addBeanColumn("Name", "name");
		userDetails.addObject(user);
		
		request.setAttribute("userDetails", userDetails);
	}
}
