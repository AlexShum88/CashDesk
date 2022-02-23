package my.project.services.checkServises;

import my.project.entity.Transaction;
import my.project.entity.User;
import my.project.services.db.DbCheckManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class CreateCheck {
    private static final Logger LOG = LogManager.getLogger(CreateCheck.class);

    public void createCheck( User user, Transaction check, DbCheckManager dbm) {
        LOG.debug("create check");
        dbm.newCheck(user.getId());
        dbm.setDate(new Date(), check.getId());
    }
}
