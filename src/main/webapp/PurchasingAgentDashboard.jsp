<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Machine Dashboard</title>
    <link rel="stylesheet" href="styles.css"> <!-- Assurez-vous d'avoir un fichier CSS pour le style -->
</head>
<body>
    <h1>Machine Dashboard</h1>
    <table border="1">
        <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Type</th>
                <th>Status</th>
                <th>Zone Colors</th>
                <th>Site City</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="machine" items="${machineViewModels}">
                <tr>
                    <td>${machine.id}</td>
                    <td>${machine.name}</td>
                    <td>${machine.machineType}</td>
                    <td>${machine.status}</td>
                    <td>${machine.zoneColors}</td>
                    <td>${machine.siteCity}</td>
                    <td>
                        <c:if test="${machine.buy}">
                            <form action="buyMachine" method="post">
                                <input type="hidden" name="machineId" value="${machine.id}" />
                                <button type="submit">Buy</button>
                            </form>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>
