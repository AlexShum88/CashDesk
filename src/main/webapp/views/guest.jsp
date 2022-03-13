<%@ page isELIgnored="false" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>


<f:setLocale value="${sessionScope.locale}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Guest</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>
<f:bundle basename="locale">
<body>
<h3><f:message key="Hello guest"/></h3>
<br>
<hr>
<br>
<h4><f:message key="soon"/></h4>
<br>
<hr>
<br>
<div>
    <button class="w3-btn w3-blue w3-round-large" onclick="location.href='index.jsp'"><f:message key="To login"/></button>
</div>
</f:bundle>
</body>
</html>