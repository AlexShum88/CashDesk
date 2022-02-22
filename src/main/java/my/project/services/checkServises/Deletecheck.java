package my.project.services.checkServises;

import my.project.entity.Transaction;
import my.project.entity.User;
import my.project.services.db.DbCheckManager;

import javax.servlet.http.HttpServletRequest;

public class Deletecheck {
    public void delete(HttpServletRequest req, DbCheckManager dbm) {
        Transaction transaction=(Transaction)req.getSession().getAttribute("check");
        User user = (User) req.getSession().getAttribute("senior");
        dbm.deleteCheck(transaction.getId(), user.getId());
    }
}
