package my.project.servlets;

import my.project.services.db.DbManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ChangeRoleServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("in change role serv");

        String login = req.getParameter("login");
        System.out.println("useername = " + req.getParameter("login") );

        String role = req.getParameter("role");
        System.out.println("new role  = " + req.getParameter("role") );


        var dbm =(DbManager) getServletContext().getAttribute("dbManager");
        System.out.println(dbm.changeRole(login, role));
        resp.sendRedirect("admin");
    }
}
