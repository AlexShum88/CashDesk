<%@ page isELIgnored="false" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>


<f:setLocale value="${sessionScope.locale}"/>


<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Merchandiser</title>
</head>
<body>
<f:bundle basename="locale">
    <table>
        <tr>
            <th scope="col"><f:message key="Id"/></th>
            <th scope="col"><f:message key="Name"/></th>
            <th scope="col"><f:message key="Price"/></th>
            <th scope="col"><f:message key="Number"/></th>
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
                        <label for="price">${product.getPrice()}</label><br>
                        <input type="text" maxlength="12" size="12" id="price" name="price"
                               value="${product.getPrice()}"
                               pattern="[-]?[0-9]{1,10}[.]?[0-9]{0,2}"><br>
                        <input type="submit" name="setPrice" value="<f:message key="Accept change" />">
                    </td>
                    <!-- todo bug is near here. if enter on text number price get its value-->
                    <td>
                        <label for="number">${product.getNumber()} +</label><br>
                        <input type="text" maxlength="12" size="12" id="number" name="number" value="0"
                               pattern="[-]?[0-9]{1,10}[.]?[0-9]{0,2}"><br>
                        <input type="submit" name="setNumber" value="<f:message key="Accept change" />">
                    </td>
                    <td>
                        <input type="submit" id="delete" name="delete" value="<f:message key="Delete" />">
                    </td>
                </form>
            </tr>
        </c:forEach>
    </table>
    <br>
    <form action="merchandiser" method="get">
        <input type="button" onclick="changeElementVisible('newName','submitNewName')"
               name="new" value="<f:message key="Add new product" />"/><br>
        <input name="newName" type="text" id="newName" size="12" value="" hidden><br>
        <input type="submit" onclick="changeElementHidden('newName','submitNewName')"
               id="submitNewName" name="insert" value="<f:message key="Add new product" />" hidden>
    </form>
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
    <div>
        <button onclick="location.href='index.jsp'"><f:message key="To login"/></button>
    </div>
</f:bundle>
</body>
</html>