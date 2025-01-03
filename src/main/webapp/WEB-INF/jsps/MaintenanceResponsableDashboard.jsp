<%@ 
	page language="java" 
	contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"
%>
<%@page import="java.util.Collection" %>
<%@page import="be.alb_mar_hen.models.*" %>
<%@page import="be.alb_mar_hen.enumerations.MachineStatus" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
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
		Object machinesObj = request.getAttribute("machines");
		Collection<Machine> machines = null;
		if (machinesObj instanceof Collection) {
			machines = (Collection<Machine>) machinesObj;
		}
	%>
	<div class="modal fade" id="modal" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h1 class="modal-title fs-5" id="staticBackdropLabel">Modal title</h1>
	        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
	      </div>
	      <div class="modal-body">
	        <p id="reportText"></p>
	      </div>
	      <form action="MaintenanceResponsableDashboardServlet" method="POST">
		      <div class="modal-footer">
		      	<input type="hidden" id="machineIdHiddenInput" name="machineId" value="-1">
		        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
		        <input type="submit" class="btn btn-primary" value="Validate">
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
		<% if (machines != null && !machines.isEmpty()) { %>
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
						switch (machine.getStatus()) {
							case TO_BE_REPLACED:
								statusBadgeClass += "bg-danger";
								trClass = "table-danger";
								break;
							case OK:
								statusBadgeClass += "bg-success";
								break;
							case IN_MAINTENANCE:
								statusBadgeClass += "bg-info";
								break;
							case NEED_MAINTENANCE:
								statusBadgeClass += "bg-warning";
								actionButton = "<button class='btn btn-warning btn-sm w-75'>Send to maintenance</button>";
								trClass = "table-warning";
								break;
							case REPLACED:
                                statusBadgeClass += "bg-secondary";
                                break;
							case NEED_VALIDATION:
								statusBadgeClass += "bg-primary";
								actionButton = "<button id=" + machine.getId().orElse(-1) + " class='btn btn-primary btn-sm w-75 validate-maintenance-btn' data-bs-toggle='modal' data-bs-target='#modal'>Validate maintenance</button>";
								trClass = "table-primary";
								reportText = machine.getLastMaintenance().getReport().orElse("No report available");
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
