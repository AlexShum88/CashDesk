package my.project.services.commands.check;

import my.project.entity.Transaction;
import my.project.entity.User;
import my.project.services.db.DbCheckManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class CreateCheckCommand implements CommandCheck {
    HttpServletRequest req;
    public CreateCheckCommand(HttpServletRequest req) {
        this.req = req;
    }
    private static final Logger LOG = LogManager.getLogger(CreateCheckCommand.class);
    @Override
    public void execute() {
        //initialised
        User user = (User) req.getSession().getAttribute("user");
        Transaction transaction = getDbm(req).getCheck(user.getId());
        DbCheckManager dbm = getDbm(req);
        //do
        req.getSession().setAttribute("check", getDbm(req).getCheck(user.getId()));
        LOG.debug("create check");

        dbm.newCheck(user.getId());
        dbm.setDate(new Date(), transaction.getId());

    }
}
