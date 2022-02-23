package my.project.services.commands.check;

import my.project.entity.Transaction;
import my.project.services.checkServises.AddSelectedProduct;

import javax.servlet.http.HttpServletRequest;

public class AddSelectedProductCommand implements CommandCheck {
    HttpServletRequest req;
    public AddSelectedProductCommand(HttpServletRequest req) {
        this.req = req;
    }

    @Override
    public void execute() {
        var prodId = req.getParameter("product").split(" ")[0];
        Integer id = Integer.valueOf(prodId);
        Transaction transaction = (Transaction) req.getSession().getAttribute("check");
        new AddSelectedProduct().addSelectedProduct(transaction, id, getDbm(req));
    }
}
