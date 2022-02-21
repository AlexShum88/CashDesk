package my.project.services.seniorServises;

public class TransportProd{
        String name;
        Double cash = 0d;
        Double num = 0d;

        public TransportProd(String name, Double totalCash, Double totalNum) {
            this.name = name;
            this.cash = totalCash;
            this.num = totalNum;
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
