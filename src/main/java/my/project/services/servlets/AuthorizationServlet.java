package my.project.services.servlets;

import my.project.entity.User;
import my.project.services.db.DbManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthorizationServlet extends HttpServlet {

    private final ContextListener conL = new ContextListener();


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var manager =(DbManager) getServletContext().getAttribute("dbManager");
        System.out.println(req.getParameter("login"));
        System.out.println(req.getParameter("password"));
        User user = new User(
                req.getParameter("login"),
                req.getParameter("password"),
                "guest"
        );

        manager.InsertUser(user);

        req.getRequestDispatcher("views/guest.html").forward(req, resp);

    }
}
