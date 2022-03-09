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
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>
<body>
<%@include file="/views/localHeader.jsp" %>
<f:bundle basename="locale">
    <div class="w3-container">
    <form action="senior" method="get">
        <input class="w3-btn w3-blue-gray w3-round-large" type="submit" name="goods" value="<f:message key="Goods" />">
        <input class="w3-btn w3-blue-gray w3-round-large" type="submit" name="cashiers" value="<f:message key="Cashiers" />">
    </form>
    </div>

    <c:if test="${sessionScope.allProd!=null}">
    <div class="w3-container">
        <table class="w3-table w3-striped w3-bordered w3-border w3-sand w3-margin-top" >
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
        </div>
    </c:if>

    <c:if test="${sessionScope.cashiers!=null}">
        <div class="w3-container">
        <table class="w3-table w3-striped w3-bordered w3-border w3-blue-gray w3-text-black w3-margin-top">
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
        </div>
    </c:if>
    <div class="w3-container w3-margin-top">
        <br>
        <button class="w3-btn w3-blue w3-round-large" onclick="location.href='index.jsp'"><f:message key="To login"/></button>
    </div>
</f:bundle>
</body>
</html>