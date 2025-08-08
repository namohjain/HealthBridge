
<%@page import="in.co.ehealth.care.controller.PatientCtl"%>
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
<title>Patient Registration</title>

<!-- Fonts & Icons -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
<link href="https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@700&display=swap" rel="stylesheet">

<!-- CSS -->
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/navbar.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/styles.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/home.css">
<link rel="stylesheet" href="<%=EHCView.APP_CONTEXT%>/css/login.css">
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
					<i class="fa fa-arrow-right" aria-hidden="true"></i>Patient Registration
				</li>
			</ol>
		</nav>
	</div>
	<hr>

	<div class="wrapper rounded bg-white">
		<div class="h3">Patient Registration</div>
		<b><font color="red"> <%=ServletUtility.getErrorMessage(request)%>
		</font></b> <b><font color="Green"> <%=ServletUtility.getSuccessMessage(request)%>
		</font></b>
		<hr>
		<form method="post" action="<%=EHCView.PATIENT_CTL%>">

			<jsp:useBean id="bean" class="in.co.ehealth.care.bean.PatientBean"
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
							type="text" placeholder="Enter DOB here..." id="datepicker" name="dob"
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
						<label>City<font color="red">*</font></label> <input
							type="text" placeholder="Enter City here..." name="city"
							class="form-control"
							value="<%=DataUtility.getStringData(bean.getCity())%>">
						<font color="red"><%=ServletUtility.getErrorMessage("city", request)%></font>
					</div>
				</div>
				
				<div class="row">
					<div class="col-md-12 mt-md-0 mt-3">
						<label>Address<font color="red">*</font></label> <textarea
							 placeholder="Enter Address here..." name="address"
							class="form-control"
							><%=DataUtility.getStringData(bean.getAddress())%></textarea>
						<font color="red"><%=ServletUtility.getErrorMessage("address", request)%></font>
					</div>

					
				</div>

				<input type="submit" name="operation"
					value="<%=PatientCtl.OP_SAVE%>"
					class="btn btn-primary mt-3" />or<input type="submit" name="operation"
					value="<%=PatientCtl.OP_RESET%>"
					class="btn btn-primary mt-3" />
			</div>
		</form>
	</div>
	<%@ include file="footer.jsp"%>
</body>
</html>