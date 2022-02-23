package my.project.services.merchServises;

import my.project.services.db.DbProductManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class DeleteProduct {
    private  static final Logger LOG = LogManager.getLogger(DeleteProduct.class);
    public void deleteProduct (int id, DbProductManager dbm) {
        LOG.debug("in delete product");

        dbm.deleteProduct(id);
    }
}
