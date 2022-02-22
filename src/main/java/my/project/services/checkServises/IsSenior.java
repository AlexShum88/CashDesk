package my.project.services.checkServises;

import my.project.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static my.project.entity.Roles.SENIOR_CASHIER;

public class IsSenior {

    private static final Logger LOG = LogManager.getLogger(IsSenior.class);

    public void isSenior(HttpServletRequest req) {
        LOG.debug("redact param {}", req.getParameter("redact"));

        req.getSession().setAttribute("ready", "redact");

        User user = (User) req.getSession().getAttribute("user");
        User senior = (User) req.getSession().getAttribute("senior");
        if (senior == null) {
            senior = user;
        }

        req.getSession().setAttribute("loginSenior", "redact");
        if (SENIOR_CASHIER.name.equals(senior.getRole())) {
            req.getSession().setAttribute("redact", "redact");
            req.getSession().setAttribute("loginSenior", null);
        }
    }
}
