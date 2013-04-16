<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="Header.jsp" %>

<h1>Search</h1>

<hr/>

<form method="get" action="Search">
<div>
	<select name="filter">
		<option value="title">Title</option>
		<option value="category">Category</option>
		<option value="city">City</option>
	</select>
	<input type="text" name="search" />
	<input type="submit" value="Search" title="Search" />
</div>
</form>

<hr/>
${results}
<hr/>

<%@ include file="Footer.jsp" %>