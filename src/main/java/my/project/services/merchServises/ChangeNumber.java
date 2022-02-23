package my.project.services.merchServises;

import my.project.services.db.DbProductManager;
import my.project.servlets.MerchandiserServlet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class ChangeNumber {
    private  static final Logger LOG = LogManager.getLogger(MerchandiserServlet.class);
    public void changeNumber(int id, Double number, DbProductManager dbm) {
        LOG.debug("in change price");
        dbm.changeProductNumber(id, number);
    }
}
