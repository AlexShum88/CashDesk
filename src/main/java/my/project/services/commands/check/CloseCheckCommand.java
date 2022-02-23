package my.project.services.commands.check;

import my.project.entity.Product;
import my.project.entity.Transaction;
import my.project.entity.User;
import my.project.services.checkServises.CloseCheck;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CloseCheckCommand implements CommandCheck {
    HttpServletRequest req;
    HttpServletResponse resp;
    Transaction check;
    User user;
    List<Product> products;
    Map<Product, Double> productsAndCurrentPrise;
    Date date;
    public CloseCheckCommand(HttpServletRequest req, HttpServletResponse resp) {
        this.req = req;
        this.resp = resp;
        productsAndCurrentPrise = new LinkedHashMap<>();
        check = (Transaction) req.getSession().getAttribute("check");
        user = (User) req.getSession().getAttribute("user");
        date = new Date();
        products = getDbm(req).getAllProdOfCheck(check.getId());
        for (int i = 0; i < products.size(); i++) {
            productsAndCurrentPrise.put(
                    products.get(i),
                    getDbm(req).getCurrentPrice(
                            check.getId(),
                            products.get(i).getId()
                    )
            );
        }

    }

    @Override
    public void execute() {
        try {
            check.setClosed(true);
            var productNumberCurrPrice = new CloseCheck().closeCheck(getDbm(req), date, check, products);
            //set flags for jsp
            req.setAttribute("products", productNumberCurrPrice);
            req.setAttribute("checkId", check.getId());
            req.setAttribute("cashier", user.getId());
            req.setAttribute("total", getDbm(req).getTotalSum(check.getId()));
            req.setAttribute("date", date);
            //redirect to jsp
            req.getRequestDispatcher("/views/checkPrint.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }
}
