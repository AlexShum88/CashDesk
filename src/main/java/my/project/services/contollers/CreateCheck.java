package my.project.services.contollers;

import my.project.entity.Transaction;
import my.project.entity.User;
import my.project.services.db.DbCheckManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class CreateCheck {
    private static final Logger LOG = LogManager.getLogger(CreateCheck.class);

    public void createCheck(HttpServletRequest req, DbCheckManager dbm) {
        LOG.debug("create check");
        User user = (User) req.getSession().getAttribute("user");
        dbm.newCheck(user.getId());
        Transaction transaction = dbm.getCheck(user.getId());
        dbm.setDate(new Date(), transaction.getId() );
        req.getSession().setAttribute("check", dbm.getCheck(user.getId()));
        LOG.debug("{} === {}", dbm.getCheckId(user.getId()), req.getSession().getAttribute("check"));
    }
}
