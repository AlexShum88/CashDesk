package my.project.services.servlets.filters;

import my.project.entity.User;
import my.project.services.db.DbManager;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserIsPresentFilter extends HttpFilter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        System.out.println("in user is present");
        var dbm = (DbManager) getServletContext().getAttribute("dbManager");
        System.out.println(req.getParameter("login"));
        System.out.println(req.getParameter("password"));
        var user = (User) dbm.getUser(req.getParameter("login"));

        if (user==null) {
            ((HttpServletResponse) res).sendRedirect("index.html");
//            chain.doFilter(req, res);
            return;
        }

        req.setAttribute("user", user);

        chain.doFilter(req, res);
    }
}
