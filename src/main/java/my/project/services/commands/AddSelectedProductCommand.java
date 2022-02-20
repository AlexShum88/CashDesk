package my.project.services.commands;

import my.project.services.checkServises.AddSelectedProduct;

import javax.servlet.http.HttpServletRequest;

public class AddSelectedProductCommand implements Command{
    HttpServletRequest req;
    public AddSelectedProductCommand(HttpServletRequest req) {
        this.req = req;
    }

    @Override
    public void execute() {
        new AddSelectedProduct().addSelectedProduct(req, getDbm(req));
    }
}
