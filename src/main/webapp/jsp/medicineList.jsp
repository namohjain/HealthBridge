<%@page import="in.co.ehealth.care.bean.MedicineBean"%>
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
<title>Medicine List</title>
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
					<i class="fa fa-arrow-right" aria-hidden="true"></i>Medicine List
				</li>
			</ol>
		</nav>
	</div>
	<hr>
	<form method="post" action="<%=EHCView.MEDICINE_LIST_CTL%>">
		<div class="card">
			<h5 class="card-header"
				style="background-color: #00061df7; color: white;">Medicine List</h5>
			<div class="card-body">
				<div class="row g-3">

					

					<div class="col">
						<input type="text" placeholder="Search By Name here..."
							name="name" class="form-control"
							value="<%=ServletUtility.getParameter("name", request)%>">
					</div>
					
					<div class="col">
						<input type="text" placeholder="Search By Company Name here..."
							name="cName" class="form-control"
							value="<%=ServletUtility.getParameter("cName", request)%>">
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
							<th scope="col">Name</th>
							<th scope="col">Company Name</th>
							<th scope="col">Price</th>
							<th scope="col">Status</th>	
							<th scope="col">Description</th>
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
						MedicineBean bean = null;
						List list = ServletUtility.getList(request);
						Iterator<MedicineBean> iterator = list.iterator();
						while (iterator.hasNext()) {
							bean = iterator.next();
						%>
						<tr>
						<% if(userbean.getRoleId()==1){ %>
							<td><input type="checkbox" class="case" name="ids"
								value="<%=bean.getId()%>"></td>
								<%} %>
							<td scope="row"><%=index++%></td>
							<td scope="row"><%=bean.getName()%></td>
							<td scope="row"><%=bean.getCompanyName()%></td>
							<td scope="row"><%=bean.getPrice()%></td>
							<td scope="row"><%=bean.getStatus()%></td>
							<td scope="row"><%=bean.getDescription()%></td>
							
							<% if(userbean.getRoleId()==1){ %>
							<td><a href="medicine?id=<%=bean.getId()%>"><i
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