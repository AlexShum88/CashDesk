package my.project.services.checkServises;

import my.project.entity.Transaction;
import my.project.services.db.DbCheckManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class AddSelectedProduct {
    private static final Logger LOG = LogManager.getLogger(AddSelectedProduct.class);

    public void addSelectedProduct(Transaction transaction, Integer prodId, DbCheckManager dbm) {
        LOG.debug("add selected product");
        LOG.debug( "transaction = {}",transaction);
        dbm.addProd(transaction.getId(), prodId);
    }
}
