<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="be.alb_mar_hen.models.*" %>
<%@page import="java.util.Collection" %>
<%@page import="java.util.List" %>
<%@page import="be.alb_mar_hen.enumerations.MaintenanceStatus" %>
<%@page import="be.alb_mar_hen.formatters.LocalDateTimeFormatter"%>
<!DOCTYPE html>
<html lang="fr">
<head>
	<meta charset="ISO-8859-1">
	<title>Maintenance History</title>
	<link
		href="<%= request.getContextPath() %>/webjars/bootstrap/5.3.3/css/bootstrap.min.css"
		rel="stylesheet"
	>
	<script src="<%= request.getContextPath() %>/webjars/bootstrap/5.3.3/js/bootstrap.bundle.min.js" defer></script>
	<script src="<%= request.getContextPath() %>/scripts/MaintenanceHistoryScript.js" defer></script>
</head>
<body class="bg-light">
	<nav class="navbar navbar-light bg-light">
	    <div class="container-fluid">
	        <div class="d-flex justify-content-end w-100">
	            <a href="LogoutServlet" class="btn btn-danger">Logout</a>
	        </div>
	    </div>
	</nav>

	<div class="container mt-5">
		<div class="mb-3">
           	<a href="PurchasingAgentDashboardServlet" class="btn btn-primary">Go back</a>
 		</div>
		<h1 class="display-4 text-center mb-4">Maintenance History</h1>
		<table class="table table-hover table-striped">
			<thead class="table-dark">
				<tr>
					<th scope="col">Id</th>
					<th scope="col">Start Date</th>
					<th scope="col">End Date</th>
					<th scope="col">Report</th>
					<th scope="col">Workers</th>
					<th scope="col">Responsable</th>
					<th scope="col">Machine</th>
					<th scope="col">Status</th>
				</tr>
			</thead>
			<tbody>
				<%
				Object maintenancesObj = request.getAttribute("maintenances");
				Collection<Maintenance> maintenances = null;
				
				if (maintenancesObj instanceof List) {
	               	maintenances = (Collection<Maintenance>) maintenancesObj;
				}      
	                        
	              	for (Maintenance maintenance : maintenances) {
	     			%>
					<tr>
						<td><%= maintenance.getId().get() %></td>
						<td><%= LocalDateTimeFormatter.format(maintenance.getStartDateTime()) %></td>
						<td><%= LocalDateTimeFormatter.format(maintenance.getEndDateTime().orElse(null)) %></td>
						<td >
							<p style="display:none" id="<%= maintenance.getId().get() %>" class="p-report"><%= maintenance.getReport().orElse("N/A") %></p>
							<button type="button" id="<%= maintenance.getId().get() %>" class="btn btn-info button-report" data-bs-toggle="modal" data-bs-target="#sharedModal">View Report</button>
						</td>
						
						<td>
							<ul class="list-unstyled">
							<%
								for (MaintenanceWorker worker : maintenance.getMaintenanceWorkers()) {
									%>
									<li><i class="bi bi-person-fill"></i> <%= worker.getFullNameFormatted() %></li>
									<%
								}
							%>
							</ul>
						</td>
						
						<td><%= maintenance.getMaintenanceResponsable().getFullNameFormatted() %></td>
						<td><%= maintenance.getMachine().getName() %></td>
						<td>
							<span class="badge bg-<%= maintenance.getStatus().equals(MaintenanceStatus.IN_PROGRESS) ? "warning" : "success" %>">
								<%= maintenance.getStatus() %>
							</span>
						</td>
					</tr>
				<% } %>
			</tbody>
		</table>
	</div>
	
	<!-- Modal -->
	<div class="modal fade" id="sharedModal" tabindex="-1" aria-labelledby="sharedModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="sharedModalLabel">Finalize Maintenance</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
				</div>
					<div class="modal-body">
						<div class="mb-3">
							<p id="displayReport"></p>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">OK</button>
					</div>
			</div>
		</div>
	</div>
</body>
</html>