<%@page import="ch.ethz.inf.dbproject.UserServlet"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="Header.jsp" %>

<h2>Your Account</h2>

<% 
if ((Boolean) session.getAttribute(UserServlet.SESSION_USER_LOGGED_IN)) {
	// User is logged in. Display the details:
%>
	
<%= session.getAttribute(UserServlet.SESSION_USER_DETAILS) %>
	


<%
//TODO: Display funded projects

//TODO: Add possibility to create new projects (requires a form) 
%>
	<form action="Project" method="post">
		<input type="hidden" name="action" value="new" />
		<table>
			<tr>
				<th>Title</th>
				<td><input type="text" name="title" value="" /></td>
			</tr>
			<tr>
				<th>Description</th>
				<td><input type="text" name="description" value="" /></td>
			</tr>
			<tr>
				<th>Category</th>
				<td><%= session.getAttribute(UserServlet.SESSION_CATEGORIES) %></td>
			</tr>
			<tr>
				<th>City</th>
				<td><%= session.getAttribute(UserServlet.SESSION_CITIES) %></td>
			</tr>
			<tr>
				<th>Goal</th>
				<td><input type="number" name="goal" value="" /></td>
			</tr>
			<tr>
				<th>Start</th>
				<td><input type="date" name="start" value="" /></td>
			</tr>
			<tr>
				<th>End</th>
				<td><input type="date" name="end" value="" /></td>
			</tr>
			<tr>
				<th colspan="2">
					<input type="submit" value="Add" />
				</th>
			</tr>
		</table>
	</form>

<%
	
} else {
	// User not logged in. Display the login form.
%>

	<form action="User" method="get">
		<input type="hidden" name="action" value="login" />
		<table>
			<tr>
				<th>Username</th>
				<td><input type="text" name="username" value="" /></td>
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

<%
}
%>

<%@ include file="Footer.jsp" %>