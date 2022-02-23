<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<%@ page import="java.util.List, java.text.*, my.project.entity.User" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt"%>

<f:setLocale value="${sessionScope.locale}"/>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Admin</title>
</head>
<body>
<f:bundle basename="locale">
    <%@include file="/views/localHeader.jsp"%>
<h3><f:message key="Hello" /> Admin</h3>
<br>
<table>
    <tr>
        <th><f:message key="Login" /></th>
        <th><f:message key="Role now" /></th>
        <th><f:message key="Change role" /></th>
        <th><f:message key="Accept change" /></th>
    </tr>


    <c:forEach var="user" items="${requestScope.users}">
        <form action="change_role" method="get">
            <tr>
                <input type="hidden" name="login" value="${user.login} "/>
                <td><label>${user.getLogin()}</label></td>
                <td>${user.role}</td>

                <td>
                    <label><f:message key="Choose role" /></label>
                    <br>
                    <input list="role" name="role">
                    <datalist id="role">
                        <option value="guest"><f:message key="guest" /></option>
                        <option value="cashier"><f:message key="cashier" /></option>
                        <option value="senior_cashier"><f:message key="senior cashier" /></option>
                        <option value="merchandiser"><f:message key="merchandiser" /></option>
                        <option value="admin"><f:message key="admin" /></option>
                    </datalist>
                </td>


                <td><input type="submit" value="<f:message key="Accept change" />"></td>
            </tr>
        </form>
    </c:forEach>


    </tr>

</table>
    <hr>
    <div>
        <button onclick="location.href='index.jsp'"><f:message key="To login" /></button>
    </div>
</f:bundle>
</body>
</html>