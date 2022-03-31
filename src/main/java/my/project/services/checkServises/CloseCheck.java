package my.project.services.checkServises;

import my.project.db.DbCheckManager;
import my.project.db.DbProductManager;
import my.project.model.Product;
import my.project.model.Transaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CloseCheck {
    private static final Logger LOG = LogManager.getLogger(CloseCheck.class);


    public Map<Product, List<Double>> closeCheck(DbCheckManager dbm, Date date, Transaction check, List<Product> products) {
        LOG.debug("close check");

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
        dbm.closedCheck(check.getId());
        minusProductFromDb(check.getId(), dbm);
        return productNumberCurrPrice;
    }

    private void minusProductFromDb(Integer checkId, DbCheckManager dbm) {
        DbProductManager productManager = DbProductManager.getInstance();
        List<Product> allProdOfCheck = dbm.getAllProdOfCheck(checkId);
        for (int i = 0; i < allProdOfCheck.size(); i++) {
            Integer prodId = allProdOfCheck.get(i).getId();
            double v = -dbm.getNumber(checkId, prodId);
            productManager.changeProductNumber(prodId, v);
        }

    }
}
