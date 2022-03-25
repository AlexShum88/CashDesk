<%@ page isELIgnored="false" %>
<%@ page import="java.util.List, java.text.*" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt"%>


<f:setLocale value="${sessionScope.locale}"/>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Cashier</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>
<body>
<f:setLocale value="${sessionScope.locale}"/>
<f:bundle basename="locale">
<div class="w3-container">
    <form action="check" method="get">
        <input class="w3-btn w3-blue w3-round-large w3-margin-top"
               type="submit" name="createCheck" value="<f:message key="createCheck" />">
    </form>
</div>
    <div class="w3-container">
        <form action="check" method="get">
            <input class="w3-btn w3-light-green w3-round-large w3-margin-top "
                   type="submit" name="exitUser" value="<f:message key="To login" />">
        </form>

    </div>

<br>

<br>

</f:bundle>
</body>
</html>