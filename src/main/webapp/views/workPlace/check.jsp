<%@ page isELIgnored="false" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>


<f:setLocale value="${sessionScope.locale}"/>
<f:setLocale value="${sessionScope.locale}"/>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Check</title>
</head>
<body>

<f:bundle basename="locale">
    <%--frame to login as senior--%>
    ${sessionScope.loginSenior}
    <c:if test="${sessionScope.loginSenior !=null}">
        <jsp:include page="/index.jsp"/>
    </c:if>
    <table>
        <tr>
            <th scope="col"><f:message key="Name" /></th>
            <th scope="col"><f:message key="Number" /></th>
            <th scope="col"><f:message key="Price" /></th>
            <c:if test="${sessionScope.redact !=null}">
                <th scope="col"><f:message key="Delete" /></th>
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
                        <label for="number"><f:message key="Number"/> = ${productEntity.key.number}</label>
                                <br>
                        <input type="text" maxlength="12" size="12" id="number" name="number" value="0"
                               pattern="[-]?[0-9]{1,10}[.]?[0-9]{0,2}"><br>
                        <input type="submit" name="setNumber" value="<f:message key="Accept change" />">
                            <%--message if cant add product--%>
                        <c:if test="${sessionScope.cant== productEntity.key.id}">
                            <script>
                                function cantAlert() {
                                    alert("<f:message key="noSuchNum" /> ${productEntity.key.name} ");
                                }
                            </script>
                            <br>
                            <button onclick="cantAlert()"><f:message key="problem"/></button>
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
                        <input type="submit" name="deleteProd" value="<f:message key="delete product" />">
                    </td>
                    </c:if>
            </tr>
            <tr>
                    <%--delete check--%>
                <c:if test="${sessionScope.redact !=null}">
                    <td>
                        <input type="submit" name="deleteCheck" value="<f:message key="delete check" />">
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
            <input type="submit" name="selectedProduct" value="<f:message key="get it" />">
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
                    <input type="submit" name="closeCheck" value="<f:message key="Close check" />">
                </form>
                <%--redact check--%>
                <form action="check" method="post">
                    <input type="submit" name="redact" value="<f:message key="redact as senior" />">
                </form>
            </c:if>
            <c:if test="${sessionScope.check.isClosed() == true}">
                <div>
                    <button onclick="location.href='cashier.jsp'"><f:message key="To cahier"/></button>
                </div>
            </c:if>
        </div>
        <div>
            <c:if test="${sessionScope.redact !=null}">
                <%--exit form redact mode--%>
                <form action="check" method="post">
                    <input type="submit" name="exit" value="<f:message key="exit form senior" />">
                </form>
            </c:if>
        </div>

    </c:if>
</f:bundle>
</body>
</html>