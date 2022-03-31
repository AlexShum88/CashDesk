package my.project.services.seniorServises;

import my.project.db.DbCheckManager;
import my.project.model.Product;
import my.project.model.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * create objects for jsp
 * for create report about sales
 */
public class CreateGoodsReport {
    public List<TransportProd> create(DbCheckManager dbm) {
        List<Transaction> allChecks = dbm.getAllChecks();
        for (int i = 0; i < allChecks.size(); i++) {
            allChecks.get(i).setList(dbm.getAllProdOfCheck(allChecks.get(i).getId()));
        }
        List<Product> allProd = new ArrayList<>();
        allChecks = allChecks.stream().filter(Transaction::isClosed).toList();
        for (int i = 0; i < allChecks.size(); i++) {
            List<Product> products = allChecks.get(i).getList();
            for (int j = 0; j < products.size(); j++) {
                products.get(j).setNumber(dbm.getNumber(allChecks.get(i).getId(), products.get(j).getId()));
                products.get(j).setPrice(dbm.getCurrentPrice(allChecks.get(i).getId(), products.get(j).getId()));
                allProd.add(products.get(j));
            }

        }

        Map<String, List<Product>> allProd2 = allProd.stream()
                .collect(Collectors.groupingBy(Product::getName));

        var cash = allProd2.values().stream()
                .map(r -> r.stream()
                        .map(Product::getPrice)
                        .reduce(Double::sum))
                .map(e -> e.get())
                .toList();

        var num = allProd2.values().stream()
                .map(r -> r.stream()
                        .map(Product::getNumber)
                        .reduce(Double::sum))
                .map(e -> e.get())
                .toList();

        var key = allProd2.keySet().stream()
                .toList();

        List<TransportProd> transport = new ArrayList<>();
        for (int i = 0; i < key.size(); i++) {
            transport.add(new TransportProd(key.get(i), cash.get(i), num.get(i)));
        }

        return transport;

    }
}


