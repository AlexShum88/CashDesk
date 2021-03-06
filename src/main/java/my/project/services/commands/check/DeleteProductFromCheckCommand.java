package my.project.services.commands.check;

import my.project.db.DbCheckManager;
import my.project.model.Transaction;

import javax.servlet.http.HttpServletRequest;

public class DeleteProductFromCheckCommand implements CommandCheck {
    HttpServletRequest req;

    public DeleteProductFromCheckCommand(HttpServletRequest req) {
        this.req = req;
    }

    @Override
    public void execute() {
        Transaction transaction = (Transaction) req.getSession().getAttribute("check");
        Integer productId = Integer.valueOf(req.getParameter("productId"));
        DbCheckManager dbm = getDbm(req);
        dbm.deleteProd(transaction.getId(), productId);
    }
}
