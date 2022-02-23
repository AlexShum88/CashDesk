package my.project.services.commands.check;

import my.project.entity.Transaction;
import my.project.entity.User;
import my.project.services.checkServises.CreateCheck;

import javax.servlet.http.HttpServletRequest;

public class CreateCheckCommand implements CommandCheck {
    HttpServletRequest req;
    public CreateCheckCommand(HttpServletRequest req) {
        this.req = req;
    }

    @Override
    public void execute() {
        //initialised
        User user = (User) req.getSession().getAttribute("user");
        Transaction transaction = getDbm(req).getCheck(user.getId());
        //do
        new CreateCheck().createCheck( user, transaction, getDbm(req));
        req.getSession().setAttribute("check", getDbm(req).getCheck(user.getId()));

    }
}
