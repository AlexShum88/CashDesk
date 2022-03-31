package my.project.services.commands.check;

import my.project.db.DbCheckManager;
import my.project.model.Transaction;
import my.project.model.User;

import javax.servlet.http.HttpServletRequest;

public class DeleteCheckCommand implements CommandCheck {
    HttpServletRequest req;

    public DeleteCheckCommand(HttpServletRequest req) {
        this.req = req;
    }

    @Override
    public void execute() {
        Transaction transaction = (Transaction) req.getSession().getAttribute("check");
        User user = (User) req.getSession().getAttribute("senior");
        DbCheckManager dbm = getDbm(req);
        dbm.deleteCheck(transaction.getId(), user.getId());
    }
}
