
<%@page import="in.co.ehealth.care.controller.ChangePasswordCtl"%>
<%@page import="in.co.ehealth.care.controller.MyProfileCtl"%>
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
<title>Change Password</title>
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
					<i class="fa fa-arrow-right" aria-hidden="true"></i>Change Password
				</li>
			</ol>
		</nav>
	</div>
	<hr>

	<div class="wrapper rounded bg-white">
		<div class="h3">Change Password</div>
		<b><font color="red"> <%=ServletUtility.getErrorMessage(request)%>
		</font></b> <b><font color="Green"> <%=ServletUtility.getSuccessMessage(request)%>
		</font></b>
		<hr>
		<form method="post" action="<%=EHCView.CHANGE_PASSWORD_CTL%>">

			<jsp:useBean id="bean" class="in.co.ehealth.care.bean.UserBean"
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
						<label>Old Password<font color="red">*</font></label> <input
							type="password" placeholder="Enter Old Password here..."
							name="oldPassword" class="form-control"
							value=<%=DataUtility
                    .getString(request.getParameter("oldPassword") == null ? ""
                            : DataUtility.getString(request
                                    .getParameter("oldPassword")))%>>
						<font color="red"><%=ServletUtility.getErrorMessage("oldPassword", request)%></font>
					</div>
				</div>

				<div class="row">
					<div class="col-md-6 mt-md-0 mt-3">
						<label>New Password<font color="red">*</font></label> <input
							type="password" placeholder="Enter New Password here..."
							name="newPassword" class="form-control"
							value=<%=DataUtility.getString(request.getParameter("newPassword") == null ? ""
                            : DataUtility.getString(request.getParameter("newPassword")))%>>
						<font color="red"><%=ServletUtility.getErrorMessage("newPassword", request)%></font>
					</div>
					
					
					<div class="col-md-6 mt-md-0 mt-3">
						<label>Confirm Password<font color="red">*</font></label> <input
							type="password" placeholder="Enter Confirm Password here..."
							name="confirmPassword" class="form-control"
							value=<%=DataUtility.getString(request
                    .getParameter("confirmPassword") == null ? "" : DataUtility
                    .getString(request.getParameter("confirmPassword")))%>>
						<font color="red"><%=ServletUtility.getErrorMessage("confirmPassword", request)%></font>
					</div>
				</div>
				
				<input type="submit" name="operation"
					value="<%=ChangePasswordCtl.OP_SAVE%>"
					class="btn btn-primary mt-3" />
			</div>
		</form>
	</div>
	<%@ include file="footer.jsp"%>
</body>
</html>