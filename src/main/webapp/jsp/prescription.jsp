<%@page import="in.co.ehealth.care.controller.PrescriptionCtl"%>
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
<title>Prescription</title>
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
					<i class="fa fa-arrow-right" aria-hidden="true"></i> Prescription
				</li>
			</ol>
		</nav>
	</div>
	<hr>

	<div class="wrapper rounded bg-white">
		<div class="h3">Prescription</div>
		<b><font color="red"> <%=ServletUtility.getErrorMessage(request)%>
		</font></b> <b><font color="Green"> <%=ServletUtility.getSuccessMessage(request)%>
		</font></b>
		<hr>
		<form method="post" action="<%=EHCView.PRESCRIPTION_CTL%>">

			<jsp:useBean id="bean" class="in.co.ehealth.care.bean.PrescriptionBean"
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
						<label>Date<font color="red">*</font></label> <input
							type="text" placeholder="Enter Date here..." id="datepicker"
							name="date" class="form-control" 
							value="<%=DataUtility.getDateString(bean.getDate())%>">
						<font color="red"><%=ServletUtility.getErrorMessage("date", request)%></font>
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
						<label>Prescription<font color="red">*</font></label> <textarea
							 placeholder="Enter Prescription here..." name="prescription"
							class="form-control"
							><%=DataUtility.getStringData(bean.getPrescription())%></textarea>
						<font color="red"><%=ServletUtility.getErrorMessage("prescription", request)%></font>
					</div>
				</div>

				<input type="submit" name="operation"
					value="<%=PrescriptionCtl.OP_SAVE%>"
					class="btn btn-primary mt-3" />or<input type="submit" name="operation"
					value="<%=PrescriptionCtl.OP_RESET%>"
					class="btn btn-primary mt-3" />
			</div>
		</form>
	</div>
	<%@ include file="footer.jsp"%>
</body>
</html>