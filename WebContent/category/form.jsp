<%@page import="ch.ethz.inf.dbproject.CategoryServlet"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../Header.jsp" %>

<% Category category = (Category)request.getAttribute("category"); %>
<% boolean edit = request.getParameter("action").equals("edit"); %>
<%  if (edit) { %>
		<h2>Edit Category</h2>
<% } else { %>
		<h2>New Category</h2>
<% } %>

<form action="Category" method="post">
	<input type="hidden" name="action" value="<%= edit ? "edit" : "new" %>" />
	<% if (edit) { %>
		<input type="hidden" name="id" value="<%= category.getId() %>" />
	<% } %>
	<table>
		<tr>
			<th>Name</th>
			<td><input type="text" name="name" value="<%= edit ? category.getName() : "" %>" /></td>
		</tr>
		<tr>
			<th colspan="2">
				<input type="submit" value=<%= edit ? "Edit" : "Add" %> />
			</th>
		</tr>
	</table>
</form>

<%@ include file="../Footer.jsp" %>