package my.project.services.commands.check;

import my.project.services.checkServises.SetTotalSum;

import javax.servlet.http.HttpServletRequest;

public class SetTotalSumCommand implements CommandCheck {
    HttpServletRequest req;
    public SetTotalSumCommand(HttpServletRequest req) {
        this.req = req;
    }

    @Override
    public void execute() {
        new SetTotalSum(req, getDbm(req));
    }
}
