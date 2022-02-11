package my.project.services.servlets;

import my.project.services.db.DbManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AdminServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var manager =(DbManager) getServletContext().getAttribute("dbManager");
        var ss = manager.getAllUsers();
        req.setAttribute("users", ss);
        req.getRequestDispatcher("views/admin.jsp").forward(req,resp);
    }
}
