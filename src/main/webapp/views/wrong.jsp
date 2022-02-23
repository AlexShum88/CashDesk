<%@ page isELIgnored="false" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>


<f:setLocale value="${sessionScope.locale}"/>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Error</title>
</head>
<body>
<f:bundle basename="locale">
    <h3><f:message key="somethingWrong"/><br>
        <f:message key="askAdmin"/>
    </h3>
    <div>
        <button onclick="location.href='index.jsp'"><f:message key="To login"/></button>
    </div>
</f:bundle>
</body>
</html>