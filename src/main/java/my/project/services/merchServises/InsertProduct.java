package my.project.services.merchServises;

import my.project.entity.Product;
import my.project.services.db.DbProductManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class InsertProduct {
    private  static final Logger LOG = LogManager.getLogger(InsertProduct.class);
    public void insertProduct(String prName, DbProductManager dbm){
        LOG.debug("in insert product");

        Double price =  0.0;
        Double number =  0.0;
        Product product = new Product(prName, price, number);
        dbm.insertProduct(product);
    }
}
