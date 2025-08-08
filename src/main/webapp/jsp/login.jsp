
<%@page import="in.co.ehealth.care.controller.LoginCtl"%>
<%@page import="in.co.ehealth.care.util.DataUtility"%>
<%@page import="in.co.ehealth.care.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login</title>
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
					class="fas fa-tachometer-alt"></i> <a class="link-dark" href="<%=EHCView.WELCOME_CTL%>">Home</a></li>
				<li class="breadcrumb-item linkSize active" aria-current="page">
					<i class="fa fa-arrow-right" aria-hidden="true"></i> Login
				</li>
			</ol>
		</nav>
	</div>
	<hr>

	<div class="container">
		<div class="row">
			<div class="col-3">
			
			</div>
			<div class="col-6">
				<div class="card shadow  mb-5 bg-body rounded">
					<h5 class="card-header bgcolor" style=" background-color: #2D89FF; color: white; height: 80px; font-size: 30px; padding-top: 17px"  align="center">Login</h5>
					<b><font color="red" size="2px"> <%=ServletUtility.getErrorMessage(request)%>
					</font></b> <b><font color="Green" size="2px"> <%=ServletUtility.getSuccessMessage(request)%>
					</font></b>
					<div class="card-body">
						<form method="post" action="<%=EHCView.LOGIN_CTL%>">

							<jsp:useBean id="bean"
								class="in.co.ehealth.care.bean.UserBean"
								scope="request"></jsp:useBean>

							<%
							String uri = (String) request.getAttribute("uri");
							%>

							<input type="hidden" name="uri" value="<%=uri%>"> <input
								type="hidden" name="id" value="<%=bean.getId()%>"> <input
								type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
							<input type="hidden" name="modifiedBy"
								value="<%=bean.getModifiedBy()%>"> <input type="hidden"
								name="createdDatetime"
								value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
							<input type="hidden" name="modifiedDatetime"
								value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">
							<div class="card-body">

								<div class="mb-3">
									<label style="margin-bottom: 6px;">User Name</label> <input
										type="text" name="userName" class="form-control"
										value="<%=DataUtility.getStringData(bean.getUserName())%>">
									<font color="red"><%=ServletUtility.getErrorMessage("userName", request)%></font>
								</div>
								<div class="mb-3">
									<label style="margin-bottom: 6px;">Password</label> <input
										type="password" name="password" class="form-control"
										value="<%=DataUtility.getStringData(bean.getPassword())%>">
									<font color="red"> <%=ServletUtility.getErrorMessage("password", request)%></font>
								</div>

								<input type="submit" name="operation"
									value="<%=LoginCtl.OP_SIGN_IN%>"
									class="btn btn-primary btn btn-info" />&nbsp;&nbsp;&nbsp;

							</div>
						</form>
					</div>
				</div>
			</div>
			<div class="col-3"></div>
		</div>

	</div>
	<%@ include file="footer.jsp"%>
</body>
</html>