<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="fr">
<head>
<meta charset="ISO-8859-1">
<title>View maintenances</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
	rel="stylesheet">
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
			<form action="FinalizeMaintenanceServlet" method="POST">
				<c:forEach var="maintenance" items="${maintenances}">
					<tr>
						<td>${maintenance.id.get()}</td>
						<td>${maintenance.startDateTime}</td>

						<c:choose>
							<c:when test="${maintenance.hasEndDateTime()}">
								<td>${maintenance.endDateTime.get()}</td>
							</c:when>
							<c:otherwise>
								<td>Pas de date de fin</td>
							</c:otherwise>
						</c:choose>

						<c:choose>
							<c:when test="${maintenance.hasDuration()}">
								<td>${maintenance.duration.get()}</td>
							</c:when>
							<c:otherwise>
								<td>Pas de durée</td>
							</c:otherwise>
						</c:choose>

						<c:choose>
							<c:when test="${maintenance.hasReport()}">
								<td>${maintenance.report.get()}</td>
							</c:when>
							<c:otherwise>
								<td>Pas de rapport</td>
							</c:otherwise>
						</c:choose>
						<td><c:forEach var="worker"
								items="${maintenance.maintenanceWorkers}">
	                            ${worker.firstName} ${worker.lastName} <br>
							</c:forEach></td>
						<td>${maintenance.maintenanceResponsable.firstName}
							${maintenance.maintenanceResponsable.lastName} <br>
						</td>
						<td>${maintenance.machine.name}</td>
						<td>${maintenance.status}</td>
						<td><c:if test="${maintenance.status eq 'IN_PROGRESS'}">
								<input type="hidden" name="maintenanceId"
									value="${maintenance.id.get()}">
								<input type="submit" value="Finalize" class="btn btn-primary">
							</c:if></td>
					</tr>
				</c:forEach>
			</form>
		</tbody>
	</table>
</body>
</html>