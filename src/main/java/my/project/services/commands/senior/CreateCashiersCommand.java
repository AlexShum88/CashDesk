package my.project.services.commands.senior;

import my.project.services.seniorServises.CreateCashiersReport;

import javax.servlet.http.HttpServletRequest;

public class CreateCashiersCommand implements SeniorCommand {
    HttpServletRequest req;

    public CreateCashiersCommand(HttpServletRequest req) {
        this.req = req;
    }

    @Override
    public void execute() {req.getSession().setAttribute("cashiers", new CreateCashiersReport().create(getDbm(req)));
    }
}
