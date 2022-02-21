package my.project.services.commands.check;

import my.project.services.checkServises.SetPriceByNumber;

import javax.servlet.http.HttpServletRequest;

public class SetPriceByNumberCommand implements CommandCheck {
    HttpServletRequest req;

    public SetPriceByNumberCommand(HttpServletRequest req) {
        this.req = req;
    }

    @Override
    public void execute() {
        new SetPriceByNumber().setPriceByNumber(req, getDbm(req));
    }
}
