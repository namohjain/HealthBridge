
<%@page import="in.co.ehealth.care.controller.UserRegistrationCtl"%>
<%@page import="in.co.ehealth.care.util.DataUtility"%>
<%@page import="in.co.ehealth.care.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Registration</title>
<link href="<%=EHCView.APP_CONTEXT%>/css/login.css" rel="stylesheet">
</head>
<body>
	<%@ include file="header.jsp"%>
	<div class="wrapper rounded bg-white">
		<div class="h3">Registration Form</div>
		<b><font color="red"> <%=ServletUtility.getErrorMessage(request)%>
		</font></b> <b><font color="Green"> <%=ServletUtility.getSuccessMessage(request)%>
		</font></b>
		<hr>
		<form method="post" action="<%=EHCView.USER_REGISTRATION_CTL%>">
		 
		 <jsp:useBean id="bean" class="in.co.ehealth.care.bean.UserBean"
            scope="request"></jsp:useBean>
            
				<input type="hidden" name="id" value="<%=bean.getId()%>">
              <input type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
              <input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy()%>">
              <input type="hidden" name="createdDatetime" value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
              <input type="hidden" name="modifiedDatetime" value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">
		<div class="form">
			<div class="row">
				<div class="col-md-6 mt-md-0 mt-3">
					<label>First Name<font color="red">*</font></label> <input type="text" placeholder="Enter First Name here..." name="firstName" class="form-control" value="<%=DataUtility.getStringData(bean.getFirstName())%>">
					<font  color="red"><%=ServletUtility.getErrorMessage("firstName", request)%></font>
				</div>
				
				<div class="col-md-6 mt-md-0 mt-3">
					<label>Last Name<font color="red">*</font></label> <input type="text" placeholder="Enter Last Name here..." name="lastName" class="form-control" value="<%=DataUtility.getStringData(bean.getLastName())%>">
					<font  color="red"><%=ServletUtility.getErrorMessage("lastName", request)%></font>
				</div>
				
			</div>
			
			<div class="row">
				<div class="col-md-6 mt-md-0 mt-3">
					<label>User Name<font color="red">*</font></label> <input type="text" placeholder="Enter User Name Name here..." name="userName" class="form-control" value="<%=DataUtility.getStringData(bean.getUserName())%>">
					<font  color="red"><%=ServletUtility.getErrorMessage("userName", request)%></font>
				</div>
				<div class="col-md-6 mt-md-0 mt-3">
					<label>Password<font color="red">*</font></label> <input type="password" placeholder="Enter Password Name here..." name="password" class="form-control" value="<%=DataUtility.getStringData(bean.getPassword())%>">
					<font
                        color="red"> <%=ServletUtility.getErrorMessage("password", request)%></font>
				</div>
			</div>
			
			<div class="row">
				<div class="col-md-12 mt-md-0 mt-3">
					<label>Contact Number<font color="red">*</font></label> <input type="text" placeholder="Enter Contact No here..." name="contactNo" class="form-control" value="<%=DataUtility.getStringData(bean.getContactNo())%>">
					<font  color="red"><%=ServletUtility.getErrorMessage("contactNo", request)%></font>
				</div>
			</div>
			
			<input type="submit" name="operation" value="<%=UserRegistrationCtl.OP_SIGN_UP%>" class="btn btn-primary mt-3"  />
		</div>
		</form>
	</div>
	<%@ include file="footer.jsp"%>
</body>
</html>