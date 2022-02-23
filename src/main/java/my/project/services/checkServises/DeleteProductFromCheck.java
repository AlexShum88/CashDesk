package my.project.services.checkServises;

import my.project.entity.Transaction;
import my.project.services.db.DbCheckManager;

import javax.servlet.http.HttpServletRequest;

public class DeleteProductFromCheck {
    public void delete(Transaction transaction, int prodId, DbCheckManager dbm) {

        dbm.deleteProd(transaction.getId(), prodId);
    }
}
