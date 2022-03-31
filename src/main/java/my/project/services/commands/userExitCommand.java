package my.project.services.commands;

import javax.servlet.http.HttpServletRequest;

public class userExitCommand implements Command {
    HttpServletRequest req;

    public userExitCommand(HttpServletRequest req) {
        this.req = req;
    }

    @Override
    public void execute() {
        req.getSession().setAttribute("user", null);
    }
}
