package my.project.servlets;

import my.project.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static my.project.model.Roles.*;
/**
 * servlet who redirect users to their workplaces
 * */
public class AuthorizationServlet extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(AuthorizationServlet.class);

    private String guestPage = "views/guest.jsp";
    private String cashierPage = "views/workPlace/cashier.jsp";
    private String seniorPage = "views/workPlace/senior_cashier.jsp";
    private String merchandiser = "merchandiser";
    private String toCheck = "check";

    /**
     * redirect users to their workplaces
     * but
     * if session already have user
     * and its cashier
     * and new user its senior cashier
     * than redirect to check view for redact check as senior
     * */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.info("in auth servlet");
        LOG.debug("session id = {} ", req.getSession().getId());

        User user = (User) req.getAttribute("user");

        LOG.debug(" user {} role {}", req.getSession().getAttribute("senior"), SENIOR_CASHIER.name.equals(user.getRole()));

        User userInSession =(User) req.getSession().getAttribute("user");
        if (userInSession == null) {
            userInSession = new User();
        }
        if (SENIOR_CASHIER.name.equals(user.getRole())){
            if (CASHIER.name.equals(userInSession.getRole())){
                req.getSession().setAttribute("user", userInSession);
                req.getSession().setAttribute("senior", user);
                req.getSession().setAttribute("cashier", userInSession);

                req.getRequestDispatcher(toCheck).forward(req, resp);
                return;
            }else {
                req.getSession().setAttribute("user", user);
                req.getRequestDispatcher(seniorPage).forward(req, resp);
                return;
            }
        }
            req.getSession().setAttribute("user", user);

        if (GUEST.name.equals(user.getRole())) req.getRequestDispatcher(guestPage).forward(req, resp);
        if (MERCHANDISER.name.equals(user.getRole())) req.getRequestDispatcher(merchandiser).forward(req, resp);
        if (CASHIER.name.equals(user.getRole())) req.getRequestDispatcher(cashierPage).forward(req, resp);
    }

}
