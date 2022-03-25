package my.project.servlets.filters;

import my.project.model.User;
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
 * second filter
 * check if user enter correct password
 * else return user to login page
 * */
public class PasswordMatchFilter extends HttpFilter {
    private static final Logger LOG = LogManager.getLogger(PasswordMatchFilter.class);
    String loginPage = "index.jsp";

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        LOG.info("in pass test");
        var user = (User) req.getAttribute("user");
        var pass = req.getParameter("password");
        if (pass.equals(user.incriptPassword())) {
            chain.doFilter(req, res);
        } else {
            ((HttpServletResponse) res).sendRedirect(loginPage);

        }
    }
}
