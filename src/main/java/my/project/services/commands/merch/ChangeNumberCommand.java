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
        Integer id = Integer.parseInt(req.getParameter("prodID"));
        Double number = Double.valueOf(req.getParameter("number"));
        new  ChangeNumber().changeNumber(id, number, getDbm(req));
    }


}
