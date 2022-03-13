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

    <div class="w3-panel w3-border">
        <form method="post" action="authorization">
            <label><f:message key="Login">:</f:message></label><br>
            <input type="text" required minlength="4" maxlength="16" name="login"
                   pattern="[A-Z,a-z, А-Я,а-я,0-9][a-z,а-я,0-9]+"><br>

            <label><f:message key="Password">:</f:message></label><br>
            <input type="password" required minlength="4" maxlength="30" name="password"
                   pattern="[A-Z,a-z, А-Я,а-я,0-9][a-z,а-я,0-9]+"><br>

            <input class="w3-btn w3-red w3-round-large w3-section" type="submit" value="<f:message key="Enter" />">

        </form>
    </div>
</f:bundle>
</body>
</html>