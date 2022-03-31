package my.project.services.commands.check;

import my.project.db.DbCheckManager;
import my.project.model.Transaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class AddSelectedProductCommand implements CommandCheck {
    HttpServletRequest req;
    private static final Logger LOG = LogManager.getLogger(AddSelectedProductCommand.class);

    public AddSelectedProductCommand(HttpServletRequest req) {
        this.req = req;
    }

    @Override
    public void execute() {
        LOG.debug("add selected product");
        var prodId = req.getParameter("product");
        if (prodId.isBlank() || req.getParameter("product").isEmpty()) return;
        prodId = prodId.split(" ")[0];
        Integer id = Integer.valueOf(prodId);
        Transaction transaction = (Transaction) req.getSession().getAttribute("check");
        LOG.debug("transaction = {}", transaction);
        DbCheckManager dbm = getDbm(req);
        dbm.addProd(transaction.getId(), id);
    }
}
