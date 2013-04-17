<%@page import="ch.ethz.inf.dbproject.util.UserManagement"%>
<%@page import="ch.ethz.inf.dbproject.model.User"%>
<%@page import="ch.ethz.inf.dbproject.model.Project"%>
<%@page import="ch.ethz.inf.dbproject.ProjectServlet"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../Header.jsp" %>

<% 
	final User user = (User) session.getAttribute(UserManagement.SESSION_USER);
	Project project = (Project)request.getAttribute("project");
	boolean edit = request.getParameter("action").equals("edit");
	
	if (edit) { %>
		<h2>Edit Project</h2>
<% } else { %>
		<h2>New Project</h2>
<% } %>


<form action="Project" method="post">
	<input type="hidden" name="action" value="<%= edit ? "edit" : "new" %>" />
	<input type="hidden" name="user_id" value="<%= user.getId() %>" />
	<% if (edit) { %>
		<input type="hidden" name="id" value="<%= project.getId() %>" />
	<% } %>
	<table>
		<tr>
			<th>Title</th>
			<td><textarea rows="4" cols="50" name="title"><%= edit ? project.getTitle() : "" %></textarea></td>
		</tr>
		<tr>
			<th>Description</th>
			<td><input type="text" name="description" value="<%= edit ? project.getDescription() : "" %>" /></td>
		</tr>
		<tr>
			<th>Category</th>
			<td>${categories_combo}</td>
		</tr>
		<tr>
			<th>City</th>
			<td>${cities_combo}</td>
		</tr>
		<tr>
			<th>Goal</th>
			<td><input type="number" step="0.5" name="goal" value="<%= edit ? project.getGoal() : "" %>" /></td>
		</tr>
		<tr>
			<th>Start</th>
			<td><input type="date" name="start" value="<%= edit ? project.getStart() : "" %>" /></td>
		</tr>
		<tr>
			<th>End</th>
			<td><input type="date" name="end" value="<%= edit ? project.getEnd() : "" %>" /></td>
		</tr>
		<tr>
			<th colspan="2">
				<input type="submit" value=<%= edit ? "Edit" : "Add" %> />
			</th>
		</tr>
	</table>
</form>
<%@ include file="../Footer.jsp" %>