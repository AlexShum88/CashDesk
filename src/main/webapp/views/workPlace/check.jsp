<%@ page isELIgnored="false" %>
<%@ page import="java.util.List, java.text.*, my.project.entity.Product" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Check</title>
</head>
<body>
<%--frame to login as senior--%>
<c:if test="${sessionScope.loginSenior !=null}">
    <%@ include file="/index.html" %>
</c:if>
<table>
    <tr>
        <th>Name</th>
        <th>Number</th>
        <th>Price</th>
        <c:if test="${sessionScope.redact !=null}">
            <th>Delete</th>
        </c:if>

    </tr>

    <c:forEach var="productEntity" items="${requestScope.products}">
        <tr>
            <form action="check" method="get">
                <input type="hidden" name="productId" value="${productEntity.key.id}">
                <td>
                        <%--name--%>
                    <label>${productEntity.key.name}</label>
                </td>
                <td>
                        <%--number--%>
                    <input type="text" maxlength="12" size="12" id="number" name="number" value="0"
                           pattern="[-]?[0-9]{1,10}[.]?[0-9]{0,2}"><br>
                    <input type="submit" name="setNumber" value="Accept">
                        <%--message if cant add product--%>
                    <c:if test="${sessionScope.cant== productEntity.key.id}">
                        <script>
                            function cantAlert() {
                                alert("no such number of ${productEntity.key.name} in stock");
                            }
                        </script>
                        <br>
                        <button onclick="cantAlert()">problem</button>
                    </c:if>
                </td>
                <td>
                        <%--price--%>
                    <label>Price = ${productEntity.key.price}</label><br>
                    <label>Current price = ${productEntity.value}</label>
                </td>
                    <%--delete product--%>
                <c:if test="${sessionScope.redact !=null}">
                <td>
                    <input type="submit" name="deleteProd" value="delete product">
                </td>
                </c:if>
        </tr>
        <tr>
                <%--delete check--%>
            <c:if test="${sessionScope.redact !=null}">
                <td>
                <input type="submit" name="deleteCheck" value="delete check">
                </td>
            </c:if>
        </tr>
        </form>
    </c:forEach>

</table>

<%--product choose--%>
<div>
    <form action="check" method="get">
        <label for="product">choose product</label><br>
        <input list="product" name="product">
        <datalist id="product">
            <c:forEach var="product" items="${requestScope.allProducts}">
            <option value="${product.id} ${product.name}">
                </c:forEach>
        </datalist>
        <input type="submit" name="selectedProduct" value="get it">
    </form>
</div>
<%--total sum--%>
<div>
    <label>Total sum = ${requestScope.totalSum}</label>
</div>
<%--prohibits editing the receipt after closing--%>
<c:if test="${sessionScope.check.isClosed() == false}">

    <div>
        <c:if test="${sessionScope.redact ==null}">
            <%--close check--%>
        <form action="check" method="get">
            <input type="submit" name="closeCheck" value="Close check">
        </form>
            <%--redact check--%>
        <form action="check" method="post">
            <input type="submit" name="redact" value="redact as senior">
        </form>
        </c:if>
    </div>
    <div>
        <c:if test="${sessionScope.redact !=null}">
        <%--exit form redact mode--%>
        <form action="check" method="post">
            <input type="submit" name="exit" value="exit form senior">
        </form>
        </c:if>
    </div>

</c:if>

</body>
</html>