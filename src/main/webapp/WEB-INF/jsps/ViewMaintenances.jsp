<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="be.alb_mar_hen.models.*" %>
<%@page import="java.util.Collection" %>
<%@page import="java.util.List" %>
<%@page import="be.alb_mar_hen.enumerations.MaintenanceStatus" %>
<!DOCTYPE html>
<html lang="fr">
<head>
	<meta charset="ISO-8859-1">
	<title>View maintenances</title>
	<link
		href="<%= request.getContextPath() %>/webjars/bootstrap/5.3.3/css/bootstrap.min.css"
		rel="stylesheet"
	>
	<script src="<%= request.getContextPath() %>/webjars/bootstrap/5.3.3/js/bootstrap.bundle.min.js" defer></script>
	<script src="<%= request.getContextPath() %>/scripts/WorkerDashboardScript.js" defer></script>
</head>
<body>
	<h1 class="display-1 text-center">Maintenances</h1>
	<table class="table table-bordered">
		<thead>
			<tr>
				<th scope="col">Id</th>
				<th scope="col">Start Date</th>
				<th scope="col">End Date</th>
				<th scope="col">Duration (min)</th>
				<th scope="col">Report</th>
				<th scope="col">Workers</th>
				<th scope="col">Responsable</th>
				<th scope="col">Machine</th>
				<th scope="col">Status</th>
				<th scope="col">Action</th>
			</tr>
		</thead>
		<tbody>
				<%
				Object maintenancesObj = request.getAttribute("maintenances");
				Collection<Maintenance> maintenances = null;
				
				if(maintenancesObj instanceof List) {
	               	maintenances = (Collection<Maintenance>) maintenancesObj;
				}      
	                        
	              	for(Maintenance maintenance : maintenances){
	     			%>
	     			
					<tr>
						<td><%= maintenance.getId().get()%></td>
						<td><%= maintenance.getStartDateTime() %></td>
						<td><%= maintenance.getEndDateTime().orElse(null) %></td>
						<td><%= maintenance.getDuration().orElse(null) %></td>
						<td><%= maintenance.getReport().orElse(null) %></td>
						
						<td>
							<ul>
							<%
								for(MaintenanceWorker worker : maintenance.getMaintenanceWorkers()){
									
									%>
									<li><%= worker.getFullNameFormatted() %></li>
									<%
								}
							%>
							</ul>
						</td>
						
						<td><%= maintenance.getMaintenanceResponsable().getFullNameFormatted() %></td>
						<td><%= maintenance.getMachine().getName() %></td>
						<td><%= maintenance.getStatus() %></td>
						<td>
							<% if (maintenance.getStatus().equals(MaintenanceStatus.IN_PROGRESS)) { %>
						        <!-- Bouton pour ouvrir le modal -->
						        <button
						        	id="<%= maintenance.getId().get() %>"
						            type="button"
						            class="btn btn-primary finalize-button"
						            data-bs-toggle="modal"
						            data-bs-target="#sharedModal">
						            Finalize
						        </button>
					        <% } %>
				        </td>
					</tr>
				<%              		
							}
				%>
		</tbody>
	</table>
	
	<div class="modal fade" id="sharedModal" tabindex="-1" aria-labelledby="sharedModalLabel" aria-hidden="true">
	    <div class="modal-dialog">
	        <div class="modal-content">
	            <div class="modal-header">
	                <h5 class="modal-title" id="sharedModalLabel">Finalize Maintenance</h5>
	                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
	            </div>
	            <form action="ViewMaintenancesServlet" method="POST">
		            <div class="modal-body">		      
	                    <label for="report">Report:</label>
	                	<input type="textarea" class="form-control" id="report" name="report">
		            </div>
		            <div class="modal-footer">
	                    <input type="hidden" name="maintenanceId" id="modalMaintenanceId">
	                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Annuler</button>
	                    <button type="submit" class="btn btn-primary">Confirmer</button>
		            </div>
	            </form>
	        </div>
	    </div>
	</div>
</body>
</html>