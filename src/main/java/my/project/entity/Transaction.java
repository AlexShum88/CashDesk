package my.project.entity;

import java.io.Serializable;
import java.util.List;

public class Transaction implements Serializable {
    private int id;
    private Double total;
    private List<Product> list;
    private int autorId;
    private boolean isCanceled;

    public Transaction() {
    }

    public Transaction(int id, Double total, List<Product> list, int autorId, boolean isCanceled) {
        this.id = id;
        this.total = total;
        this.list = list;
        this.autorId = autorId;
        this.isCanceled = isCanceled;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public List<Product> getList() {
        return list;
    }

    public void setList(List<Product> list) {
        this.list = list;
    }

    public int getAutorId() {
        return autorId;
    }

    public void setAutorId(int autorId) {
        this.autorId = autorId;
    }

    public boolean isCanceled() {
        return isCanceled;
    }

    public void setCanceled(boolean canceled) {
        isCanceled = canceled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transaction that = (Transaction) o;

        if (id != that.id) return false;
        if (autorId != that.autorId) return false;
        return isCanceled == that.isCanceled;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + autorId;
        result = 31 * result + (isCanceled ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", total=" + total +
                ", list=" + list +
                ", autorId=" + autorId +
                ", isCanceled=" + isCanceled +
                '}';
    }
}
