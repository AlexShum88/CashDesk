package my.project.services.commands.check;

import my.project.services.checkServises.CloseCheck;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CloseCheckCommand implements CommandCheck {
    HttpServletRequest req;
    HttpServletResponse resp;
    public CloseCheckCommand(HttpServletRequest req, HttpServletResponse resp) {
        this.req = req;
        this.resp = resp;
    }

    @Override
    public void execute() {
        try {
            new CloseCheck(req, resp, getDbm(req));
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }
}
