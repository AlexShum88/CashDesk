package my.project.servlets.filters;

import com.mysql.cj.log.Log;
import my.project.entity.User;
import my.project.services.db.DBException;
import my.project.services.db.DbManager;
import my.project.servlets.AuthorizationServlet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserIsPresentFilter extends HttpFilter {
    private static final Logger LOG = LogManager.getLogger(UserIsPresentFilter.class);
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        LOG.debug("in user is present");
        LOG.debug("get login"+req.getParameter("login"));
        LOG.debug("get password"+req.getParameter("password"));
        var user = createUser(req.getParameter("login"));

        if (user==null) {
            ((HttpServletResponse) res).sendRedirect("index.html");
//            chain.doFilter(req, res);
            return;
        }

        req.setAttribute("user", user);

        chain.doFilter(req, res);
    }

    private User createUser(String login){
        var dbm = (DbManager) getServletContext().getAttribute("dbManager");
        try {
            return (User) dbm.getUser(login);
        } catch (DBException e) {
            e.printStackTrace();
        }
        return null;
    }
}
