package my.project.services.commands.senior;

import my.project.services.seniorServises.CreateGoodsReport;

import javax.servlet.http.HttpServletRequest;

public class CreateGoodsReportCommand implements SeniorCommand{
    HttpServletRequest req;

    public CreateGoodsReportCommand(HttpServletRequest req) {
        this.req = req;
    }

    @Override
    public void execute() {
        req.getSession().setAttribute("allProd", new CreateGoodsReport().create(getDbm(req)));

    }
}
