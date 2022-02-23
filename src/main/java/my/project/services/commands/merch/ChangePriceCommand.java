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
        int id = Integer.parseInt(req.getParameter("prodID"));
        Double price = Double.valueOf(req.getParameter("price"));
        new ChangePrice().changePrice(id, price, getDbm(req));
    }
}
