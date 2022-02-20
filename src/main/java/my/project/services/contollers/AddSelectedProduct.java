package my.project.services.contollers;

import my.project.entity.Transaction;
import my.project.services.db.DbCheckManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class AddSelectedProduct {
    private static final Logger LOG = LogManager.getLogger(AddSelectedProduct.class);

    public void addSelectedProduct(HttpServletRequest req, DbCheckManager dbm) {
        LOG.debug("add selected product");
        Transaction transaction = (Transaction) req.getSession().getAttribute("check");
        LOG.debug( "transaction = {}",transaction);

        req.getParameterMap().keySet().forEach(System.out::println);

        var prodId = req.getParameter("product").split(" ")[0];
        Integer id = Integer.valueOf(prodId);
        LOG.debug("product = {}",req.getParameter("product"));
        dbm.addProd(transaction.getId(), id);
    }
}
