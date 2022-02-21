package my.project.servlets;

import my.project.services.commands.senior.CreateGoodsReportCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SeniorServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("goods")!=null) new CreateGoodsReportCommand(req).execute();
//        if (req.getParameter("cashiers")!=null)
        resp.sendRedirect("SeniorRPG");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

       resp.sendRedirect("SeniorRPG");
    }
}
