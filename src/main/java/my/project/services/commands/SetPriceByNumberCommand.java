package my.project.services.commands;

import my.project.services.contollers.SetPriceByNumber;

import javax.servlet.http.HttpServletRequest;

public class SetPriceByNumberCommand implements Command{
    HttpServletRequest req;

    public SetPriceByNumberCommand(HttpServletRequest req) {
        this.req = req;
    }

    @Override
    public void execute() {
        new SetPriceByNumber().setPriceByNumber(req, getDbm());
    }
}
