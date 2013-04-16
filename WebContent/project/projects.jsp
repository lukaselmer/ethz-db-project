<%@ page import="ch.ethz.inf.dbproject.ProjectsServlet"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../Header.jsp" %>

<h1>Projects</h1>

<%= request.getAttribute("projects") %>

<%@ include file="../Footer.jsp" %>