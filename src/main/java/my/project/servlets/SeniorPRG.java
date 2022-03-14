package my.project.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * servlet to execute PRG pattern
 * */
@WebServlet(name = "SeniorPRG", value = "/SeniorPRG")
public class SeniorPRG extends HttpServlet {
    String toSenior = "/views/workPlace/senior_cashier.jsp";
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.getRequestDispatcher(toSenior).forward(req, resp);
    }
}
