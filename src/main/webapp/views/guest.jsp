<%@ page isELIgnored="false" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>


<f:setLocale value="${sessionScope.locale}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
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
    <button onclick="location.href='index.jsp'"><f:message key="To login"/></button>
</div>
</f:bundle>
</body>
</html>