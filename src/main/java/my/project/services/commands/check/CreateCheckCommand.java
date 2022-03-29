package my.project.services.commands.check;

import my.project.db.DbCheckManager;
import my.project.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class CreateCheckCommand implements CommandCheck {
    HttpServletRequest req;
    private static final Logger LOG = LogManager.getLogger(CreateCheckCommand.class);
    public CreateCheckCommand(HttpServletRequest req) {
        this.req = req;
    }

    @Override
    public void execute() {
        //initialised
        User user = (User) req.getSession().getAttribute("user");
        DbCheckManager dbm = getDbm(req);
        //do
        req.getSession().setAttribute("check", getDbm(req).getCheck(user.getId()));
        LOG.debug("create check");

        dbm.newCheck(user.getId());
        dbm.setDate(new Date(), getDbm(req).getCheck(user.getId()).getId());

    }
}
