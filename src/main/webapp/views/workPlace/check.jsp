<%@ page isELIgnored="false" %>
<%@ page import="java.util.List, java.text.*, my.project.entity.Product" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Check</title>
</head>
<body>
<table>
    <tr>
        <th>Name</th>
        <th>Number</th>
        <th>Price</th>
    </tr>


    <c:forEach var="productEntity" items="${requestScope.products}">
    <tr>
        <form action="check" method="get">
            <input type="hidden" name="productId" value="${productEntity.key.id}">
            <td>
                <label>${productEntity.key.name}</label>
            </td>
            <td>
<%--                <label for="number">${productEntity.key.number}</label><br>--%>
                <input type="text" maxlength="12" size="12" id="number" name="number" value="0"
                       pattern="[-]?[0-9]{1,10}[.]?[0-9]{0,2}" ><br>
                <input type="submit" name="setNumber" value="Accept">
            </td>
            <td>
                <label>Price = ${productEntity.key.price}</label><br>
                <label>Current price = ${productEntity.value}</label>
<%--                <input type="hidden" value="${productEntity.value}">--%>
            </td>
        </form>
    </tr>
    </c:forEach>
</table>

<%--product choose--%>
<form action="check" method="get">
    <label for="product">choose product</label><br>
    <input list="product" name="product">
    <datalist id="product">
        <c:forEach var="product" items="${requestScope.allProducts}">
            <option value="${product.name} ${product.id}">
        </c:forEach>
    </datalist>
    <input type="submit" name="selectedProduct" value="get it">
</form>
<%--total sum--%>
<div>
    <label>Total sum = ${totalSum}</label>
</div>

<%--form to edit check--%>
<form action="check_edit.jsp">
<input type="submit" value="edit check by senior"/>
</form>
<%--close check--%>
<form action="check" method="get">
    <input type="submit" name="closeCheck" value="Close check">
</form>
<div>
    <button onclick="location.href='views/workPlace/cashier.jsp'">To cashier</button>
</div>
</body>
</html>