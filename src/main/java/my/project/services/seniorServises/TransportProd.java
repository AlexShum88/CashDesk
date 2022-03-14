package my.project.services.seniorServises;

import java.io.Serializable;
/**
 * objects for transport to jsp data about sales
 * */
public class TransportProd implements Serializable {
        String name;
        Double cash = 0d;
        Double num = 0d;

        public TransportProd(String name, Double totalCash, Double totalNum) {
            this.name = name;
            double scale = Math.pow(10, 2);
            this.cash = Math.ceil(totalCash * scale) / scale;
            this.num = Math.ceil(totalNum * scale) / scale;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Double getCash() {
            return cash;
        }

        public void setCash(Double cash) {
            this.cash = cash;
        }

        public Double getNum() {
            return num;
        }

        public void setNum(Double num) {
            this.num = num;
        }

        public void addCash(Double num){
            this.cash = this.cash +num;
        }
        public void addNum(Double num){
            this.num = this.num +num;
        }
    }
