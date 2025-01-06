<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Purchasing Agent Dashboard</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
	<nav class="navbar navbar-light bg-light">
	    <div class="container-fluid">
	        <div class="d-flex justify-content-end w-100">
	            <a href="LogoutServlet" class="btn btn-danger">Logout</a>
	        </div>
	    </div>
	</nav>

    <div class="container mt-5">
        <c:if test="${not empty successMessage}">
            <div class="alert alert-success" role="alert">
                ${successMessage}
            </div>
        </c:if>
	
        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger" role="alert">
                ${errorMessage}
            </div>
        </c:if>
       
       	<div class="mb-3">
        	<a href="ViewOrdersServlet" class="btn btn-primary">View Orders</a>
 		</div>
 		
 		<div class="mb-3">
           	<a href="MaintenanceHistoryServlet" class="btn btn-primary">Maintenances History</a>
 		</div>
       
        <h1>Machines Dashboard</h1>
        <table class="table table-striped">
            <thead>
                <tr>
                    <th>Machine Name</th>
                    <th>Type</th>
                    <th>Status</th>
                    <th>Zone Color</th>
                    <th>City</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="machine" items="${machineViewModels}">
                    <tr>
                        <td>${machine.machineName}</td>
                        <td>${machine.machineTypeName}</td>
                        <td>${machine.machineStatus}</td>
                        <td>${machine.zoneColor}</td>
                        <td>${machine.siteCity}</td>
                        <td>
                            <!-- Button to open modal with machine details -->
                            <button type="button" class="btn btn-info" data-bs-toggle="modal" data-bs-target="#machineModal-${machine.machineId}">
                                View more
                            </button>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

    <!-- Modal to display machine details -->
    <c:forEach var="machine" items="${machineViewModels}">
        <div class="modal fade" id="machineModal-${machine.machineId}" tabindex="-1" aria-labelledby="machineModalLabel-${machine.machineId}" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="machineModalLabel-${machine.machineId}">${machine.machineName}</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <!-- Displaying Machine Info -->
                        <h6>Machine type: ${machine.machineTypeName}</h6>
                        <p><strong>Price:</strong> ${machine.price} dollars</p>
                        <p><strong>Number of maintenances for this machine:</strong> ${machine.numberOfMaintenances}</p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                        <!-- Conditionally show the Buy button in modal if buy = true -->
                        <c:if test="${machine.buy}">
                            <form action="PurchasingAgentDashboardServlet" method="POST" class="d-inline">
                                <input type="hidden" name="machineJson" value="${fn:escapeXml(machine.machineJson)}">
                                <button type="submit" class="btn btn-primary">
                                    Buy
                                </button>
                            </form>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </c:forEach>
	
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
