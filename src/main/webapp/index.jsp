<%@ page isELIgnored="false" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt"%>


<f:setLocale value="${sessionScope.locale}"/>

<!DOCTYPE html>
<html >
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<f:setLocale value="${sessionScope.locale}"/>
<f:bundle basename="locale">
<%--<c:import url="localHeader.jsp" var="local"></c:import>--%>
<%@include file="/views/localHeader.jsp"%>
<br>
<hr>
<br>
<form method="post" action="authorization">
    <label><f:message key="Login" >:</f:message></label><br>
    <input type="text" required minlength="4" maxlength="16" name="login" pattern="[A-Z,a-z, А-Я,а-я][a-z,а-я]+"><br>

    <label><f:message key="Password" >:</f:message></label><br>
    <input type="password" required minlength="4" maxlength="30" name="password" pattern="[A-Z,a-z, А-Я,а-я,0-9][a-z,а-я,0-9]+"><br>
    <br>
    <input type="submit" value="<f:message key="Enter" />"><br>

</form>
<br>
<hr>
<br>
<button name="registration" onclick="location.href='views/registration.jsp'"><f:message key="Registration" /></button>
<br>
<hr>
</f:bundle>
</body>
</html>