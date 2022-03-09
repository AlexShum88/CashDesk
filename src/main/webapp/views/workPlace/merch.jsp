<%@ page isELIgnored="false" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>


<f:setLocale value="${sessionScope.locale}"/>


<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Merchandiser</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>
<body>
<f:bundle basename="locale">
    <%@include file="/views/localHeader.jsp"%>
    <div class="w3-container">
        <table class="w3-table w3-striped w3-bordered w3-border w3-sand ">
            <tr>
                <th scope="col"><f:message key="Id"/></th>
                <th scope="col"><f:message key="Name"/></th>
                <th scope="col"><f:message key="Price"/></th>
                <th scope="col"><f:message key="Change"/></th>
                <th scope="col"><f:message key="Number"/></th>
                <th scope="col"><f:message key="Change"/></th>
                <th scope="col"><f:message key="Delete"/></th>

            </tr>
            <c:forEach var="product" items="${requestScope.listOfProduct}">

                <tr>
                    <form method="get" action="merchandiser">
                        <input type="hidden" name="prodID" value="${product.getId()}">
                        <td>
                            <label>${product.getId()}</label>
                        </td>
                        <td>
                            <label>${product.getName()}</label>
                        </td>
                        <td>
                            <label for="price">${product.getPrice()}</label>
                        </td>
                        <td>
                            <input type="text" maxlength="12" size="12" id="price" name="price"
                                   value="0"
                                   pattern="[-]?[0-9]{1,10}[.]?[0-9]{0,2}"><br>
                            <input class="w3-btn w3-green w3-round-large w3-margin-top" type="submit" name="setPrice"
                                   value="<f:message key="Accept change" />">
                        </td>
                        <td>
                            <label for="number">${product.getNumber()} +</label><br>
                        </td>
                        <td>
                            <input type="text" maxlength="12" size="12" id="number" name="number" value="0"
                                   pattern="[-]?[0-9]{1,10}[.]?[0-9]{0,2}"><br>
                            <input class="w3-btn w3-green w3-round-large w3-margin-top" type="submit" name="setNumber"
                                   value="<f:message key="Accept change" />">
                        </td>
                        <td>
                            <input class="w3-btn w3-red w3-round-large" type="submit" id="delete" name="delete"
                                   value="<f:message key="Delete" />">
                        </td>
                    </form>
                </tr>
            </c:forEach>
        </table>
    </div>
    <div class="w3-container w3-margin-top">
        <form action="merchandiser" method="get">
            <input class="w3-btn w3-orange w3-round-large" type="button"
                   onclick="changeElementVisible('newName','submitNewName')"
                   name="new" value="<f:message key="Add new product" />"/>
            <br>

            <input name="newName" type="text" id="newName" size="12" value="" hidden>
            <br>

            <input type="submit"
                   onclick="changeElementHidden('newName','submitNewName')"
                   id="submitNewName" name="insert" value="<f:message key="Add" />" hidden>
        </form>
    </div>
    <div class="w3-container">
        <button class="w3-btn w3-blue w3-round-large" onclick="location.href='index.jsp'"><f:message
                key="To login"/></button>
    </div>
</f:bundle>

<script type="text/javascript">
    function changeElementVisible(elementID1, elementID2) {
        document.getElementById(elementID1).hidden = false;
        document.getElementById(elementID2).hidden = false;

    }

    function changeElementHidden(elementID1, elementID2) {
        document.getElementById(elementID1).hidden = true;
        document.getElementById(elementID2).hidden = true;

    }
</script>

</body>
</html>