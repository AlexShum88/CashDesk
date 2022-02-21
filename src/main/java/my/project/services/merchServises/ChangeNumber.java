package my.project.services.merchServises;

import my.project.services.db.DbProductManager;
import my.project.servlets.MerchandiserServlet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class ChangeNumber {
    private  static final Logger LOG = LogManager.getLogger(MerchandiserServlet.class);
    public void changeNumber(HttpServletRequest req, DbProductManager dbm) {
        LOG.debug("in change price");
        Integer id = Integer.parseInt(req.getParameter("prodID"));
        Double number = Double.valueOf(req.getParameter("number"));
        dbm.changeProductNumber(id, number);

    }
}
