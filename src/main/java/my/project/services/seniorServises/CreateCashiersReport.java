package my.project.services.seniorServises;

import my.project.entity.Transaction;
import my.project.services.db.DbCheckManager;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CreateCashiersReport {
    public void create(HttpServletRequest req, DbCheckManager dbm) {

        List<Transaction> allChecks = dbm.getAllChecks();
        var coll = allChecks.stream()
                .collect(Collectors.groupingBy(Transaction::getAutorId));

        var id = coll.keySet().stream()
                .toList();
        var totalChecks = coll.values().stream()
                .map(List::size).toList();
        var isClosed = coll.values().stream()
                .map(e->e.stream()
                        .filter(Transaction::isClosed)
                        .toList()

                ).map(List::size)
                .toList();
        var sum = coll.values().stream()
                .map(e->e.stream()
                        .map(Transaction::getTotal)
                        .reduce(Double::sum).get()
                ).toList();
        List<TransportCashiers> transportCashiers = new ArrayList<>();
        for (int i = 0; i < id.size(); i++) {
            transportCashiers.add(new TransportCashiers(id.get(i), totalChecks.get(i), isClosed.get(i),sum.get(i) ));
        }
        req.getSession().setAttribute("cashiers", transportCashiers);
    }
}