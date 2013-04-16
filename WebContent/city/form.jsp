<%@page import="ch.ethz.inf.dbproject.model.City"%>
<%@page import="ch.ethz.inf.dbproject.CityServlet"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../Header.jsp" %>

<% City city = (City)request.getAttribute("city"); %>
<% boolean edit = request.getParameter("action").equals("edit"); %>
<%  if (edit) { %>
		<h2>Edit City</h2>
<% } else { %>
		<h2>New City</h2>
<% } %>


<form action="City" method="post">
	<input type="hidden" name="action" value="<%= edit ? "edit" : "new" %>" />
	<% if (edit) { %>
		<input type="hidden" name="id" value="<%= city.getId() %>" />
	<% } %>
	<table>
		<tr>
			<th>Name</th>
			<td><input type="text" name="name" value="<%= edit ? city.getName() : "" %>" /></td>
		</tr>
		<tr>
			<th colspan="2">
				<input type="submit" value=<%= edit ? "Edit" : "Add" %> />
			</th>
		</tr>
	</table>
</form>

<%@ include file="../Footer.jsp" %>