<%@ page isELIgnored="false" %>
<%@ page import="java.util.List, java.text.*, my.project.entity.Product" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<form action="senior" method="get">
    <input type="submit" name="goods" value="Goods">
    <input type="submit" name="cashiers" value="Cashiers">
</form>


<c:if test="${sessionScope.allProd!=null}">

<table>
    <tr>
        <th>Goods</th>
        <th>Sold</th>
        <th>Total cash</th>
    </tr>
    <c:forEach var="prod" items="${sessionScope.allProd}">
        <tr>
            <td>${prod.name}</td>
            <td>${prod.num}</td>
            <td>${prod.cash}</td>
        </tr>

    </c:forEach>

</table>

</c:if>

<c:if test="${sessionScope.cashiers!=null}">
<table>
    <tr>
        <th>Cashier ID</th>
        <th>All checks</th>
        <th>Canceled Checks</th>
        <th>Sales</th>
    </tr>
    <c:forEach var="cashier" items="${sessionScope.cashiers}">
        <tr>
            <td>${cashier.id}</td>
            <td>${cashier.checks}</td>
            <td>${cashier.isClosed}</td>
            <td>${cashier.sum}</td>
        </tr>
    </c:forEach>
</table>
</c:if>
</body>
</html>