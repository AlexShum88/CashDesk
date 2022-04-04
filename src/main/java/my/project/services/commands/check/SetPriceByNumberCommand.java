package my.project.services.commands.check;

import my.project.db.DbProductManager;
import my.project.model.Transaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class SetPriceByNumberCommand implements CommandCheck {
    private static final Logger LOG = LogManager.getLogger(SetPriceByNumberCommand.class);
    HttpServletRequest req;

    public SetPriceByNumberCommand(HttpServletRequest req) {
        this.req = req;
    }

    @Override
    public void execute() {
        Transaction transaction = (Transaction) req.getSession().getAttribute("check");
        Integer productId = Integer.valueOf(req.getParameter("productId"));
        Double number = Double.parseDouble(req.getParameter("number"));
        DbProductManager productManager = DbProductManager.getInstance();
        var dbm = getDbm(req);
        if ((productManager.getNumber(productId) - dbm.getNumber(transaction.getId(), productId) - number < 0) || !dbm.setProdNumber(number, transaction.getId(), productId)) {
            req.getSession().setAttribute("cant", productId);
            LOG.debug("can`t set number");

        } else {
            dbm.setCurrentPrice(transaction.getId(), productId);
            req.getSession().setAttribute("cant", null);
        }
    }
}
