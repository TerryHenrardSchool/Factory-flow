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
			Object maintenanceWorkersObj = request.getAttribute("maintenanceWorkers");
			Object errorMessagesObj = request.getAttribute("errorMessages");
			
			Collection<Machine> machines = null;
			Collection<MaintenanceWorker> maintenanceWorkers = null;
			Collection<String> errorMessages = null;
						
			if (machinesObj instanceof Collection) {
				machines = (Collection<Machine>) machinesObj;				
			}
			
			if (maintenanceWorkersObj instanceof Collection) {
				maintenanceWorkers = (Collection<MaintenanceWorker>) maintenanceWorkersObj;				
			}
			
			if (errorMessagesObj instanceof Collection) {
                errorMessages = (Collection<String>) errorMessagesObj;				
			}
			
			System.out.println("errorMessages: " + errorMessages);
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
		      	<input type="hidden" id="machineIdHiddenInputValidateMaintenance" name="machineId" value="-1">
		        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
		        <input type="submit" name="action" class="btn btn-danger" value="Restart maintenance">
		        <input type="submit" name="action" class="btn btn-primary" value="Validate">
		      </div>
	      </form>
	    </div>
	  </div>
	</div>
	
	<div class="modal fade" tabindex="-1" id="sendToMaintenanceModal">
	  <div class="modal-dialog modal-lg">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title">Maintenance workers</h5>
	        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
	      </div>
	      <form action="MaintenanceResponsableDashboardServlet" method="POST">
		      <div class="modal-body">
		        <% if (!objectValidator.hasValue(maintenanceWorkers) || maintenanceWorkers.isEmpty()) { %>
		          <p class="text-center text-muted">No maintenance workers available</p>
		        <% } else { %>
		          <table class="table table-hover">
		            <thead>
		              <tr>
		                <th>#</th>
		                <th>ID</th>
		                <th>Worker</th>
		                <th>Matricule</th>
		                <th>Status</th>
		                <th>Add to maintenance</th>
		              </tr>
		            </thead>
		            <tbody>
		              <% int i = 1; %>
		              <% for (MaintenanceWorker maintenanceWorker : maintenanceWorkers) { 
		                String statusBadgeClass = "badge text-uppercase px-3 py-2 ";
		                String actionButton = "";
		                String statusBadgeText = "";
		
		                if (maintenanceWorker.isFree()) {
		                  statusBadgeClass += "bg-success";
		                  statusBadgeText = "Free";
		                  actionButton = 
	                		  "<input type='checkbox' name='workerIds' value='" + maintenanceWorker.getId().orElse(-1) + "' class='btn-check' id='btncheck" + i + "' autocomplete='off'>" +
	                          "<label class='btn btn-outline-primary' for='btncheck" + i + "'>Select</label>";

		                } else {
		                  statusBadgeClass += "bg-danger";
		                  statusBadgeText = "Unavailable";
		                }
		              %>
		              <tr>
		                <td class="fw-bold fst-italic"><%= i++ %></td>
		                <td><%= maintenanceWorker.getId().orElse(-1) %></td>
		                <td><%= maintenanceWorker.getFullNameFormatted() %></td>
		                <td><%= maintenanceWorker.getMatricule() %></td>
		                <td><span class="<%= statusBadgeClass %>"><%= statusBadgeText %></span></td>
		                <td><%= actionButton %></td>
		              </tr>
		              <% } %>
		            </tbody>
		          </table>
		        <% } %>
		      </div>
		      <div class="modal-footer">
		        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
   		      	<input type="hidden" id="machineIdHiddenInputSendToMaintenance" name="machineId" value="-1">		        
		        <input type="submit" name="action" class="btn btn-primary" value="Send to maintenance">
		      </div>
	      </form>
	    </div>
	  </div>
	</div>

	
	<div class="text-center py-4 bg-light shadow-sm">
		<h1 class="display-4 fw-bold text-primary">Dashboard</h1>
		<p class="text-muted fst-italic">Your hub for insights, metrics, and controls</p>
	</div>
	
	<div class="container mt-4">
	<% if (objectValidator.hasValue(errorMessages)) { %>
		<% for (String errorMessage: errorMessages) { %>
			<div class="alert alert-danger" role="alert">
			  <%= errorMessage %>
			</div>
		<% } %>
	<% } %>
		
	<% if (objectValidator.hasValue(machines)) { %>
		<table class="table table-hover">
			<thead>
				<tr>
					<th>#</th>
					<th>ID</th>
					<th>Name</th>
					<th>Type</th>
					<th>Next in</th>
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
							actionButton = "<button id=" + machine.getId().orElse(-1) + " class='btn btn-warning btn-sm w-75 send-to-maintenance-btn' data-bs-toggle='modal' data-bs-target='#sendToMaintenanceModal'>Send to maintenance</button>";
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
					    <td class="fw-bold fst-italic"><%= i++ %></td>
						<td><%= machine.getId().get() %></td>
						<td><%= machine.getName() %></td>
						<td><%= machine.getMachineType().getType() %></td>
						<% if (machine.getStatus().equals(MachineStatus.OK)) { %>
						<td><%= machine.calculateDaysBeforeNextMaintenance() %> days</td>
						<% } else { %>
						<td>-</td>
						<% } %>	
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
