<%@page import="ch.ethz.inf.dbproject.model.User"%>
<%@page import="ch.ethz.inf.dbproject.UserServlet"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../Header.jsp" %>

<% User user = (User)request.getAttribute("user"); %>
<% boolean edit = request.getParameter("action").equals("edit"); %>
<%  if (edit) { %>
		<h2>Edit User</h2>
<% } else { %>
		<h2>New User</h2>
<% } %>


<form action="User" method="post">
	<input type="hidden" name="action" value="<%= edit ? "edit" : "new" %>" />
	<% if (edit) { %>
		<input type="hidden" name="id" value="<%= user.getId() %>" />
	<% } %>
	<table>
		<tr>
			<th>Name</th>
			<td><input type="text" name="name" value="<%= edit ? user.getName() : "" %>" /></td>
		</tr>
		<tr>
			<th>Password</th>
			<td><input type="password" name="password" value="<%= edit ? user.getPassword() : "" %>" /></td>
		</tr>
		<tr>
			<th colspan="2">
				<input type="submit" value=<%= edit ? "Edit" : "Add" %> />
			</th>
		</tr>
	</table>
</form>
<%@ include file="../Footer.jsp" %>