package my.project.servlets;

import my.project.services.db.DbManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * servlet to change role of user
 * */
public class ChangeRoleServlet extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(ChangeRoleServlet.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.info("in change role serv");

        String login = req.getParameter("login");
        LOG.debug("useername = {}", req.getParameter("login") );

        String role = req.getParameter("role");
        LOG.debug("new role  = {}", req.getParameter("role"));


        var dbm =(DbManager) getServletContext().getAttribute("dbManager");
        dbm.changeRole(login, role);
        resp.sendRedirect("admin");
    }
}
