package my.project.services.checkServises;

import my.project.entity.Transaction;
import my.project.entity.User;
import my.project.services.db.DbCheckManager;

import javax.servlet.http.HttpServletRequest;

public class Deletecheck {
    public void delete(Transaction transaction, User user, DbCheckManager dbm) {
        dbm.deleteCheck(transaction.getId(), user.getId());
    }
}
