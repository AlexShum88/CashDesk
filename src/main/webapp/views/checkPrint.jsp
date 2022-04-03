<%@ page isELIgnored="false" %>
<%@ page import="java.util.List, java.text.*" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>


<f:setLocale value="${sessionScope.locale}"/>

<html>
<head>
    <title>Check</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>
<body>
<f:bundle basename="locale">
    <div class="w3-container">
    <div class="w3-container w3-border">
        <label><b>Check num:</b>  ${requestScope.checkId}</label>

    </div>
    <div class="w3-container w3-border">
        <label><b>Cashier id:</b> ${requestScope.cashier}</label>

    </div>
    <div class="w3-container w3-border">
        <label><b>Date:</b> ${requestScope.date}</label>
    </div>
    <div class="w3-container w3-border">

        <label><b>Products:</b></label>
        <br>
        <c:forEach var="product" items="${requestScope.products}">
            <div class="w3-panel">
            <label>${product.key.name}</label>
            <br>
            <label class="w3-margin-left">${product.key.price}x${product.value.get(0)} = ${product.value.get(1)} </label>
    </div>
        </c:forEach>

    </div>
    <div class="w3-container w3-border">
        <label><b>Total = ${requestScope.total}</b></label>

    </div>
    <div class="w3-container w3-border">

        <label><f:message key="Thanks"/></label>
        <br>
        <label><f:message key="wish"/></label>

    </div>
    <div class="w3-container w3-border ">
        <form action="check" method="get">
            <input class="w3-btn w3-red w3-round-large w3-margin-top" type="submit" name="to cashier" value="To cashier">
        </form>
    </div>
    <div>
        <form action="check" method="get">
            <input type="hidden" name="messageForPrint" value="<f:message key="Thanks"/>///<f:message key="wish"/>">
            <input class="w3-btn w3-red w3-round-large w3-margin-top" type="submit" name="print" value="<f:message key="print"/>">
        </form>
    </div>
    </div>
</f:bundle>
</body>
</html>
