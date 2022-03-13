<%@ page isELIgnored="false" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>


<f:setLocale value="${sessionScope.locale}"/>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>
<body>
<f:setLocale value="${sessionScope.locale}"/>
<f:bundle basename="locale">

    <%@include file="/views/localHeader.jsp" %>
    <%@include file="/views/login.jsp" %>


    <div class="w3-container w3-section">
        <form action="authorization" method="post">
            <input class="w3-btn w3-light-green w3-round-large" type="submit" name="registration"
                    value='<f:message key="Registration"/>'>
        </form>

    </div>
</f:bundle>
</body>
</html>