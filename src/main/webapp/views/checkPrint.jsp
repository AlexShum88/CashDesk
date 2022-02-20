<%@ page isELIgnored="false" %>
<%@ page import="java.util.List, java.text.*, my.project.entity.Product" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Check</title>
</head>
<body>

<div>
    <label><b>check num --- ${requestScope.checkId}</b></label>
    <hr>
</div>
<div>
    <label><b>cashier id --- ${requestScope.cashier}</b></label>
    <hr>
</div>
<div>
    <label>date --- ${requestScope.date}</label>
    <hr>
</div>
<div>
    <hr>
    <label><b>products</b></label>
    <c:forEach var="product" items="${requestScope.products}">
    <hr>
    <label>${product.key.name}</label>
        <br>
    <label>${product.key.price}x${product.value.get(0)}.......${product.value.get(1)} </label>
    </c:forEach>
    <hr>
</div>
<div>
    <label><b>total_______${requestScope.total}</b></label>
    <hr>
</div>
<div>
    <hr>
    <label>Thanks for buying!</label>
    <br>
    <label>Have a nice day!</label>
    <hr>
</div>
</body>
</html>