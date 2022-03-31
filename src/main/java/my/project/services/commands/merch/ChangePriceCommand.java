package my.project.services.commands.merch;

import my.project.db.DbProductManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class ChangePriceCommand implements CommandMerch {
    HttpServletRequest req;
    private static final Logger LOG = LogManager.getLogger(ChangePriceCommand.class);

    public ChangePriceCommand(HttpServletRequest req) {
        this.req = req;
    }

    @Override
    public void execute() {
        int id = Integer.parseInt(req.getParameter("prodID"));
        Double price = Double.valueOf(req.getParameter("price"));
        LOG.debug("in change price");
        DbProductManager dbm = getDbm(req);
        dbm.changeProductPrice(id, price);
    }
}
