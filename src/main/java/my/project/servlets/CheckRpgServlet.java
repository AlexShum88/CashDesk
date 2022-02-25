package my.project.servlets;

import my.project.entity.Product;
import my.project.entity.Transaction;
import my.project.services.db.DbCheckManager;
import my.project.services.db.DbProductManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/CheckRPG")
public class CheckRpgServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setAttributeToJsp(req);
        req.getRequestDispatcher("/views/workPlace/check.jsp").forward(req, resp);
    }

    private void setAttributeToJsp(HttpServletRequest req) {
        DbCheckManager dbm = DbCheckManager.getInstance();
        Transaction check = (Transaction) req.getSession().getAttribute("check");
        Map<Product, Double> productsAndCurrentPrise = new LinkedHashMap<>();
        List<Product> products = dbm.getAllProdOfCheck(check.getId());
        for (int i = 0; i < products.size(); i++) {
            products.get(i).setNumber(dbm.getNumber(check.getId(), products.get(i).getId()));
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
