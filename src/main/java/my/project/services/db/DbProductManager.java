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

    private static final String SQL_GET_ALL_PRODUCTS = "select * from products;";
    private static final String SQL_GET_PRODUCT = "select * from products where id = ?;";
    private static final String SQL_GET_PRODUCT_BY_ID = "select * from products where id = ?;";
    private static final String SQL_INSERT_PRODUCT = "insert into products (name, price, number) values ( ?, ?, ?)";
    private static final String SQL_DELETE_PRODUCT = "update products set is_deleted = true where id = ?";
    private static final String SQL_CHANGE_PROD_NUMBER = "update products set number = number + ? where id = ? ;";
    private static final String SQL_CHANGE_PROD_PRICE = "update products set price = ? where id = ? ;";


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
            PreparedStatement preparedStatement = conn.prepareStatement(SQL_GET_ALL_PRODUCTS);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String prName = resultSet.getString("name");
                Double price = resultSet.getDouble("price");
                Double number = resultSet.getDouble("number");
                Integer id = resultSet.getInt("id");
                Product product = new Product(prName, price, number, id);
                products.add(product);
            }

        } catch (DBException | SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public Product getProductById(Integer id){
        Product product = null;
        try (Connection conn = getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(SQL_GET_PRODUCT);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                String prName = resultSet.getString("name");
                Double price = resultSet.getDouble("price");
                Double number = resultSet.getDouble("number");
                Integer id2 = resultSet.getInt("id");
                product = new Product(prName, price, number, id2);
            }
        } catch (DBException | SQLException e) {
            e.printStackTrace();
        }

        return product;
    }

    public boolean insertProduct(Product product){
        try (Connection conn = getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(SQL_INSERT_PRODUCT);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setDouble(3, product.getNumber());


            if (preparedStatement.executeUpdate()>0){
                return true;
            }

        } catch (DBException | SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteProduct(Integer id){
        try (Connection conn = getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(SQL_DELETE_PRODUCT);
            preparedStatement.setInt(1, id);
            if (preparedStatement.executeUpdate()>0){
                return true;
            }
        } catch (DBException | SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean changeProductNumber(Integer id, Double num){
        try (Connection conn = getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(SQL_CHANGE_PROD_NUMBER);
            preparedStatement.setDouble(1, num);
            preparedStatement.setInt(2, id);
            if (preparedStatement.executeUpdate()>0){
                return true;
            }
        } catch (DBException | SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean changeProductPrice (Integer id, Double num) {
        try (Connection conn = getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(SQL_CHANGE_PROD_PRICE);
            preparedStatement.setDouble(1, num);
            preparedStatement.setInt(2, id);
            if (preparedStatement.executeUpdate()>0){
                return true;
            }
        } catch (DBException | SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public Product getProductById(int id) {
        Product product = null;
        try (Connection conn = getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(SQL_GET_PRODUCT_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                String prName = resultSet.getString("name");
                Double price = resultSet.getDouble("price");
                Double number = resultSet.getDouble("number");
                Integer idp = resultSet.getInt("id");
                product = new Product(prName, price, number, idp);
            }
        } catch (DBException | SQLException e) {
            e.printStackTrace();
        }
        return product;
    }
}
