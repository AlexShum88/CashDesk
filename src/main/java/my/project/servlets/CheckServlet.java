package my.project.servlets;

import my.project.entity.Transaction;
import my.project.entity.User;
import my.project.services.commands.check.ExitCommand;
import my.project.services.commands.check.DeleteCheckCommand;
import my.project.services.commands.check.DeleteProductFromCheckCommand;
import my.project.services.commands.check.IsSeniorComand;
import my.project.services.commands.check.*;
import my.project.services.db.DbCheckManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class CheckServlet extends HttpServlet {


    private static final Logger LOG = LogManager.getLogger(CheckServlet.class);


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.debug("checkServlet#doPost");
        LOG.debug("session id {}", req.getSession().getId());
        LOG.debug("check is {}", req.getAttribute("check"));

        //if exit from senior mode
        if (req.getParameter("exit") != null) new ExitCommand(req).execute();
        //if redact as senior
        if (req.getParameter("redact") != null || req.getSession().getAttribute("ready") != null) {
            new IsSeniorComand(req).execute();
        }

        User user = (User) req.getSession().getAttribute("user");
        var dbm = DbCheckManager.getInstance();
        Transaction transaction = dbm.getCheck(user.getId());
        req.getSession().setAttribute("check", transaction);


        resp.sendRedirect("CheckRPG");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.debug("in check#doGet");
        //log param
        LOG.debug("start log param:");
        req.getParameterMap().keySet().forEach(LOG::debug);
        LOG.debug("end log param;");

        //chose action
        if (req.getParameter("createCheck") != null) new CreateCheckCommand(req).execute();
        if (req.getParameter("selectedProduct") != null) new AddSelectedProductCommand(req).execute();
        if (req.getParameter("setNumber") != null) new SetPriceByNumberCommand(req).execute();
        if (req.getParameter("closeCheck") != null) new CloseCheckCommand(req, resp).execute();
        if (req.getParameter("deleteProd") != null) new DeleteProductFromCheckCommand(req).execute();
        if (req.getParameter("deleteCheck") != null) {
            new DeleteCheckCommand(req).execute();
            req.getRequestDispatcher("/views/workPlace/cashier.jsp").forward(req, resp);
            return;
        }
        //set total sum to db
        new SetTotalSumCommand(req).execute();
        //make attribute to translate to jsp
        resp.sendRedirect("CheckRPG");
    }

}
