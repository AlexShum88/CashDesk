package my.project.servlets;

import my.project.db.DbProductManager;
import my.project.model.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * servlet to execute PRG pattern
 */
@WebServlet(name = "MerchPRG", value = "/MerchPRG")
public class MerchPRG extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(MerchPRG.class);
    private String toWrong = "views/wrong.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute("listOfProduct", viewProduct(req));
            req.getRequestDispatcher("views/workPlace/merch.jsp").forward(req, resp);
        } catch (Exception e) {
            req.getRequestDispatcher(toWrong).forward(req, resp);
        }
    }

    /**
     * @return list of product - the main point of merchandiser functional
     */
    private List<Product> viewProduct(HttpServletRequest req) {
        LOG.debug("in view product");
        var dbm = (DbProductManager) req.getSession().getAttribute("dbm");
        return dbm.getAllProducts();
    }


}
