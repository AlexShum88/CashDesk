package my.project.services.checkServises;

import my.project.entity.Product;
import my.project.entity.Transaction;
import my.project.entity.User;
import my.project.services.db.DbCheckManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CloseCheck {
    private static final Logger LOG = LogManager.getLogger(CloseCheck.class);
    Transaction check;
    User user;
    List<Product> products;
    Map<Product, Double> productsAndCurrentPrise;
    HttpServletResponse resp;
    public CloseCheck(HttpServletRequest req, HttpServletResponse resp, DbCheckManager dbm) throws ServletException, IOException {
        this.resp = resp;
        check = (Transaction) req.getSession().getAttribute("check");
        user = (User) req.getSession().getAttribute("user");
        productsAndCurrentPrise = new LinkedHashMap<>();
        products = dbm.getAllProdOfCheck(check.getId());
        for (int i = 0; i < products.size(); i++) {
            productsAndCurrentPrise.put(
                    products.get(i),
                    dbm.getCurrentPrice(
                            check.getId(),
                            products.get(i).getId()
                    )
            );
        }
        closeCheck(req,check.getId(),dbm);
        Transaction check = (Transaction) req.getSession().getAttribute("check");
        check.setClosed(true);
    }

    private void closeCheck(HttpServletRequest req ,Integer checkId, DbCheckManager dbm) throws ServletException, IOException {
        LOG.debug("close check");
        req.setAttribute("checkId", check.getId());
        req.setAttribute("cashier", user.getId());
        req.setAttribute("total", dbm.getTotalSum(check.getId()));
        Date date = new Date();
        req.setAttribute("date", date);
        //set date fo closed check to db
        dbm.setDate(date, check.getId());
        Map<Product, List<Double>> productNumberCurrPrice = new LinkedHashMap<>();
        for (int i = 0; i < products.size(); i++) {
            productNumberCurrPrice.put(
                    products.get(i),
                    List.of(
                            dbm.getNumber(
                                    check.getId(),
                                    products.get(i).getId()),
                            dbm.getCurrentPrice(
                                    check.getId(),
                                    products.get(i).getId()
                            )
                    )
            );
        }
        req.setAttribute("products", productNumberCurrPrice);
        req.getRequestDispatcher("/views/checkPrint.jsp").forward(req, resp);
        dbm.closedCheck(checkId);
    }
}
