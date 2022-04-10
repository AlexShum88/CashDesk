<%@ page isELIgnored="false" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>


<f:setLocale value="${sessionScope.locale}"/>


<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Check</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>
<body>
<%@include file="/views/localHeader.jsp" %>
<f:bundle basename="locale">
    <%--frame to login as senior--%>
    <c:if test="${sessionScope.loginSenior !=null}">
        <jsp:include page="/views/login.jsp"/>
    </c:if>
    <div class="w3-container">
        <table class="w3-table w3-striped w3-bordered w3-border w3-sand ">
            <tr>
                <th scope="col"><f:message key="Name"/></th>
                <th scope="col"><f:message key="Number"/></th>
                <th scope="col"><f:message key="Change Number"/></th>
                <th scope="col"><f:message key="Price"/></th>
                <th scope="col"><f:message key="Current Price"/></th>
                <c:if test="${sessionScope.redact !=null}">
                    <th scope="col"><f:message key="Delete"/></th>
                </c:if>

            </tr>

            <c:forEach var="productEntity" items="${requestScope.products}">
                <tr>
                    <form action="check" method="get">
                        <input type="hidden" name="productId" value="${productEntity.key.id}">
                        <td>
                                <%--name--%>
                            <c:if test="${productEntity.key.number == 0}">
                                <label class="w3-text-red">${productEntity.key.name}</label>
                            </c:if>
                            <c:if test="${productEntity.key.number != 0}">
                                <label class="w3-text-black">${productEntity.key.name}</label>
                            </c:if>
                        </td>
                        <td>
                                <%--number--%>
                            <p>${productEntity.key.number}</p>
                        <td>
                            <input type="text" maxlength="12" size="12" id="number" name="number" value="0"
                                   pattern="[-]?[0-9]{1,10}[.]?[0-9]{0,2}"><br>
                            <input class="w3-btn w3-blue w3-round-large w3-margin-top" type="submit" name="setNumber"
                                   value="<f:message key="Accept change" />">
                                <%--message if cant add product--%>
                            <c:if test="${sessionScope.cant == productEntity.key.id}">
                            <script>
                                function cantAlert() {
                                    alert("<f:message key="noSuchNum" /> ${productEntity.key.name} ");
                                }
                            </script>
                            <br>
                            <button class="w3-btn w3-red w3-round-large w3-margin-top" onclick="cantAlert()"><f:message
                                    key="problem"/></button>
                        </td>
                        </c:if>

                        <td>
                                <%--price--%>
                            <label>${productEntity.key.price}</label>
                        </td>

                        <td>
                                <%--current price--%>
                            <label>${productEntity.value}</label>
                        </td>
                            <%--delete product--%>
                        <c:if test="${sessionScope.redact !=null}">
                        <td>
                            <input class="w3-btn w3-red w3-round-large w3-margin-top" type="submit" name="deleteProd"
                                   value="<f:message key="delete product" />">
                        </td>
                        </c:if>
                </tr>
                <tr>
                        <%--delete check--%>
                    <c:if test="${sessionScope.redact !=null}">
                        <td>
                            <input class="w3-btn w3-red w3-round-large w3-margin-top" type="submit" name="deleteCheck"
                                   value="<f:message key="delete check" />">
                        </td>
                    </c:if>
                </tr>
                </form>
            </c:forEach>

        </table>
    </div>
    <%--total sum--%>
    <div class="w3-container">
        <label><b><f:message key="Total sum"/> = ${requestScope.totalSum}</b></label>
    </div>
    <%--product choose--%>
    <div class="w3-container">
        <table>
            <tr>
                <th></th>
                <th></th>
            </tr>
            <tr>
                <form action="check" method="get">
                    <td>

                        <label for="product"><f:message key="Choose product"/> </label><br>
                        <input list="product" name="product">
                        <datalist id="product">
                            <c:forEach var="product" items="${requestScope.allProducts}">
                            <option value="${product.id} ${product.name}">
                                </c:forEach>
                        </datalist>
                    </td>
                    <td>
                        <input class="w3-btn w3-green w3-round-large w3-margin-top" type="submit" name="selectedProduct"
                               value="<f:message key="get it" />">
                </form>
                </td>
            </tr>
        </table>
    </div>

    <%--prohibits editing the receipt after closing--%>
    <c:if test="${sessionScope.check.isClosed() == false}">

        <div class="w3-container w3-padding-16">
            <c:if test="${sessionScope.redact ==null}">
                <%--close check--%>
                <form action="check" method="get">
                    <input class="w3-btn w3-orange w3-round-large w3-margin-top" type="submit" name="closeCheck"
                           value="<f:message key="Close check" />">
                </form>
                <%--redact check--%>
                <form action="check" method="post">
                    <input class="w3-btn w3-red w3-round-large w3-margin-top" type="submit" name="redact"
                           value="<f:message key="redact as senior" />">
                </form>
            </c:if>
            <c:if test="${sessionScope.check.isClosed() == true}">
                <div class="w3-container">
                    <button class="w3-btn w3-green w3-round-large w3-margin-top" onclick="location.href='cashier.jsp'">
                        <f:message key="To cahier"/></button>
                </div>
            </c:if>
        </div>
        <div class="w3-container">
            <c:if test="${sessionScope.redact !=null}">
                <%--exit form redact mode--%>
                <form action="check" method="post">
                    <input class="w3-btn w3-red w3-round-large w3-margin-top" type="submit" name="exit"
                           value="<f:message key="exit form senior" />">
                </form>
            </c:if>
        </div>

    </c:if>
</f:bundle>
</body>
</html>