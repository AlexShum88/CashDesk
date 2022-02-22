package my.project.services.commands.check;

import my.project.services.checkServises.Exit;

import javax.servlet.http.HttpServletRequest;

public class ExitCommand implements CommandCheck{
    HttpServletRequest req;

    public ExitCommand(HttpServletRequest req) {
        this.req = req;
    }

    @Override
    public void execute() {
        new Exit().exit(req);
    }
}
