<%@ 
	page language="java" 
	contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"
%>
<%@page import="java.util.Collection" %>
<%@page import="be.alb_mar_hen.models.*" %>
<%@page import="be.alb_mar_hen.enumerations.MachineStatus" %>
<%@page import="be.alb_mar_hen.validators.*" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Maintenance responsable dashboard</title>
	<link
		href="<%= request.getContextPath() %>/webjars/bootstrap/5.3.3/css/bootstrap.min.css"
		rel="stylesheet"
	>
	<script src="<%= request.getContextPath() %>/webjars/bootstrap/5.3.3/js/bootstrap.bundle.min.js" defer></script>
	<script src="<%= request.getContextPath() %>/scripts/maintenanceResponsableDashboard.js" defer></script>
</head>
<body>
	<%
		ObjectValidator objectValidator = new ObjectValidator();
	
		Object machinesObj = request.getAttribute("machines");
		String errorMessage = request.getParameter("errorMessage");
		
		Collection<Machine> machines = null;
		if (machinesObj instanceof Collection) {
			machines = (Collection<Machine>) machinesObj;
		}
	%>
	
	<nav class="navbar navbar-light bg-light">
	    <div class="container-fluid">
	        <div class="d-flex justify-content-end w-100">
	            <a href="LogoutServlet" class="btn btn-danger">Logout</a>
	        </div>
	    </div>
	</nav>
	
	
	<div class="modal fade" id="validateMaintenanceModal" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h1 class="modal-title fs-5" id="reportTitle">Modal title</h1>
	        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
	      </div>
	      <div class="modal-body">
	        <p id="reportText"></p>
	      </div>
	      <form action="MaintenanceResponsableDashboardServlet" method="POST">
		      <div class="modal-footer">
		      	<input type="hidden" id="machineIdHiddenInput" name="machineId" value="-1">
		        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
		        <input type="submit" name="action" class="btn btn-danger" value="Restart maintenance">
		        <input type="submit" name="action" class="btn btn-primary" value="Validate">
		      </div>
	      </form>
	    </div>
	  </div>
	</div>
	
	<div class="modal fade" tabindex="-1" id="sendToMaintenanceModal">
	  <div class="modal-dialog modal-fullscreen">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title">Modal title</h5>
	        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
	      </div>
	      <div class="modal-body">
	        <p>Modal body text goes here.</p>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
	        <button type="button" class="btn btn-primary">Save changes</button>
	      </div>
	    </div>
	  </div>
	</div>
	
	<button 
	  class="btn btn-primary btn-sm" 
	  data-bs-toggle="modal" 
	  data-bs-target="#sendToMaintenanceModal"
	> 
	  Test 
	</button>
	
	<div class="text-center py-4 bg-light shadow-sm">
		<h1 class="display-4 fw-bold text-primary">Dashboard</h1>
		<p class="text-muted fst-italic">Your hub for insights, metrics, and controls</p>
	</div>
	
	<div class="container mt-4">
	<% if (objectValidator.hasValue(errorMessage)) { %>
		<div class="alert alert-danger" role="alert">
		  <%= errorMessage %>
		</div>
	<% } %>
		
	<% if (!objectValidator.hasValue(machines) || !machines.isEmpty()) { %>
		<table class="table table-hover">
			<thead>
				<tr>
					<th>#</th>
					<th>ID</th>
					<th>Name</th>
					<th>Type</th>
					<th>Machine Status</th>
					<th>Zone Colors</th>
					<th>Actions</th>
				</tr>
			</thead>
			<tbody>
				<%
				int i = 1;
				for (Machine machine : machines) { 
					String statusBadgeClass = "badge text-uppercase px-3 py-2 ";
					String trClass = "";
					String actionButton = "";
					String reportText = "";
					String reportAuthors = "";
					
					switch (machine.getStatus()) {
						case TO_BE_REPLACED:
							statusBadgeClass += "bg-danger";
							trClass = "table-danger";
							break;
							
						case OK:
							statusBadgeClass += "bg-success";
							actionButton = "<button class='btn btn-warning btn-sm w-75'>Send to maintenance</button>";
							break;
							
						case IN_MAINTENANCE:
							statusBadgeClass += "bg-info";
							break;
							
						case NEED_MAINTENANCE:
							statusBadgeClass += "bg-warning";
							trClass = "table-warning";
							break;
							
						case REPLACED:
                               statusBadgeClass += "bg-secondary";
                               break;
                               
						case NEED_VALIDATION:
							statusBadgeClass += "bg-primary";
							actionButton = "<button id=" + machine.getId().orElse(-1) + " class='btn btn-primary btn-sm w-75 validate-maintenance-btn' data-bs-toggle='modal' data-bs-target='#validateMaintenanceModal'>Validate maintenance</button>";
							trClass = "table-primary";
							
							Maintenance lastMaintenance = machine.getLastMaintenance();
							reportText = lastMaintenance.getReport().orElse("No report available");
							reportAuthors = lastMaintenance.getWorkersNames();;
							break;
							
						default:
							statusBadgeClass += "bg-secondary";
					}
					%>
					<tr class="<%= trClass%>">
					    <td><%= i++ %></td>
						<td><%= machine.getId().get() %></td>
						<td><%= machine.getName() %></td>
						<td><%= machine.getMachineType().getType() %></td>
						<td><span class="<%= statusBadgeClass %>"><%= machine.getStatus() %></span></td>
						<td>
							<div class="d-flex flex-wrap gap-2">
								<%
								for (Zone zone : machine.getZones()) { 
									String zoneColorClass = "badge text-uppercase px-3 py-2 ";
									switch (zone.getColor()) {
										case ORANGE:
											zoneColorClass += "bg-warning";
											break;
										case RED:
											zoneColorClass += "bg-danger";
											break;
										case BLACK:
											zoneColorClass += "bg-dark";
											break;
										case GREEN:
											zoneColorClass += "bg-success";
											break;
										default:
											zoneColorClass += "bg-secondary";
									}
								%>
								<span class="<%= zoneColorClass %>"><%= zone.getName() %></span>
								<% } %>
							</div>
						</td>
						<td>
							<%= actionButton %>
							<p id="reportTextMachine<%= machine.getId().orElse(-1) %>" style="display: none;"><%= reportText %></p>
							<p id="reportAuthorMachine<%= machine.getId().orElse(-1) %>" style="display: none;"><%= reportAuthors %></p>
						</td>
					</tr>
				<% } %>
			</tbody>
		</table>
	<% } else { %>
		<p class="text-center text-muted">No machines available</p>
	<% } %>
	</div>
</body>
</html>
