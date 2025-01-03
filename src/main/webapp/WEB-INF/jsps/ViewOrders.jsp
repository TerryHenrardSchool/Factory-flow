<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="fr">
<head>
<meta charset="ISO-8859-1">
<title>View Orders</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
	rel="stylesheet">
</head>
<body>
    <h1>Order List</h1>
    <table>
        <thead>
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
</body>
</html>
