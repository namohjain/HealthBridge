<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="in.co.ehealth.care.bean.DoctorBean"%>
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
<title>Doctor List</title>
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
					<i class="fa fa-arrow-right" aria-hidden="true"></i> Doctor List
				</li>
			</ol>
		</nav>
	</div>
	<hr>
	<form method="post" action="<%=EHCView.DOCTOR_LIST_CTL%>">
		<div class="card">
			<h5 class="card-header"
				style="background-color: #00061df7; color: white;">Doctor List</h5>
			<div class="card-body">
				<div class="row g-3">

					<div class="col">
						<input type="text" placeholder="Search By First Name here..."
							name="firstName" class="form-control"
							value="<%=ServletUtility.getParameter("firstName", request)%>">
					</div>

					<div class="col">
						<input type="text" placeholder="Search By Last Name here..."
							name="lastName" class="form-control"
							value="<%=ServletUtility.getParameter("lastName", request)%>">
					</div>

					<div class="col">
						<input type="submit" class="btn  btn-outline-primary"
							name="operation" value="Search"></input> or <input type="submit"
							class="btn  btn-outline-secondary" name="operation"
							value="Reset">
					</div>
				</div>
				<b><font color="red"> <%=ServletUtility.getErrorMessage(request)%>
				</font></b> <b><font color="Green"> <%=ServletUtility.getSuccessMessage(request)%>
				</font></b><br>

				<table class="table table-bordered border-primary">
					<thead>
						<tr>
							<% if(userbean.getRoleId()==1){ %>
							<th scope="col"><input type="checkbox" id="selectall">Select
								All</th>
								<%} %>
							<th scope="col">#</th>
							<th scope="col">First Name</th>
							<th scope="col">Last Name</th>
							<th scope="col">Email Id</th>
							<th scope="col">Contact No</th>
							<th scope="col">DOB</th>
							<th scope="col">Qualification</th>
							<th scope="col">Specialization</th>
							<th scope="col">Experience</th>
							<% if(userbean.getRoleId()==1){ %>
							<th scope="col">Action</th>
							<%} %>
						</tr>
					</thead>
					<tbody>
						<%
						int pageNo = ServletUtility.getPageNo(request);
						int pageSize = ServletUtility.getPageSize(request);
						int index = ((pageNo - 1) * pageSize) + 1;
						int size = ServletUtility.getSize(request);
						DoctorBean bean = null;
						List list = ServletUtility.getList(request);
						Iterator<DoctorBean> iterator = list.iterator();
						while (iterator.hasNext()) {
							bean = iterator.next();
						%>
						<tr>
						<% if(userbean.getRoleId()==1){ %>
							<td><input type="checkbox" class="case" name="ids"
								value="<%=bean.getId()%>"></td>
								<%} %>
							<td scope="row"><%=index++%></td>
							<td scope="row"><%=bean.getFirstName()%></td>
							<td scope="row"><%=bean.getLastName()%></td>
							<td scope="row"><%=bean.getEmail()%></td>
							<td scope="row"><%=bean.getContactNo()%></td>
							<td scope="row"><%=DataUtility.getDateString(bean.getDob())%></td>
							<td scope="row"><%=bean.getQualification()%></td>
							<td scope="row"><%=bean.getSpecialization()%></td>
							<td scope="row"><%=bean.getExperience()%></td>
							<% if(userbean.getRoleId()==1){ %>
							<td><a href="doctor?id=<%=bean.getId()%>"><i
										class="fas fa-edit"></i></a></td>
										<%} %>
						</tr>
						<%
						}
						%>
					</tbody>
				</table>

				<div class="clearfix">
				<% if(userbean.getRoleId()==1){ %>
						<input type="submit" name="operation"
							class="btn btn-sm btn-danger float-start"
							<%=(list.size() == 0) ? "disabled" : ""%>
							value="Delete">
				<%} %>
					<nav aria-label="Page navigation example float-end">
						<ul class="pagination justify-content-end" style="font-size: 13px">
							<li class="page-item"><input type="submit" name="operation"
								class="page-link" <%=(pageNo == 1) ? "disabled" : ""%> value="Previous"></li>
							
							<li class="page-item"><input type="submit" name="operation"
								class="page-link"
								<%=((list.size() < pageSize) || size == pageNo * pageSize) ? "disabled" : ""%>
								value="Next"></li>
						</ul>
					</nav>
				</div>
			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
			type="hidden" name="pageSize" value="<%=pageSize%>">

			</div>

		</div>
	</form>

	<%@ include file="footer.jsp"%>
</body>
</html>