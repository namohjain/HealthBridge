
<%@page import="in.co.ehealth.care.controller.StaffCtl"%>
<%@page import="in.co.ehealth.care.controller.DoctorCtl"%>
<%@page import="in.co.ehealth.care.controller.UserRegistrationCtl"%>
<%@page import="in.co.ehealth.care.util.DataUtility"%>
<%@page import="in.co.ehealth.care.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Staff</title>
<link href="<%=EHCView.APP_CONTEXT%>/css/login.css" rel="stylesheet">
</head>
<body>
	<%@ include file="header.jsp"%>

	<br>
	<div class="container">
		<nav aria-label="breadcrumb">
			<ol class="breadcrumb">
				<li class="breadcrumb-item linkSize"><i
					class="fas fa-tachometer-alt"></i> <a class="link-dark"
					href="<%=EHCView.WELCOME_CTL%>">Home</a></li>
				<li class="breadcrumb-item linkSize active" aria-current="page">
					<i class="fa fa-arrow-right" aria-hidden="true"></i> Staff
				</li>
			</ol>
		</nav>
	</div>
	<hr>

	<div class="wrapper rounded bg-white">
		<div class="h3">Staff</div>
		<b><font color="red"> <%=ServletUtility.getErrorMessage(request)%>
		</font></b> <b><font color="Green"> <%=ServletUtility.getSuccessMessage(request)%>
		</font></b>
		<hr>
		<form method="post" action="<%=EHCView.STAFF_CTL%>">

			<jsp:useBean id="bean" class="in.co.ehealth.care.bean.StaffBean"
				scope="request"></jsp:useBean>

			<input type="hidden" name="id" value="<%=bean.getId()%>"> <input
				type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
			<input type="hidden" name="modifiedBy"
				value="<%=bean.getModifiedBy()%>"> <input type="hidden"
				name="createdDatetime"
				value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
			<input type="hidden" name="modifiedDatetime"
				value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">
			<div class="form">
				<div class="row">
					<div class="col-md-6 mt-md-0 mt-3">
						<label>First Name<font color="red">*</font></label> <input
							type="text" placeholder="Enter First Name here..."
							name="firstName" class="form-control"
							value="<%=DataUtility.getStringData(bean.getFirstName())%>">
						<font color="red"><%=ServletUtility.getErrorMessage("firstName", request)%></font>
					</div>

					<div class="col-md-6 mt-md-0 mt-3">
						<label>Last Name<font color="red">*</font></label> <input
							type="text" placeholder="Enter Last Name here..." name="lastName"
							class="form-control"
							value="<%=DataUtility.getStringData(bean.getLastName())%>">
						<font color="red"><%=ServletUtility.getErrorMessage("lastName", request)%></font>
					</div>

				</div>

				<div class="row">
					<div class="col-md-6 mt-md-0 mt-3">
						<label>User Name<font color="red">*</font></label> <input
							type="text" placeholder="Enter User Name Name here..."
							name="userName" class="form-control"
							value="<%=DataUtility.getStringData(bean.getUserName())%>">
						<font color="red"><%=ServletUtility.getErrorMessage("userName", request)%></font>
					</div>
					<div class="col-md-6 mt-md-0 mt-3">
						<label>Password<font color="red">*</font></label> <input
							type="password" placeholder="Enter Password Name here..."
							name="password" class="form-control"
							value="<%=DataUtility.getStringData(bean.getPassword())%>">
						<font color="red"> <%=ServletUtility.getErrorMessage("password", request)%></font>
					</div>
				</div>

				<div class="row">
					<div class="col-md-6 mt-md-0 mt-3">
						<label>Contact Number<font color="red">*</font></label> <input
							type="text" placeholder="Enter Contact No here..."
							name="contactNo" class="form-control"
							value="<%=DataUtility.getStringData(bean.getContactNo())%>">
						<font color="red"><%=ServletUtility.getErrorMessage("contactNo", request)%></font>
					</div>

					<div class="col-md-6 mt-md-0 mt-3">
						<label>Date of Birth<font color="red">*</font></label> <input
							type="text" placeholder="Enter DOB here..." name="dob"
							class="form-control"
							value="<%=DataUtility.getDateString(bean.getDob())%>">
						<font color="red"><%=ServletUtility.getErrorMessage("dob", request)%></font>
					</div>
				</div>

				<div class="row">
					<div class="col-md-6 mt-md-0 mt-3">
						<label>Email Id<font color="red">*</font></label> <input
							type="text" placeholder="Enter Email Id here..." name="email"
							class="form-control"
							value="<%=DataUtility.getStringData(bean.getEmail())%>">
						<font color="red"><%=ServletUtility.getErrorMessage("email", request)%></font>
					</div>

					<div class="col-md-6 mt-md-0 mt-3">
						<label>Experience<font color="red">*</font></label> <input
							type="text" placeholder="Enter Experience here..." name="experience"
							class="form-control"
							value="<%=DataUtility.getStringData(bean.getExperience())%>">
						<font color="red"><%=ServletUtility.getErrorMessage("experience", request)%></font>
					</div>
				</div>
				
				<div class="row">
					<div class="col-md-12 mt-md-0 mt-3">
						<label>Qualification<font color="red">*</font></label> <textarea
							 placeholder="Enter Qualification here..." name="qualification"
							class="form-control"
							><%=DataUtility.getStringData(bean.getQualification())%></textarea>
						<font color="red"><%=ServletUtility.getErrorMessage("qualification", request)%></font>
					</div>

					
				</div>

				<input type="submit" name="operation"
					value="<%=StaffCtl.OP_SAVE%>"
					class="btn btn-primary mt-3" />or<input type="submit" name="operation"
					value="<%=StaffCtl.OP_RESET%>"
					class="btn btn-primary mt-3" />
			</div>
		</form>
	</div>
	<%@ include file="footer.jsp"%>
</body>
</html>