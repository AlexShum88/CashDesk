package my.project.services.commands.check;

import my.project.services.checkServises.CreateCheck;

import javax.servlet.http.HttpServletRequest;

public class CreateCheckCommand implements CommandCheck {
    HttpServletRequest req;
    public CreateCheckCommand(HttpServletRequest req) {
        this.req = req;
    }

    @Override
    public void execute() {
        new CreateCheck().createCheck(req, getDbm(req));

    }
}
