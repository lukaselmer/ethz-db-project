<!DOCTYPE html>
<%@page import="ch.ethz.inf.dbproject.model.access.CategoryAccess"%>
<%@page import="ch.ethz.inf.dbproject.model.access.DatastoreInterface"%>
<%@page import="java.util.List"%>
<%@page import="ch.ethz.inf.dbproject.model.Category"%>
<html>
	
	<head>
	    <meta charset="utf-8"/>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
        
		<meta http-equiv="Pragma" content="cache"/>
		<meta name="robots" content="index, follow"/>
		<meta http-equiv="Language" content="en"/>
		<meta http-equiv="Content-Style-Type" content="text/css"/>
  
        <link href="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.1/css/bootstrap-combined.min.css" rel="stylesheet">
        <!-- <link href="//netdna.bootstrapcdn.com/bootswatch/2.3.0/cerulean/bootstrap.min.css" rel="stylesheet"> -->
	    <link href="style.css" rel="stylesheet">
        
        
	    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
	    <!--[if lt IE 9]>
	      <script src="../assets/js/html5shiv.js"></script>
	    <![endif]-->
        
		<title>Online Crowd Funding</title>
		
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
	</head>

	<body>


	    <div class="navbar navbar-inverse navbar-fixed-top">
	      <div class="navbar-inner">
	        <div class="container">
	          <button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
	            <span class="icon-bar"></span>
	            <span class="icon-bar"></span>
	            <span class="icon-bar"></span>
	          </button>
	          <a class="brand" href="#">CrowdStarter</a>
	          <div class="nav-collapse collapse">
	            <ul class="nav">
	              <li class="active"><a href="Home">Home</a></li>
	              <li><a href="Projects">All Projects</a></li>
	              
	              <li class="dropdown">
	                <a href="#" class="dropdown-toggle" data-toggle="dropdown">Filter <b class="caret"></b></a>
	                <ul class="dropdown-menu">
	              		<li><a href="Projects?filter=popular">Most popular</a></li>
	              		<li><a href="Projects?filter=funded">Most funded</a></li>
	             		<li><a href="Projects?filter=ending">Ending soon</a></li>
	                </ul>
	              </li>
	              
	              <li class="dropdown">
	                <a href="#" class="dropdown-toggle" data-toggle="dropdown">Categories <b class="caret"></b></a>
	                <ul class="dropdown-menu">
	                <%
	                	for (Category cat : CategoryAccess.getInstance().getAllCategories()) {
	                		%><li><a href="Projects?category=<%= cat.getId() %>"><%= cat.getName() %></a></li><%
	                	}
	                %>
	                  <li class="divider"></li>
	                  <li><a href="Category">Manage Categories</a></li>
	                  <li><a href="Category?action=new">New Category</a></li>
	                </ul>
	              </li>
	              <li class="dropdown">
	                <a href="#" class="dropdown-toggle" data-toggle="dropdown">Cities <b class="caret"></b></a>
	                <ul class="dropdown-menu">
	                  <li><a href="City">Manage Cities</a></li>
	                  <li><a href="City?action=new">New City</a></li>
	                </ul>
	              </li>
	              <li><a href="Search">Search</a></li>
	              <li><a href="User">User Profile</a></li>
	            </ul>
	            <!-- <form class="navbar-form pull-right">
	              <input class="span2" type="text" placeholder="Email">
	              <input class="span2" type="password" placeholder="Password">
	              <button type="submit" class="btn">Sign in</button>
	            </form> -->
	          </div>
	        </div>
	      </div>
	    </div>

				
		
        <div class="container main-content">
        	<div class="row">
        	
				