package my.project.services.db;

import jdk.jshell.spi.SPIResolutionException;
import my.project.entity.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DbProductManager extends DbSuperManager{

    private static final DbProductManager instance = new DbProductManager();

    public static synchronized DbProductManager getInstance() {
        System.out.println("get db product manager instance");
        return instance;
    }


    private DbProductManager() {
    }



    public List<Product> getAllProducts (){
        List<Product>products = new ArrayList<>();
        try (Connection conn = getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "select * from products;");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String prName = resultSet.getString("name");
                Double price = resultSet.getDouble("price");
                int count = resultSet.getInt("count");
                boolean div = resultSet.getBoolean("div");
                Product product = new Product(prName, price, count, div);
                products.add(product);
            }

        } catch (DBException | SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public Product getProduct(String prodName){
        Product product = null;
        try (Connection conn = getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "select * from product where name = ?;"
            );
            preparedStatement.setString(1, prodName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                String prName = resultSet.getString("name");
                Double price = resultSet.getDouble("price");
                int count = resultSet.getInt("count");
                boolean div = resultSet.getBoolean("div");
                product = new Product(prName, price, count, div);
            }
        } catch (DBException | SQLException e) {
            e.printStackTrace();
        }

        return product;
    }

    public boolean insertProduct(Product product){
        try (Connection conn = getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "insert into products (name, price, count, dev) values ( ?, ?, ?, ? )"
            );
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setInt(3, product.getCount());
            preparedStatement.setBoolean(4, product.isDiv());

            if (preparedStatement.executeUpdate()>0){
                return true;
            }

        } catch (DBException | SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteProduct(String prodName){
        try (Connection conn = getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "delete from products where name = ?"
            );
            preparedStatement.setString(1, prodName);
            if (preparedStatement.executeUpdate()>0){
                return true;
            }
        } catch (DBException | SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean changeProductNumber(String prodName, int num){
        try (Connection conn = getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "update products set count = count + ? where name = ? ;"
            );
            preparedStatement.setInt(1, num);
            preparedStatement.setString(2, prodName);
            if (preparedStatement.executeUpdate()>0){
                return true;
            }
        } catch (DBException | SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean changeProductPrice (String prodName, Double num) {
        try (Connection conn = getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "update products set price = price + ? where name = ? ;"
            );
            preparedStatement.setDouble(1, num);
            preparedStatement.setString(2, prodName);
            if (preparedStatement.executeUpdate()>0){
                return true;
            }
        } catch (DBException | SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

}
