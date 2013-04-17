<%@page import="ch.ethz.inf.dbproject.model.Project"%>
<%@ page import="ch.ethz.inf.dbproject.ProjectServlet"%>
<%@ page import="ch.ethz.inf.dbproject.model.User"%>
<%@ page import="ch.ethz.inf.dbproject.util.UserManagement"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	final User user = (User) session.getAttribute(UserManagement.SESSION_USER);
	final Project project = (Project) request.getAttribute("project");
%>

<%@ include file="../Header.jsp" %>

<h1>Project Detail</h1>
${projectTable}


<h2>Stretch Goals</h2>
${goalsTable}
<% 
	// If Project belongs to user, he can add a funding_amount
	if (user != null && project.getUser_id() == user.getId()) { %>
		<form action="Project" method="post">
		<input type="hidden" name="action" value="new_stretch_goal" />
		<input type="hidden" name="project_id" value="${project_id}" />
		<table>
			<tr>
				<th>Bonus</th>
				<td><input type="text" name="bonus" value="" /></td>
			</tr>
			<tr>
				<th>Amount</th>
				<td><input type="number" step="0.05" name="amount" value="" /></td>
			</tr>
			<tr>
				<th colspan="2">
					<input type="submit" value="Add Stretch Goal" />
				</th>
			</tr>
		</table>
</form>
<% 	} %>

<h2>Funding Amounts</h2>
${amountTable}
<% 
	// If Project belongs to user, he can add a funding_amount
	if (user != null && project.getUser_id() == user.getId()) { %>
		<form action="Project" method="post">
		<input type="hidden" name="action" value="new_funding_amount" />
		<input type="hidden" name="project_id" value="${project_id}" />
		<table>
			<tr>
				<th>Reward</th>
				<td><input type="text" name="reward" value="" /></td>
			</tr>
			<tr>
				<th>Amount</th>
				<td><input type="number" step="0.05" name="amount" value="" /></td>
			</tr>
			<tr>
				<th colspan="2">
					<input type="submit" value="Add Funding Amount" />
				</th>
			</tr>
		</table>
</form>
<% 	} %>

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