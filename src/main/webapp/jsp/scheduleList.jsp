<%@page import="in.co.ehealth.care.bean.ScheduleDoctorBean"%>
<%@page import="in.co.ehealth.care.bean.StaffBean"%>
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
<title>Schedule List</title>
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
					<i class="fa fa-arrow-right" aria-hidden="true"></i>Schedule Time List
				</li>
			</ol>
		</nav>
	</div>
	<hr>
	<form method="post" action="<%=EHCView.SCHEDULE_DOCTOR_LIST_CTL%>">
		<div class="card">
			<h5 class="card-header"
				style="background-color: #00061df7; color: white;">Schedule Time List</h5>
			<div class="card-body">
				<div class="row g-3">

					

					<div class="col">
						<input type="text" placeholder="Search By DoctorName here..."
							name="name" class="form-control"
							value="<%=ServletUtility.getParameter("bedNo", request)%>">
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
						<% if(userbean.getRoleId()==2){ %>
							<th scope="col"><input type="checkbox" id="selectall">Select
								All</th>
							<%} %>	
							<th scope="col">#</th>
							<th scope="col">Doctor Name</th>
							<th scope="col">Timing</th>
							<th scope="col">Date</th>
							<th scope="col">City</th>
							<th scope="col">Address</th>
							<% if(userbean.getRoleId()==2){ %>
							<th scope="col">Action</th>
							<%} %>
							<% if(userbean.getRoleId()==4){ %>
								<th scope="col">Make Appointment</th>
							<%} %>
						</tr>
					</thead>
					<tbody>
						<%
						int pageNo = ServletUtility.getPageNo(request);
						int pageSize = ServletUtility.getPageSize(request);
						int index = ((pageNo - 1) * pageSize) + 1;
						int size = ServletUtility.getSize(request);
						ScheduleDoctorBean bean = null;
						List list = ServletUtility.getList(request);
						Iterator<ScheduleDoctorBean> iterator = list.iterator();
						while (iterator.hasNext()) {
							bean = iterator.next();
						%>
						<tr>
						<% if(userbean.getRoleId()==2){ %>
							<td><input type="checkbox" class="case" name="ids"
								value="<%=bean.getId()%>"></td>
								<%} %>
							<td scope="row"><%=index++%></td>
							<td scope="row"><%=bean.getDoctorName()%></td>
							<td scope="row"><%=bean.getTime()%></td>
							<td scope="row"><%=bean.getDate()%></td>
							<td scope="row"><%=bean.getCity()%></td>
							<td scope="row"><%=bean.getAddress()%></td>
							<% if(userbean.getRoleId()==2){ %>
							<td><a href="schedule?id=<%=bean.getId()%>"><i
										class="fas fa-edit"></i></a></td>
										
							<td><a class="btn btn-sm btn-info" href="appointmentList?sId=<%=bean.getId()%>">View Appointments</a></td>
							<%} %>
							
							<% if(userbean.getRoleId()==4){ %>
							<td><a class="btn btn-sm btn-info" href="appointment?sId=<%=bean.getId()%>">Make Appointment</a></td>
							<%} %>
							
							
						</tr>
						<%
						}
						%>
					</tbody>
				</table>

				<div class="clearfix">
				<% if(userbean.getRoleId()==2){ %>
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