<%@ page isELIgnored="false" %>
<%@ page import="java.util.List, java.text.*, my.project.entity.Product" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>


<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<table>
    <tr>
        <th>Name</th>
        <th>Price</th>
        <th>Number</th>
        <th>Delete</th>

    </tr>
    <c:forEach var="product" items="${requestScope.listOfProduct}">
        <tr>
            <form method="get" action="merchandiser">
                <input type="hidden" name="prodName" value="${product.getName()}">
                <td>
                    <label>${product.getName()}</label>
                </td>
                <td>
                    <label for="price">${product.getPrice()} +</label><br>
                    <input type="text" maxlength="12" size="12" id="price" name="price" value="${product.getPrice()}"
                           pattern="[-]?[0-9]{1,10}[.]?[0-9]{0,2}"><br>
                    <input type="submit" name="setPrice" value="Accept">
                </td>
                <td>
                    <label for="number">${product.getNumber()} +</label><br>
                    <input type="text" maxlength="12" size="12" id="number" name="number" value="${product.getNumber()}"
                           pattern="[-]?[0-9]{1,10}[.]?[0-9]{0,2}"><br>
                    <input type="submit" name="setNumber" value="Accept">
                </td>
                <td>
                    <input type="submit" id="delete" name="delete" value="Delete">
                </td>
            </form>
        </tr>
    </c:forEach>
        <tr>
            <td>
                <form action="merchandiser" method="get">
                    <input type="button" onclick="changeElementVisible('newName','submitNewName')"
                           name="new" value="Add new product"/><br>
                    <input name="newName" type="text" id="newName"  size="12" value="" hidden><br><br>
                    <input type="submit" onclick="changeElementHidden('newName','submitNewName')"
                           id="submitNewName" name="insert" value="Add product" hidden>
                </form>
            </td>
        </tr>
</table>
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