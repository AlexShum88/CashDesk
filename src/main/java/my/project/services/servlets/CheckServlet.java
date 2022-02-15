package my.project.services.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CheckServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //todo check create
        System.out.println("in check#doPost");
        System.out.println("session id "+req.getSession().getId());

        req.getSession().setAttribute("check", null);


        System.out.println("in session get senior "+req.getSession().getAttribute("senior"));

        if (req.getSession().getAttribute("senior")!=null){
            req.getRequestDispatcher("/views/workPlace/check_edit_with_senior.jsp").forward(req, resp);
        }else {
            req.getSession().setAttribute("senior", null);
        }

        System.out.println("redact param"+req.getParameter("redact"));
        if (req.getParameter("redact")!=null){
            req.getRequestDispatcher("/index.html").forward(req, resp);
        }
        req.getRequestDispatcher("/views/workPlace/check_edit.jsp").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //todo check create
        System.out.println("in check#doGet");


        req.getRequestDispatcher("/views/workPlace/check_edit.html").forward(req, resp);
    }
}
