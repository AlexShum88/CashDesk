package my.project.servlets;

import my.project.entity.Product;
import my.project.entity.Transaction;
import my.project.services.commands.*;
import my.project.services.db.DbCheckManager;
import my.project.services.db.DbProductManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;


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

        //chose action first block
        if (req.getParameter("createCheck") != null) new CreateCheckCommand(req).execute();
        if (req.getParameter("selectedProduct") != null) new AddSelectedProductCommand(req).execute();
        if (req.getParameter("setNumber") != null) new SetPriceByNumberCommand(req).execute();
        if (req.getParameter("closeCheck") != null) {
            new CloseCheckCommand(req, resp).execute();
        }

        setAttributeToJsp(req);

        LOG.debug("check form servlet {}", req.getSession().getAttribute("check"));
        //get product + current price

        //set total sum to db
        new SetTotalSumCommand(req).execute();
        //make attribute to translate to jsp

        req.getRequestDispatcher("/views/workPlace/check.jsp").forward(req, resp);
    }

    private void setAttributeToJsp(HttpServletRequest req) {
        DbCheckManager dbm = DbCheckManager.getInstance();
        Transaction check = (Transaction) req.getSession().getAttribute("check");
        Map<Product, Double> productsAndCurrentPrise = new LinkedHashMap<>();
        List<Product> products = dbm.getAllProdOfCheck(check.getId());
        for (int i = 0; i < products.size(); i++) {
            productsAndCurrentPrise.put(
                    products.get(i),
                    dbm.getCurrentPrice(
                            check.getId(),
                            products.get(i).getId()
                    )
            );
        }
        //for prod list on jsp
        req.setAttribute("products", productsAndCurrentPrise);
        //for choose product on jsp
        List<Product> allProducts = DbProductManager.getInstance().getAllProducts();
        req.setAttribute("allProducts", allProducts);
        //for total sum
        req.setAttribute("totalSum", dbm.getTotalSum(check.getId()));
        //send
    }

}
