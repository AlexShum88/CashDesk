package my.project.servlets;

import my.project.db.DbProductManager;
import my.project.services.commands.merch.ChangeNumberCommand;
import my.project.services.commands.merch.ChangePriceCommand;
import my.project.services.commands.merch.DeleteProductCommand;
import my.project.services.commands.merch.InsertProductCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * servlet to work with merchandiser functional
 */
public class MerchandiserServlet extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(MerchandiserServlet.class);
    private String toWrong = "views/wrong.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            DbProductManager productManager = DbProductManager.getInstance();
            req.getSession().setAttribute("dbm", productManager);

            resp.sendRedirect("MerchPRG");
        } catch (Exception e) {
            req.getRequestDispatcher(toWrong).forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("insert") != null) new InsertProductCommand(req).execute();
        if (req.getParameter("delete") != null) new DeleteProductCommand(req).execute();
        if (req.getParameter("setPrice") != null) new ChangePriceCommand(req).execute();
        if (req.getParameter("setNumber") != null) new ChangeNumberCommand(req).execute();

        resp.sendRedirect("MerchPRG");
    }


}
