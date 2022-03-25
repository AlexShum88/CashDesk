package my.project.services.commands.merch;

import my.project.db.DbProductManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class DeleteProductCommand implements CommandMerch{
    HttpServletRequest req;

    public DeleteProductCommand(HttpServletRequest req) {
        this.req = req;
    }
    private  static final Logger LOG = LogManager.getLogger(DeleteProductCommand.class);
    @Override
    public void execute() {
        Integer id = Integer.parseInt(req.getParameter("prodID"));
        LOG.debug("in delete product");
        DbProductManager dbm = getDbm(req);
        dbm.deleteProduct(id);
    }
}
