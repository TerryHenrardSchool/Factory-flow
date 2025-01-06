<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="ISO-8859-1">
    <title>View Orders</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
    <div class="container">
        <div class="d-flex justify-content-between align-items-center my-4">
            <h1>Order List</h1>
            <div class="d-flex gap-2">
            	<a href="PurchasingAgentDashboardServlet" class="btn btn-primary">Go back</a>
            	<a href="LogoutServlet" class="btn btn-danger">Logout</a>
            </div>
	    </div>
	</div>
    <table class="table table-bordered table-hover">
        <thead class="table-light">
            <tr>
                <th>Purchasing Agent</th>
                <th>Order Date</th>
                <th>Price</th>
                <th>Supplier</th>
                <th>Machine Name</th>
                <th>Machine Type</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="order" items="${orders}">
                <tr>
                    <td>${order.purchasingAgentName}</td>
                    <td>${order.orderDate}</td>
                    <td>${order.price}</td>
                    <td>${order.supplierName}</td>
                    <td>${order.machineName}</td>
                    <td>${order.machineType}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
