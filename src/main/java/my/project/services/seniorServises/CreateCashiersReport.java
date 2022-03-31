package my.project.services.seniorServises;

import my.project.db.DbCheckManager;
import my.project.model.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * create objects for jsp
 * for create report about cashiers work
 */
public class CreateCashiersReport {
    public List<TransportCashiers> create(DbCheckManager dbm) {

        List<Transaction> allChecks = dbm.getAllChecks();
        var coll = allChecks.stream()
                .collect(Collectors.groupingBy(Transaction::getAutorId));

        var id = coll.keySet().stream()
                .toList();
        var totalChecks = coll.values().stream()
                .map(List::size).toList();
        var isClosed = coll.values().stream()
                .map(e -> e.stream()
                        .filter(Transaction::isClosed)
                        .toList()

                ).map(List::size)
                .toList();
        var sum = coll.values().stream()
                .map(e -> e.stream()
                        .map(Transaction::getTotal)
                        .reduce(Double::sum).get()
                ).toList();
        List<TransportCashiers> transportCashiers = new ArrayList<>();
        for (int i = 0; i < id.size(); i++) {
            transportCashiers.add(new TransportCashiers(id.get(i), totalChecks.get(i), isClosed.get(i), sum.get(i)));
        }
        return transportCashiers;
    }
}
