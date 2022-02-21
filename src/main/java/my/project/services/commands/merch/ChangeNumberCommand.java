package my.project.services.commands.merch;

import my.project.services.merchServises.ChangeNumber;

import javax.servlet.http.HttpServletRequest;

public class ChangeNumberCommand implements CommandMerch {
    HttpServletRequest req;

    public ChangeNumberCommand(HttpServletRequest req) {
        this.req = req;
    }

    @Override
    public void execute() {
        new  ChangeNumber().changeNumber(req, getDbm(req));
    }


}
