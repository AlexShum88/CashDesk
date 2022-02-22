package my.project.services.checkServises;

import my.project.entity.Transaction;
import my.project.services.db.DbCheckManager;

import javax.servlet.http.HttpServletRequest;

public class DeleteProductFromCheck {
    public void delete(HttpServletRequest req, DbCheckManager dbm) {
        Transaction transaction=(Transaction)req.getSession().getAttribute("check");
        Integer productId = Integer.valueOf(req.getParameter("productId"));
        dbm.deleteProd(transaction.getId(), productId);
    }
}
