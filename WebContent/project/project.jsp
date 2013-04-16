<%@ page import="ch.ethz.inf.dbproject.ProjectServlet"%>
<%@ page import="ch.ethz.inf.dbproject.model.User"%>
<%@ page import="ch.ethz.inf.dbproject.util.UserManagement"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	final User user = (User) session.getAttribute(UserManagement.SESSION_USER);
%>

<%@ include file="../Header.jsp" %>

<h1>Project Detail</h1>
${projectTable}

<h2>Funding Amounts</h2>
${amountTable}

<%
	if (user != null) {
		// User is logged in. He can add a fund
%>
	<form action="Project" method="post">
		<input type="hidden" name="action" value="fund" /> 
		${fundingAmountCombo}
		<input type="submit" value="Fund" />
	</form>
<%
	}
%>


<h2>Comments</h2>
${commentTable}

<%
	if (user != null) {
		// User is logged in. He can add a comment
%>
	<form action="Project" method="post">
		<input type="hidden" name="action" value="add_comment" /> 
		<input type="hidden" name="project_id" value="${project_id}" /> 
		<b>Add Comment</b> <br />
		<textarea rows="4" cols="50" name="comment"></textarea><br /> 
		<input type="submit" value="Add Comment" />
	</form>
<%
	}
%>

<%@ include file="../Footer.jsp" %>