<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<%@ page import="java.util.List, java.text.*, my.project.entity.User" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Admin</title>
</head>
<body>
<h1>Hello Admin</h1>
<br>
<table>
    <tr>
        <th>Login</th>
        <th>Role now</th>
        <th>Change role</th>
        <th>Accept change</th>
    </tr>


    <c:forEach var="user" items="${requestScope.users}">
        <form action="change_role" method="get">
            <tr>
                <input type="hidden" name="login" value="${user.login} "/>
                <td><label>${user.getLogin()}</label></td>
                <td>${user.role}</td>

                <td>
                    <label>Choose role</label>
                    <br>
                    <input list="role" name="role">
                    <datalist id="role">
                        <option>guest</option>
                        <option>cashier</option>
                        <option>senior_cashier</option>
                        <option>merchandiser</option>
                    </datalist>
                </td>


                <td><input type="submit"></td>
            </tr>
        </form>
    </c:forEach>


    </tr>

</table>
    <hr>
    <div>
        <button onclick="location.href='index.html'">To Login</button>
    </div>
</body>
</html>