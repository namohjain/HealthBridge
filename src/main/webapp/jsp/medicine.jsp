<%@page import="in.co.ehealth.care.controller.MedicineCtl"%>
<%@page import="java.util.HashMap"%>
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
<title>Medicine</title>
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
					<i class="fa fa-arrow-right" aria-hidden="true"></i> Medicine
				</li>
			</ol>
		</nav>
	</div>
	<hr>

	<div class="wrapper rounded bg-white">
		<div class="h3">Medicine</div>
		<b><font color="red"> <%=ServletUtility.getErrorMessage(request)%>
		</font></b> <b><font color="Green"> <%=ServletUtility.getSuccessMessage(request)%>
		</font></b>
		<hr>
		<form method="post" action="<%=EHCView.MEDICINE_CTL%>">

			<jsp:useBean id="bean" class="in.co.ehealth.care.bean.MedicineBean"
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
						<label>Name<font color="red">*</font></label> <input
							type="text" placeholder="Enter Name here..."
							name="name" class="form-control"
							value="<%=DataUtility.getStringData(bean.getName())%>">
						<font color="red"><%=ServletUtility.getErrorMessage("name", request)%></font>
					</div>
				</div>
				
				<div class="row">
					<div class="col-md-12 mt-md-0 mt-3">
						<label>Company Name<font color="red">*</font></label> <input
							type="text" placeholder="Enter Comapany Name here..."
							name="cName" class="form-control"
							value="<%=DataUtility.getStringData(bean.getCompanyName())%>">
						<font color="red"><%=ServletUtility.getErrorMessage("cName", request)%></font>
					</div>
				</div>
				
				<div class="row">
					<div class="col-md-12 mt-md-0 mt-3">
						<label>Price<font color="red">*</font></label> <input
							type="text" placeholder="Enter Price here..."
							name="price" class="form-control"
							value="<%=DataUtility.getStringData(bean.getPrice())%>">
						<font color="red"><%=ServletUtility.getErrorMessage("price", request)%></font>
					</div>
				</div>
				
				<% 
					HashMap<String,String> map=new HashMap<String,String>();
					map.put("Available", "Available");
					map.put("UnAvailable","UnAvailable");
					
				%>
				
				<div class="row">
					<div class="col-md-12 mt-md-0 mt-3">
						<label>Status<font color="red">*</font></label> 
						<%=HTMLUtility.getList("status", String.valueOf(bean.getStatus()), map)%>
					<font color="red"><%=ServletUtility.getErrorMessage("status", request)%></font>
					</div>
				</div>

				<div class="row">
					<div class="col-md-12 mt-md-0 mt-3">
						<label>Description<font color="red">*</font></label> <textarea
							 placeholder="Enter Description here..." name="description"
							class="form-control"
							><%=DataUtility.getStringData(bean.getDescription())%></textarea>
						<font color="red"><%=ServletUtility.getErrorMessage("description", request)%></font>
					</div>
				</div>

				<input type="submit" name="operation"
					value="<%=MedicineCtl.OP_SAVE%>"
					class="btn btn-primary mt-3" />or<input type="submit" name="operation"
					value="<%=MedicineCtl.OP_RESET%>"
					class="btn btn-primary mt-3" />
			</div>
		</form>
	</div>
	<%@ include file="footer.jsp"%>
</body>
</html>