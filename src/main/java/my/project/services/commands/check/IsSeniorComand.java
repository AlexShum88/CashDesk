package my.project.services.commands.check;

import my.project.services.checkServises.IsSenior;

import javax.servlet.http.HttpServletRequest;

public class IsSeniorComand implements CommandCheck {
    HttpServletRequest req;

    public IsSeniorComand(HttpServletRequest req) {
        this.req = req;
    }

    @Override
    public void execute() {
        new IsSenior().isSenior(req);
    }
}
