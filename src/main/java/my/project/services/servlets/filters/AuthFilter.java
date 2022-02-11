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



public class AuthFilter extends HttpFilter {


    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        var dbm = (DbManager) getServletContext().getAttribute("dbManager");
        System.out.println(req.getParameter("login"));
        var user = (User) dbm.getUser(req.getParameter("login"));

        if (user==null) {
            chain.doFilter(req, res);
            return;
        }

        System.out.println(user.getLogin());
        if (user.getRole().equals("admin")){
            ((HttpServletResponse) res).sendRedirect("admin");
        } else chain.doFilter(req, res);

    }
}
