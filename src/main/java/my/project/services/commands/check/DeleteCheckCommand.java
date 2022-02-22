package my.project.services.commands.check;

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
        new Deletecheck().delete(req, getDbm(req));
    }
}
