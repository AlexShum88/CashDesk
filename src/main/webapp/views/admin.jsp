<%@ page isELIgnored="false" %>
<%@ page import="java.util.List, java.text.*, my.project.entity.User" %>
<%@ page contentType="text/html; charset=UTF-8"%>

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
<%
    List<User> ss = (List<User>) request.getAttribute("users");

    for (int i=0; i<ss.size();i++){
        out.println("<tr>");

        out.println("<form action=\"change_role\" method=\"get\">");
            out.println("<td><input name=\"login\" readonly value=\" " +ss.get(i).getLogin() + "\"/></td>");
            out.println("<td> " + ss.get(i).getRole() + " </td>");
            out.println("<td> ");

            out.println("<input type=\"radio\" name=\"role\" value=\"guest\">guest</input><br>");
            out.println("<input type=\"radio\" name=\"role\" value=\"cashier\">cashier</input><br>");
            out.println("<input type=\"radio\" name=\"role\" value=\"senior_cashier\">senior cashier</input><br> ");
            out.println("<input type=\"radio\" name=\"role\" value=\"merchandiser\">merchandiser</input><br></td>");

            out.println("<td> <input type=\"submit\">");
            out.println("</form>");
            out.println("</td>");
            out.println("</tr>");
    }
%>
</table>
<bd>
<hr>
<div>
    <button onclick="location.href='index.html'">To Login</button>
</div>
</body>
</html>