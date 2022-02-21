package my.project.entity;

import java.io.Serializable;

public class Product implements Serializable {
    private String name;
    private Double price;
    private Double number;
    private Integer id;
    private Boolean isDeleted = false;




    public Product() {
    }

    public Product(String name, Double price, Double number) {
        this.name = name;
        this.price = price;
        this.number = number;
    }

    public Product(String name, Double price, Double number, Integer id, Boolean isDeleted) {
        this.name = name;
        this.price = price;
        this.number = number;
        this.id = id;
        this.isDeleted = isDeleted;
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

    public Double getNumber() {
        return number;
    }

    public void setNumber(Double number) {
        this.number = number;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
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
