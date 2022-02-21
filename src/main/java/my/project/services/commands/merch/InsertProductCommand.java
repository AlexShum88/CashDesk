package my.project.services.commands.merch;

import my.project.services.merchServises.InsertProduct;

import javax.servlet.http.HttpServletRequest;

public class InsertProductCommand implements CommandMerch{
    HttpServletRequest req;

    public InsertProductCommand(HttpServletRequest req) {
        this.req = req;
    }

    @Override
    public void execute() {
        new InsertProduct().insertProduct(req, getDbm(req));
    }
}
