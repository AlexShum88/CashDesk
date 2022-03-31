package my.project.services.commands.check;

import javax.servlet.http.HttpServletRequest;

public class ExitCommand implements CommandCheck {
    HttpServletRequest req;

    public ExitCommand(HttpServletRequest req) {
        this.req = req;
    }

    @Override
    public void execute() {
        req.getSession().setAttribute("ready", null);
        req.getSession().setAttribute("redact", null);
        req.getSession().setAttribute("loginSenior", null);
        req.getSession().setAttribute("senior", null);

    }
}
