<%@ page isELIgnored="false" %>
<%@ page import="java.util.List, java.text.*, my.project.entity.Product" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt"%>


<f:setLocale value="${sessionScope.locale}"/>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Cashier</title>
</head>
<body>
<f:setLocale value="${sessionScope.locale}"/>
<f:bundle basename="locale">
<div>
    <form action="check" method="get">
        <input type="submit" name="createCheck" value="<f:message key="createCheck" />">
    </form>
</div>
<div>
    <button onclick="location.href='index.jsp'"><f:message key="To login" /></button>
</div>
<br>

<br>

</f:bundle>
</body>
</html>