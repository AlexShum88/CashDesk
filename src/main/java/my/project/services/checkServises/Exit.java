package my.project.services.checkServises;

import javax.servlet.http.HttpServletRequest;

public class Exit {

    public void exit(HttpServletRequest req) {
        req.getSession().setAttribute("ready", null);
        req.getSession().setAttribute("redact", null);
        req.getSession().setAttribute("loginSenior", null);
        req.getSession().setAttribute("senior", null);
    }
}
