package my.project.servlets.filters;

import my.project.entity.User;
import my.project.services.db.DbManager;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PasswordMatchFilter extends HttpFilter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        System.out.println("in pass test");
       var user = (User) req.getAttribute("user");
       var pass = (String) req.getParameter("password");
        System.out.println("password from user = " +pass);
        System.out.println("password from db = "+ user.getPassword());
        System.out.println("password after incript = "+ user.incriptPassword());
       if (pass.equals(user.incriptPassword())){
           chain.doFilter(req, res);
       }else {
           ((HttpServletResponse) res).sendRedirect("index.html");

       }
    }
}
