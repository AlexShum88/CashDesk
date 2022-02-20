package my.project.services.commands;

import my.project.services.checkServises.CreateCheck;

import javax.servlet.http.HttpServletRequest;

public class CreateCheckCommand implements Command{
    HttpServletRequest req;
    public CreateCheckCommand(HttpServletRequest req) {
        this.req = req;
    }

    @Override
    public void execute() {
        new CreateCheck().createCheck(req, getDbm(req));

    }
}