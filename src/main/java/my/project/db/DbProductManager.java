package my.project.db;

import my.project.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 * class for interact with db in case of products
 * */
public class DbProductManager extends DbSuperManager{
    /**
     * section for db commands
     * */
    public static final String SQL_GET_ALL_PRODUCTS = "select * from products;";
    public static final String SQL_GET_PRODUCT_BY_ID = "select * from products where id = ?;";
    public static final String SQL_INSERT_PRODUCT = "insert into products (name, price, number) values ( ?, ?, ?)";
    public static final String SQL_DELETE_PRODUCT = "update products set is_deleted = true where id = ?";
    public static final String SQL_CHANGE_PROD_NUMBER = "update products set number = number + ? where id = ? ;";
    public static final String SQL_CHANGE_PROD_PRICE = "update products set price = ? where id = ? ;";
    private static final String SQL_PRODUCT_RECOVER = "update products set is_deleted = false where name = ?";
    private static final String SQL_GET_PRODUCT_BY_NAME = "select  * from products where name = ?";

    /**
     * init section
     * */
    private static final DbProductManager instance = new DbProductManager();
    public static final String SQL_GET_PRODUCT_NUMBER_BY_ID ="select number from products where id = ?;";


    public static synchronized DbProductManager getInstance() {
        System.out.println("get db product manager instance");
        return instance;
    }


    private DbProductManager() {
    }


    /**
     * @return all products in db
     * if product is deleted will ont get it in list
     * */
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
                boolean isDelete = resultSet.getBoolean("is_deleted");
                Product product = new Product(prName, price, number, id, isDelete);
                if (!isDelete)products.add(product);

            }

        } catch (DBException | SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    /**
     * @deprecated
     * */
    public Product getProductByName(String name){
        Product product = null;
        try (Connection conn = getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(SQL_GET_PRODUCT_BY_NAME);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                String prName = resultSet.getString("name");
                Double price = resultSet.getDouble("price");
                Double number = resultSet.getDouble("number");
                Integer id2 = resultSet.getInt("id");
                boolean isDelete = resultSet.getBoolean("is_deleted");
                product = new Product(prName, price, number, id2, isDelete);

            }
        } catch (DBException | SQLException e) {
            e.printStackTrace();
        }

        return product;
    }

    private boolean ifProductExist(Connection conn, String name) throws SQLException {
        List<Product> allProducts = new ArrayList<>();
            PreparedStatement preparedStatement = conn.prepareStatement(SQL_GET_ALL_PRODUCTS);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            Product product = new Product(
                    resultSet.getString("name"),
                    resultSet.getDouble("price"),
                    resultSet.getDouble("number"),
                    resultSet.getInt("id"),
                    resultSet.getBoolean("is_deleted")
            );
            allProducts.add(product);
        }

        for (Product product : allProducts) {
            if (product.getName().equals(name)) return true;
        }
        return false;
    }

    /**
     * insert product to db
     * */
    public boolean insertProduct(Product product){
        try (Connection conn = getConnection()) {
            if(!ifProductExist(conn, product.getName())){
                PreparedStatement preparedStatement = conn.prepareStatement(SQL_INSERT_PRODUCT);
                preparedStatement.setString(1, product.getName());
                preparedStatement.setDouble(2, product.getPrice());
                preparedStatement.setDouble(3, product.getNumber());
                if (preparedStatement.executeUpdate()>0){
                    return true;
                }
            }else {
                productRecover(conn, product);
            }


        } catch (DBException | SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    /**
     * recover deleted product
     * */
    private void productRecover(Connection conn, Product product) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(SQL_PRODUCT_RECOVER);
        preparedStatement.setString(1, product.getName());
        preparedStatement.executeUpdate();
    }

    /**
     * set true for is_deleted field of product
     * */
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

    /**
     * change number of products
     * */
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
    /**
     * change price of products
     * */
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

    /**
     * get product by id
     * @return product from db
     * */
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
                boolean isDelete = resultSet.getBoolean("is_deleted");
                product = new Product(prName, price, number, idp, isDelete);

            }
        } catch (DBException | SQLException e) {
            e.printStackTrace();
        }
        return product;
    }
    /**
     * get number of products
     * */
    public Double getNumber(Integer productId){
        Double number = 0d;
        try (Connection conn = getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(SQL_GET_PRODUCT_NUMBER_BY_ID);
            preparedStatement.setInt(1, productId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                number = resultSet.getDouble("number");
            }

        } catch (DBException | SQLException e) {
            e.printStackTrace();
        }
        return number;
    }
}
