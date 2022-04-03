package my.project.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * servlet to execute PRG pattern
 */
@WebServlet(name = "SeniorPRG", value = "/SeniorPRG")
public class SeniorPRG extends HttpServlet {
    private String toSenior = "/views/workPlace/senior_cashier.jsp";
    private String toWrong = "views/wrong.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.getRequestDispatcher(toSenior).forward(req, resp);
        } catch (Exception e) {
            req.getRequestDispatcher(toWrong).forward(req, resp);
        }
    }
}
