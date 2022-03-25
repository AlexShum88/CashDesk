package my.project.servlets.filters;

import my.project.model.User;
import my.project.db.DBException;
import my.project.db.DbManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * first filter
 * its check if user is present in db
 * else its return user to login page
 * if user want registration its redirect to registration page
 * */
public class UserIsPresentFilter extends HttpFilter {
    private static final Logger LOG = LogManager.getLogger(UserIsPresentFilter.class);
    String registrationPage = "registration.jsp";
    String loginPage = "index.jsp";
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        LOG.info("in user is present");
        LOG.debug("get login {}", req.getParameter("login"));
        LOG.debug("get password {}", req.getParameter("password"));
        var user = createUser(req.getParameter("login"));

        if (user==null) {
            if (req.getParameter("registration")!=null) {
                ((HttpServletResponse) res).sendRedirect(registrationPage);
            } else((HttpServletResponse) res).sendRedirect(loginPage);

            return;
        }

        req.setAttribute("user", user);

        chain.doFilter(req, res);
    }
    /**
     * method to get user from db
     * @param login login of user
     * */
    private User createUser(String login){
        var dbm = (DbManager) getServletContext().getAttribute("dbManager");
        try {
            return dbm.getUser(login);
        } catch (DBException e) {
            e.printStackTrace();
        }
        return null;
    }
}
