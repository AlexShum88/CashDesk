package my.project.services.servlets.filters;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import my.project.entity.User;
import my.project.services.db.DbManager;


public class AdminFilter extends HttpFilter {


    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        System.out.println("in is admin testing");
        var user = (User) req.getAttribute("user");
        System.out.println("user role = "+ user.getRole());
        if ("admin".equals(user.getRole())) {
            System.out.println("admin==admin");
            ((HttpServletResponse) res).sendRedirect("admin");
        }else chain.doFilter(req, res);
    }
}
