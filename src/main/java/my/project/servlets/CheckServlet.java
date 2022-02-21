package my.project.servlets;

import my.project.services.commands.check.*;
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

        req.getSession().setAttribute("check", null);
        LOG.debug("in session get senior {}", req.getSession().getAttribute("senior"));
        if (req.getSession().getAttribute("senior") != null) {
            req.getRequestDispatcher("/views/workPlace/check_edit_with_senior.jsp").forward(req, resp);
        } else {
            req.getSession().setAttribute("senior", null);
        }

        LOG.debug("redact param {}", req.getParameter("redact"));
        if (req.getParameter("redact") != null) {
            req.getRequestDispatcher("/index.html").forward(req, resp);
        }
        req.getRequestDispatcher("/views/workPlace/check_edit.jsp").forward(req, resp);
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
        if (req.getParameter("closeCheck") != null) {
            new CloseCheckCommand(req, resp).execute();
        }

        //set total sum to db
        new SetTotalSumCommand(req).execute();
        //make attribute to translate to jsp
        resp.sendRedirect("CheckRPG");
    }


}
