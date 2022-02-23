package my.project.services.commands.check;

import my.project.entity.Transaction;
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

        var dbm =getDbm(req);
        if(!dbm.setProdNumber(
                number+dbm.getNumber(transaction.getId(), productId),
                transaction.getId(),
                productId
        )
        ){
            req.getSession().setAttribute("cant", productId);
            LOG.debug("can`t set number");

        }else {
            dbm.setCurrentPrice(transaction.getId(), productId);
        }
    }
}
