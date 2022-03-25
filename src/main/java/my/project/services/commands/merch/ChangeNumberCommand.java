package my.project.services.commands.merch;

import my.project.db.DbProductManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class ChangeNumberCommand implements CommandMerch {
    HttpServletRequest req;
    private  static final Logger LOG = LogManager.getLogger(ChangeNumberCommand.class);
    public ChangeNumberCommand(HttpServletRequest req) {
        this.req = req;
    }

    @Override
    public void execute() {

        Integer id = Integer.parseInt(req.getParameter("prodID"));
        Double number = Double.valueOf(req.getParameter("number"));
        LOG.debug("in change price");
        DbProductManager dbm = getDbm(req);
        dbm.changeProductNumber(id, number);
    }


}
