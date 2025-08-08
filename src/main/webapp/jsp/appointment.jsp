
<%@page import="in.co.ehealth.care.controller.AppointmentCtl"%>
<%@page import="in.co.ehealth.care.util.HTMLUtility"%>
<%@page import="java.util.List"%>
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
<title>Appointment</title>
<!-- Fonts & Icons -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
<link href="https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@700&display=swap" rel="stylesheet">

<!-- CSS -->
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/navbar.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/styles.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/home.css">
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
					<i class="fa fa-arrow-right" aria-hidden="true"></i> Appointment
				</li>
			</ol>
		</nav>
	</div>
	<hr>

	<div class="wrapper rounded bg-white">
		<div class="h3">Appointment</div>
		<b><font color="red"> <%=ServletUtility.getErrorMessage(request)%>
		</font></b> <b><font color="Green"> <%=ServletUtility.getSuccessMessage(request)%>
		</font></b>
		<hr>
		<form method="post" action="<%=EHCView.APPOINTMENT_CTL%>">

			<jsp:useBean id="bean" class="in.co.ehealth.care.bean.AppointmentBean"
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
					<div class="col-md-12 mt-md-0 mt-3">
						<label>Date<font color="red">*</font></label>
							<input type="text" id="datepicker" name="date" class="form-control"
  								  placeholder="Select Date" readonly="readonly"
   								  value="<%=DataUtility.getDateString(bean.getDate())%>">
						<font color="red"><%=ServletUtility.getErrorMessage("date", request)%></font>
					</div>
				</div>
				
				<div class="row">
					<div class="col-md-12 mt-md-0 mt-3">
						<label>DoctorName<font color="red">*</font></label> <input
							type="text" placeholder="Enter Doctor Name here..."
							name="doctorName" class="form-control" readonly="readonly"
							value="<%=DataUtility.getStringData(bean.getDoctorName())%>">
						<font color="red"><%=ServletUtility.getErrorMessage("doctorName", request)%></font>
					</div>
				</div>
				
				<div class="row">
					<div class="col-md-12 mt-md-0 mt-3">
						<label>Name<font color="red">*</font></label> <input
							type="text" placeholder="Enter Patient Name here..."
							name="patientName" class="form-control" readonly="readonly"
							value="<%=DataUtility.getStringData(bean.getPatientName())%>">
						<font color="red"><%=ServletUtility.getErrorMessage("patientName", request)%></font>
					</div>
				</div>
				

				<div class="row">
					<div class="col-md-12 mt-md-0 mt-3">
						<label>Symptoms<font color="red">*</font></label> <textarea
							 placeholder="Enter Symptoms here..." name="allergy"
							class="form-control"
							><%=DataUtility.getStringData(bean.getAllergy())%></textarea>
						<font color="red"><%=ServletUtility.getErrorMessage("allergy", request)%></font>
					</div>
				</div>

				<input type="submit" name="operation"
					value="<%=AppointmentCtl.OP_SAVE%>"
					class="btn btn-primary mt-3" />or<input type="submit" name="operation"
					value="<%=AppointmentCtl.OP_RESET%>"
					class="btn btn-primary mt-3" />
			</div>
		</form>
	</div>
	<%@ include file="footer.jsp"%>
</body>
</html>