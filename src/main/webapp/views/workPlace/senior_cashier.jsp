<%@ page isELIgnored="false" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>


<f:setLocale value="${sessionScope.locale}"/>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Senior cashier</title>
</head>
<body>
<f:bundle basename="locale">
    <form action="senior" method="get">
        <input type="submit" name="goods" value="<f:message key="Goods" />">
        <input type="submit" name="cashiers" value="<f:message key="Cashiers" />">
    </form>


    <c:if test="${sessionScope.allProd!=null}">

        <table>
            <tr>
                <th scope="col"><f:message key="Goods"/></th>
                <th scope="col"><f:message key="Sold"/></th>
                <th scope="col"><f:message key="Total cash"/></th>
            </tr>
            <c:forEach var="prod" items="${sessionScope.allProd}">
                <tr>
                    <td>${prod.name}</td>
                    <td>${prod.num}</td>
                    <td>${prod.cash}</td>
                </tr>

            </c:forEach>

        </table>

    </c:if>

    <c:if test="${sessionScope.cashiers!=null}">
        <table>
            <tr>
                <th scope="col"><f:message key="Cashier id"/></th>
                <th scope="col"><f:message key="All checks"/></th>
                <th scope="col"><f:message key="Canceled Checks"/></th>
                <th scope="col"><f:message key="Sales"/></th>
            </tr>
            <c:forEach var="cashier" items="${sessionScope.cashiers}">
                <tr>
                    <td>${cashier.id}</td>
                    <td>${cashier.checks}</td>
                    <td>${cashier.isClosed}</td>
                    <td>${cashier.sum}</td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
    <div>
        <br>
        <button onclick="location.href='index.jsp'"><f:message key="To login"/></button>
    </div>
</f:bundle>
</body>
</html>