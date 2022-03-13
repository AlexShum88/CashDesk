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
    <f:bundle basename="locale">
    <script>

        function validatePass() {

            var pass = document.getElementById("pass");
            var confPass = document.getElementById("confPass");
            var confMessage = document.getElementById("confMessage");
            var regButt = document.getElementById("reg");


            if (pass.value !== confPass.value) {
                confMessage.innerText = "<f:message key='passNotMatch' />";
                regButt.disabled = true;
            } else {
                confMessage.innerText = "<f:message key='passMatch' />!";
                regButt.disabled = false;
            }


        }
    </script>
</head>
<body>
<%@include file="/views/localHeader.jsp" %>

<div class="w3-container">
    <form action="registrationS" method="post">
        <label><f:message key="Login"/></label><br>
        <input type="text" required minlength="6" maxlength="16" name="login"
               pattern="[A-Z,a-z, А-Я,а-я,0-9][a-z,а-я,0-9]+"><br>
        <label><f:message key="Password"/></label><br>
        <input type="password" required min="6" maxlength="30" id="pass" name="password"
               pattern="[A-Z,a-z, А-Я,а-я,0-9][a-z,а-я,0-9]+" oninput="return validatePass()">
        <br>
        <label><f:message key="Conform password"/></label><br>
        <input type="password" required min="6" maxlength="30" id="confPass" oninput="return validatePass()"><br>
        <br>
        <label id="confMessage"></label>
        <br>
        <input class="w3-btn w3-green w3-round-large" type="submit" value="<f:message key="Registration" />" id="reg"
               disabled><br>
    </form>
</div>
<div class="w3-container">
    <button class="w3-btn w3-blue w3-round-large w3-margin-top" onclick="location.href='index.jsp'"><f:message
            key="To login"/></button>
</div>
</f:bundle>
</body>
</html>
