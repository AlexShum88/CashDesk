package my.project.servlets;

import my.project.db.DbManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * prepare admin view and redirect to it
 * */
public class AdminServlet extends HttpServlet {
    String adminPage = "views/admin.jsp";
    private static final Logger LOG = LogManager.getLogger(AdminServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.info("in Admin Servlet");
        var manager =(DbManager) getServletContext().getAttribute("dbManager");
        var ss = manager.getAllUsers();
        req.setAttribute("users", ss);
        req.getRequestDispatcher(adminPage).forward(req,resp);
    }
}
