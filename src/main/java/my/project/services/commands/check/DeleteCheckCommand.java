package my.project.services.commands.check;

import my.project.entity.Transaction;
import my.project.entity.User;
import my.project.services.checkServises.Deletecheck;
import my.project.services.commands.check.CommandCheck;

import javax.servlet.http.HttpServletRequest;

public class DeleteCheckCommand implements CommandCheck {
    HttpServletRequest req;

    public DeleteCheckCommand(HttpServletRequest req) {
        this.req = req;
    }

    @Override
    public void execute() {
        Transaction transaction=(Transaction)req.getSession().getAttribute("check");
        User user = (User) req.getSession().getAttribute("senior");
        new Deletecheck().delete(transaction, user, getDbm(req));
    }
}
