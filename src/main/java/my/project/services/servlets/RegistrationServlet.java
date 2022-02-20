package my.project.services.servlets;

import my.project.entity.User;
import my.project.services.db.DbManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegistrationServlet extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(CheckServlet.class);
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.debug("in registration servlet#doPost");
        var dbm = (DbManager) getServletContext().getAttribute("dbManager");
        User user = new User(req.getParameter("login"), req.getParameter("password"), "guest");
        if(dbm.InsertUser(user)) {
            req.getRequestDispatcher("views/guest.html").forward(req, resp);
        }else req.getRequestDispatcher("views/wrong.html").forward(req, resp);

    }
}
