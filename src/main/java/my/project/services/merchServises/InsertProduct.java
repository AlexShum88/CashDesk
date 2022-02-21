package my.project.services.merchServises;

import my.project.entity.Product;
import my.project.services.db.DbProductManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class InsertProduct {
    private  static final Logger LOG = LogManager.getLogger(InsertProduct.class);
    public void insertProduct(HttpServletRequest req, DbProductManager dbm){
        LOG.debug("in insert product");
        String prName = req.getParameter("newName");
        Double price =  0.0;
        Double number =  0.0;
        Product product = new Product(prName, price, number);
        dbm.insertProduct(product);
    }
}
