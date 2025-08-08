<%@ page import="in.co.ehealth.care.controller.WelcomeCtl" %>
<%@page import="in.co.ehealth.care.bean.UserBean"%>
<%@page import="in.co.ehealth.care.controller.EHCView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Header</title>
<link href="<%=EHCView.APP_CONTEXT%>/css/style.css" rel="stylesheet">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
	crossorigin="anonymous">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
	crossorigin="anonymous"></script>
	<style type="text/css">
	.linkSize {
		font-size: 14px;
	}
	.navbar ul li a:hover {
    	color: #2D89FF;;
	}
</style>
	<link href="https://use.fontawesome.com/releases/v5.7.2/css/all.css"
	rel="stylesheet">
	
	<link rel="stylesheet" href="//code.jquery.com/ui/1.13.0/themes/base/jquery-ui.css">
  <link rel="stylesheet" href="/resources/demos/style.css">
  <script src="https://code.jquery.com/jquery-3.6.0.js"></script>
  <script src="https://code.jquery.com/ui/1.13.0/jquery-ui.js"></script>
  <script>
  $( function() {
    $( "#datepicker" ).datepicker({
      changeMonth: true,
      changeYear: true
    });
  } );
  </script>
</head>
<body>
	<%
	UserBean userbean = (UserBean) session.getAttribute("user");
	
	boolean userLoggedIn = userbean != null;
	
	String welcomeMsg = "Hi, ";

    if (userLoggedIn) {
        welcomeMsg += userbean.getFirstName() + " (" + userbean.getRoleName() + ")";
    } else {
        welcomeMsg += "Guest";
    }
	%>
	<nav class="navbar navbar-expand-lg trs-header navbar-light" style="background-color: transparent">
		<div class="container-fluid">
			<a class="header-text"
				style="color: #2D89FF;; font-size: 26px; font-weight:600;" href="#"><i class="fas fa-heartbeat"></i>HealthBridge</a>
		</div>
		
		<div class="trs-div-header">
		<ul class="nav justify-content-end">

			<li class="nav-item">
				<a class="nav-link active link-light" aria-current="page" href="<%=EHCView.WELCOME_CTL%>">Home</a>
			</li>
			<%
			if (userbean != null) {
			%>
			<%
			if (userbean.getRoleId() == 1) {
			%>

			<li class="nav-item"><a class="nav-link link-light"
				href="<%=EHCView.PATIENT_LIST_CTL%>">Patients</a></li>
				
			<li class="nav-item dropdown">
				<a
					class="nav-link dropdown-toggle link-light" href="#" id="navbarDropdown"
					role="button" data-bs-toggle="dropdown" aria-expanded="false">Doctor
				</a>
				<ul class="dropdown-menu" aria-labelledby="navbarDropdown">
					<li><a class="dropdown-item" href="<%=EHCView.DOCTOR_CTL%>">Add Doctor</a></li>
					<li><a class="dropdown-item" href="<%=EHCView.DOCTOR_LIST_CTL%>">Doctor List</a></li>
				
				</ul></li>
				
				<li class="nav-item dropdown"><a
				class="nav-link dropdown-toggle link-light" href="#" id="navbarDropdown"
				role="button" data-bs-toggle="dropdown" aria-expanded="false">
					Staff</a>
				<ul class="dropdown-menu" aria-labelledby="navbarDropdown">
					<li><a class="dropdown-item" href="<%=EHCView.STAFF_CTL%>">Add Staff</a></li>
					<li><a class="dropdown-item" href="<%=EHCView.STAFF_LIST_CTL%>">Staff List</a></li>
				
				</ul></li>
				
				<li class="nav-item dropdown"><a
				class="nav-link dropdown-toggle link-light" href="#" id="navbarDropdown"
				role="button" data-bs-toggle="dropdown" aria-expanded="false">
					Medicine</a>
				<ul class="dropdown-menu" aria-labelledby="navbarDropdown">
					<li><a class="dropdown-item" href="<%=EHCView.MEDICINE_CTL%>">Add Medicine</a></li>
					<li><a class="dropdown-item" href="<%=EHCView.MEDICINE_LIST_CTL%>">Medicine List</a></li>
				
				</ul></li>

			
			
			<%
			}else
			%>
			
			<%
			if (userbean.getRoleId() == 2) {
			%>
			<li class="nav-item dropdown"><a
				class="nav-link dropdown-toggle link-light" href="#" id="navbarDropdown"
				role="button" data-bs-toggle="dropdown" aria-expanded="false">
					Schedule Time</a>
				<ul class="dropdown-menu" aria-labelledby="navbarDropdown">
					<li><a class="dropdown-item" href="<%=EHCView.SCHEDULE__DOCTOR_CTL%>">Add Schedule</a></li>
					<li><a class="dropdown-item" href="<%=EHCView.SCHEDULE_DOCTOR_LIST_CTL%>">Schedule List</a></li>
				
				</ul></li>
				
				<li class="nav-item"><a class="nav-link link-light"
				href="<%=EHCView.PRESCRIPTION_LIST_CTL%>">Prescription</a></li>
				
				<li class="nav-item"><a class="nav-link link-light"
				href="<%=EHCView.TEST_REPORT_LIST_CTL%>">Test Report</a></li>
				
			<%} %>
			
			<%
			if (userbean.getRoleId() == 4) {
			%>
			
			<li class="nav-item"><a class="nav-link link-light"
				href="<%=EHCView.DOCTOR_LIST_CTL%>">Doctor</a></li>
				
			<li class="nav-item"><a class="nav-link link-light"
				href="<%=EHCView.SCHEDULE_DOCTOR_LIST_CTL%>">Doctor Timing</a></li>
			
				<li class="nav-item"><a class="nav-link link-light"
				href="<%=EHCView.APPOINTMENT_LIST_CTL%>">Appointments</a></li>
				
				<li class="nav-item"><a class="nav-link link-light"
				href="<%=EHCView.PRESCRIPTION_LIST_CTL%>">Prescription</a></li>
				
				<li class="nav-item"><a class="nav-link link-light"
				href="<%=EHCView.MEDICINE_LIST_CTL%>">Medicine</a></li>
				
				<li class="nav-item"><a class="nav-link link-light"
				href="<%=EHCView.TEST_REPORT_LIST_CTL%>">Test Report</a></li>
				
			
			<%} %>
			
			<%
			if (userbean.getRoleId() == 3) {
			%>
			
				<li class="nav-item"><a class="nav-link link-light"
				href="<%=EHCView.DOCTOR_LIST_CTL%>">Doctor</a></li>
				
				<li class="nav-item dropdown"><a
				class="nav-link dropdown-toggle link-light" href="#" id="navbarDropdown"
				role="button" data-bs-toggle="dropdown" aria-expanded="false">
					Test Report</a>
				<ul class="dropdown-menu" aria-labelledby="navbarDropdown">
					<li><a class="dropdown-item" href="<%=EHCView.TEST_REPORT_CTL%>">Add Test Report</a></li>
					<li><a class="dropdown-item" href="<%=EHCView.TEST_REPORT_LIST_CTL%>">Test Report List</a></li>
				</ul></li>
			<%} %>


			<li class="nav-item dropdown"><a
				class="nav-link dropdown-toggle link-light" href="#" id="navbarDropdown"
				role="button" data-bs-toggle="dropdown" aria-expanded="false">
					<%=welcomeMsg%></a>
				<ul class="dropdown-menu" aria-labelledby="navbarDropdown">
					<li><a class="dropdown-item" href="<%=EHCView.MY_PROFILE_CTL%>">Profile</a></li>
					<li><a class="dropdown-item" href="<%=EHCView.CHANGE_PASSWORD_CTL%>">Change Password</a></li>
					<li><a class="dropdown-item" href="<%=EHCView.LOGIN_CTL%>?operation=logout">Logout</a></li>
				
				</ul></li>
				
				
			
			<%
			} else {
			%>
			<li class="nav-item"><a class="nav-link link-light"
				href="<%=EHCView.LOGIN_CTL%>">Login</a></li>
			<li class="nav-item"><a class="nav-link link-light"
				href="<%=EHCView.PATIENT_CTL%>">Sign Up</a></li>
			<li class="nav-item"><a class="nav-link link-light" href="<%=EHCView.APP_CONTEXT+EHCView.ABOUT_US_VIEW%>">About
					Us</a></li>
			<li class="nav-item"><a class="nav-link link-light" href="<%=EHCView.APP_CONTEXT+EHCView.CONTACT_US_VIEW%>">Contact
					Us</a></li>
			<%
			}
			%>
		</ul>
	</div>
	</nav>
	
</body>
</html>