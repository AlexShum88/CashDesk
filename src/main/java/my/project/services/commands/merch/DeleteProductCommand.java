package my.project.services.commands.merch;

import my.project.services.merchServises.DeleteProduct;

import javax.servlet.http.HttpServletRequest;

public class DeleteProductCommand implements CommandMerch{
    HttpServletRequest req;

    public DeleteProductCommand(HttpServletRequest req) {
        this.req = req;
    }

    @Override
    public void execute() {
        new DeleteProduct().deleteProduct(req, getDbm(req));
    }
}
