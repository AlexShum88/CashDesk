package my.project.services.commands;

import my.project.services.contollers.CloseCheck;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CloseCheckCommand implements Command {
    HttpServletRequest req;
    HttpServletResponse resp;
    public CloseCheckCommand(HttpServletRequest req, HttpServletResponse resp) {
        this.req = req;
        this.resp = resp;
    }

    @Override
    public void execute() {
        try {
            new CloseCheck(req, resp, getDbm());
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }
}
