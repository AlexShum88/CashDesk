package my.project.services.checkServises;

import my.project.entity.Transaction;
import my.project.services.db.DbCheckManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class SetPriceByNumber {
    private static final Logger LOG = LogManager.getLogger(SetPriceByNumber.class);

    public void setPriceByNumber(HttpServletRequest req, DbCheckManager dbm) {
        //init
        LOG.debug("set price by number");
        Transaction transaction = (Transaction) req.getSession().getAttribute("check");
        LOG.debug(transaction);
        Integer productId = Integer.valueOf(req.getParameter("productId"));
        LOG.debug(req.getParameter("productId"));
        Double number = Double.parseDouble(req.getParameter("number"));
        LOG.debug("number = {}", req.getParameter("number"));
        //doing
        dbm.setProdNumber(number, transaction.getId(), productId);
        dbm.setCurrentPrice(transaction.getId(), productId);
    }
}
