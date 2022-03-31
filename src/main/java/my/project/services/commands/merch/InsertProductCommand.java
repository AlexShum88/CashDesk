package my.project.services.commands.merch;

import my.project.db.DbProductManager;
import my.project.model.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class InsertProductCommand implements CommandMerch {
    HttpServletRequest req;
    private static final Logger LOG = LogManager.getLogger(InsertProductCommand.class);

    public InsertProductCommand(HttpServletRequest req) {
        this.req = req;
    }

    @Override
    public void execute() {
        String prName = req.getParameter("newName");
        LOG.debug("in insert product");
        Double price = 0.0;
        Double number = 0.0;
        Product product = new Product(prName, price, number);
        DbProductManager dbm = getDbm(req);
        dbm.insertProduct(product);

    }
}
