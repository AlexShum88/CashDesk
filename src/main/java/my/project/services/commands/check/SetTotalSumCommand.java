package my.project.services.commands.check;

import my.project.model.Transaction;
import my.project.services.checkServises.SetTotalSum;

import javax.servlet.http.HttpServletRequest;

public class SetTotalSumCommand implements CommandCheck {
    HttpServletRequest req;
    Transaction check;
    public SetTotalSumCommand(HttpServletRequest req) {
        this.req = req;
        check = (Transaction) req.getSession().getAttribute("check");
    }

    @Override
    public void execute() {
        new SetTotalSum(check, getDbm(req));
    }
}
