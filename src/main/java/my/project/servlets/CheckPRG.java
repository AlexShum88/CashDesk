package my.project.servlets;

import my.project.db.DbCheckManager;
import my.project.db.DbProductManager;
import my.project.model.Product;
import my.project.model.Transaction;
import my.project.services.commands.check.PrintCheckCommand;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * servlet to execute PRG pattern
 */
@WebServlet(name = "CheckPRG", value = "/CheckPRG")
public class CheckPRG extends HttpServlet {
    private String toCheck = "/views/workPlace/check.jsp";
    private String toWrong = "views/wrong.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            setAttributeForJsp(req);
            if (req.getSession().getAttribute("print") != null) {
                new PrintCheckCommand(req, resp).execute();
            }
            req.getRequestDispatcher(toCheck).forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            req.getRequestDispatcher(toWrong).forward(req, resp);
        }
    }

    /**
     * prepare attributes to create view
     */
    private void setAttributeForJsp(HttpServletRequest req) {
        DbCheckManager dbm = DbCheckManager.getInstance();
        Transaction check = (Transaction) req.getSession().getAttribute("check");
        Map<Product, Double> productsAndCurrentPrise = new LinkedHashMap<>();
        List<Product> products = dbm.getAllProdOfCheck(check.getId());
        for (int i = 0; i < products.size(); i++) {
            products.get(i).setNumber(dbm.getNumber(check.getId(), products.get(i).getId()));
            productsAndCurrentPrise.put(products.get(i), dbm.getCurrentPrice(check.getId(), products.get(i).getId()));
        }
        //for prod list on jsp
        req.setAttribute("products", productsAndCurrentPrise);
        //for choose product on jsp
        List<Product> allProducts = DbProductManager.getInstance().getAllProducts();
        req.setAttribute("allProducts", allProducts);
        //for total sum
        req.setAttribute("totalSum", dbm.getTotalSum(check.getId()));
    }


}
