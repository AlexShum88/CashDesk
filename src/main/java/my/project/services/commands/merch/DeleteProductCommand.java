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
        Integer id = Integer.parseInt(req.getParameter("prodID"));
        new DeleteProduct().deleteProduct(id, getDbm(req));
    }
}
