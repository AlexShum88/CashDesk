<%@ page isELIgnored="false" %>
<%@ page import="java.util.List, java.text.*, my.project.entity.User" %>


<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Admin</title>
</head>
<body>
<h1>Hello Admin</h1>
<br>

<%
    List<User> ss = (List<User>) request.getAttribute("users");
    for (int i=0; i<ss.size();i++){
        out.println("<h6> " + ss.get(i).getLogin() + " | " + ss.get(i).getPassword() + " | " + ss.get(i).getRole() + " </h6>");
    }
%>
</body>
</html>