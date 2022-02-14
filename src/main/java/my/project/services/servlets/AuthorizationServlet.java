package my.project.services.servlets;

import my.project.entity.Roles;
import my.project.entity.User;
import my.project.services.db.DbManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static my.project.entity.Roles.*;

public class AuthorizationServlet extends HttpServlet {

    private final ContextListener conL = new ContextListener();


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("in auth servlet");
        User user = (User) req.getAttribute("user");

        req.setCharacterEncoding("UTF-8"); //для нормальной работы с кирилицей в пост запросах; этот функционал можно/лучше вынести в фильтр (колесников покаывает в конце лекции)

        req.getSession().setAttribute("user", user);

        if (GUEST.name.equals(user.getRole())) req.getRequestDispatcher("views/guest.html").forward(req, resp);
//        resp.sendRedirect("views/guest.html"); PRG pattern (defence against sending form twice)
        if (MERCHANDISER.name.equals(user.getRole())) req.getRequestDispatcher("views/workPlace/merch.jsp").forward(req, resp);
        if (CASHIER.name.equals(user.getRole())) req.getRequestDispatcher("views/workPlace/cashier.jsp").forward(req, resp);
        if (SENIOR_CASHIER.name.equals(user.getRole())) req.getRequestDispatcher("views/workPlace/senior_cashier.jsp").forward(req, resp);




    }
}
