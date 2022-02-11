package my.project.entity;

import java.io.Serializable;

public class Product implements Serializable {
    private String name;
    private Double price;
    private boolean div;
    private Double count;

    public Product() {
    }

    public Product(String name, Double price, boolean div, Double count) {
        this.name = name;
        this.price = price;
        this.div = div;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public boolean isDiv() {
        return div;
    }

    public void setDiv(boolean div) {
        this.div = div;
    }

    public Double getCount() {
        return count;
    }

    public void setCount(Double count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        return name.equals(product.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
