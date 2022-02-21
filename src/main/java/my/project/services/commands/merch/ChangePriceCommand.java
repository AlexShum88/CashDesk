package my.project.services.commands.merch;

import my.project.services.merchServises.ChangePrice;

import javax.servlet.http.HttpServletRequest;

public class ChangePriceCommand implements CommandMerch{
    HttpServletRequest req;

    public ChangePriceCommand(HttpServletRequest req) {
        this.req = req;
    }


    @Override
    public void execute() {
        new ChangePrice().changePrice(req, getDbm(req));
    }
}
