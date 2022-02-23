package my.project.services.merchServises;

import my.project.services.db.DbProductManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class ChangePrice {
    private  static final Logger LOG = LogManager.getLogger(ChangePrice.class);

    public void changePrice(int id, Double price, DbProductManager dbm) {
        LOG.debug("in change price");
        dbm.changeProductPrice(id, price);
    }
}
