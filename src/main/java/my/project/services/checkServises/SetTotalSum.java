package my.project.services.checkServises;

import my.project.db.DbCheckManager;
import my.project.model.Product;
import my.project.model.Transaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SetTotalSum {
    private static final Logger LOG = LogManager.getLogger(SetTotalSum.class);
    List<Product> products;
    Map<Product, Double> productsAndCurrentPrise;

    public SetTotalSum(Transaction check, DbCheckManager dbm) {
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

        setTotalSumToDB(setTotalSum(productsAndCurrentPrise), dbm, check.getId());
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
