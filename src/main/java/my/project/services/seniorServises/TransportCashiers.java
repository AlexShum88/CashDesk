package my.project.services.seniorServises;

import java.io.Serializable;
/**
 * object for transport to jsp data about cashiers work
 * */
public class TransportCashiers implements Serializable {
    int id;
    int checks;
    int isClosed;
    Double sum;

    public TransportCashiers(int id, int checks, int isClosed, Double sum) {
        this.id = id;
        this.checks = checks;
        this.isClosed = isClosed;
        this.sum = sum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getChecks() {
        return checks;
    }

    public void setChecks(int checks) {
        this.checks = checks;
    }

    public int getIsClosed() {
        return isClosed;
    }

    public void setIsClosed(int isClosed) {
        this.isClosed = isClosed;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }
}
