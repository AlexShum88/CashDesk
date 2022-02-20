package my.project.services.contollers;

import my.project.entity.Product;
import my.project.entity.Transaction;
import my.project.services.db.DbCheckManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SetTotalSum {
    private static final Logger LOG = LogManager.getLogger(SetTotalSum.class);
    List<Product> products;
    Map<Product, Double> productsAndCurrentPrise;
    Transaction check;
    public SetTotalSum(HttpServletRequest req, DbCheckManager dbm) {

        check = (Transaction) req.getSession().getAttribute("check");
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

        setTotalSumToDB(setTotalSum(productsAndCurrentPrise),dbm, check.getId());
    }



    private Double setTotalSum(Map<Product, Double> productDoubleMap) {
        return productDoubleMap.entrySet().stream()
                .map(Map.Entry::getValue)
                .reduce(Double::sum)
                .orElse(0d);
    }

    private void setTotalSumToDB(Double totalSum, DbCheckManager dbm, Integer checkId) {
        dbm.setTotalSum(totalSum, checkId);
    }
}
