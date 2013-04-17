<%@page import="ch.ethz.inf.dbproject.model.User"%>
<%@page import="ch.ethz.inf.dbproject.util.UserManagement"%>
<%@page import="ch.ethz.inf.dbproject.UserServlet"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../Header.jsp" %>

<% 
final User user = UserManagement.getCurrentlyLoggedInUser(session);

//User is logged in. Display the details:
if (user != null) {
%>
	<h2>Your Account</h2>
	${userDetails}
	
	<h2>Fundings</h2>
	${fundings}
	<a href="Project?action=new">New Project</a>
		
<%
// User not logged in. Display the login form.
} else {
%>
	<h2>Login</h2>
	<form action="User" method="post">
		<input type="hidden" name="action" value="login" />
		<table>
			<tr>
				<th>Username</th>
				<td><input type="text" name="name" value="" /></td>
			</tr>
			<tr>
				<th>Password</th>
				<td><input type="password" name="password" value="" /></td>
			</tr>
			<tr>
				<th colspan="2">
					<input type="submit" value="Login" />
				</th>
			</tr>
		</table>
	</form>
	
	<a href="User?action=new">Register</a>

<%
}
%>

<%@ include file="../Footer.jsp" %>