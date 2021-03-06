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

import static my.project.model.Roles.ADMIN;

/**
 * check if user is admin
 * if true redirect user to admin servlet
 */
public class AdminFilter extends HttpFilter {
    private static final Logger LOG = LogManager.getLogger(AdminFilter.class);

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        LOG.info("in is admin testing");
        var user = (User) req.getAttribute("user");
        LOG.debug("user role = {}", user.getRole());
        if (ADMIN.name.equals(user.getRole())) {
            LOG.debug("user==admin");
            ((HttpServletResponse) res).sendRedirect("admin");
        } else chain.doFilter(req, res);
    }


}
