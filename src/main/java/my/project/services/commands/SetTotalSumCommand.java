package my.project.services.commands;

import my.project.services.contollers.SetTotalSum;

import javax.servlet.http.HttpServletRequest;

public class SetTotalSumCommand implements Command {
    HttpServletRequest req;
    public SetTotalSumCommand(HttpServletRequest req) {
        this.req = req;
    }

    @Override
    public void execute() {
        new SetTotalSum(req, getDbm());
    }
}
