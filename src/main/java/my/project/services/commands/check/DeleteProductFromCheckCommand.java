package my.project.services.commands.check;

import my.project.services.checkServises.DeleteProductFromCheck;

import javax.servlet.http.HttpServletRequest;

public class DeleteProductFromCheckCommand implements CommandCheck {
    HttpServletRequest req;

    public DeleteProductFromCheckCommand(HttpServletRequest req) {
        this.req = req;
    }

    @Override
    public void execute() {
        new DeleteProductFromCheck().delete(req, getDbm(req));
    }
}
