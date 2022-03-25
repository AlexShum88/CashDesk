package my.project.servlets;

import my.project.model.User;
import my.project.db.DbManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * servlet for registration of new users
 * if username is not exist in db its redirect user to guest page
 * else its redirect user to wrong page
 * */
public class RegistrationServlet extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(RegistrationServlet.class);
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String toGuest = "views/guest.jsp";
        String toWrong = "views/wrong.jsp";
        LOG.info("in registration servlet#doPost");
        var dbm = (DbManager) getServletContext().getAttribute("dbManager");
        User user = new User(req.getParameter("login"), req.getParameter("password"), "guest");
        if(dbm.InsertUser(user)) {
            req.getRequestDispatcher(toGuest).forward(req, resp);
        }else req.getRequestDispatcher(toWrong).forward(req, resp);

    }
}
