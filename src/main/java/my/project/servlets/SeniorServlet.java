package my.project.servlets;

import my.project.services.commands.senior.CreateCashiersCommand;
import my.project.services.commands.senior.CreateGoodsReportCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * servlet to work with senior cashier main functional
 */
public class SeniorServlet extends HttpServlet {
    private String toWrong = "views/wrong.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (req.getParameter("goods") != null) new CreateGoodsReportCommand(req).execute();
            if (req.getParameter("cashiers") != null) new CreateCashiersCommand(req).execute();
            resp.sendRedirect("SeniorPRG");
        } catch (Exception e) {
            req.getRequestDispatcher(toWrong).forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            resp.sendRedirect("SeniorPRG");
        } catch (Exception e) {
            req.getRequestDispatcher(toWrong).forward(req, resp);
        }
    }
}
