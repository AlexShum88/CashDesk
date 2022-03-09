package my.project;

import my.project.entity.Product;
import my.project.services.db.DBException;
import my.project.services.db.DbProductManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DbProductManagerTest {
    DbProductManager dbm = mock(DbProductManager.class);

    @Before
    public void getConn() throws DBException, SQLException {
        when(dbm.getConnection()).thenReturn(DriverManager.getConnection("jdbc:mysql://localhost:3306/cash_deck_test?" +
                "password=root&" +
                "user=root"
        ));
    }

    @After
    public void after() throws DBException, SQLException {
        dbm.getConnection().close();
    }

    @Test
    public void getAllProducts() {
        List<Product> products = new ArrayList<>();
        try (Connection conn = dbm.getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(DbProductManager.SQL_GET_ALL_PRODUCTS);
            ResultSet resultSet = preparedStatement.executeQuery();

            assertTrue(resultSet.next());

            String prName = resultSet.getString("name");
            Double price = resultSet.getDouble("price");
            Double number = resultSet.getDouble("number");
            Integer id = resultSet.getInt("id");
            boolean isDelete = resultSet.getBoolean("is_deleted");
            Product product = new Product(prName, price, number, id, isDelete);
            //if (!isDelete)
             products.add(product);

            List<Product> products1 = new ArrayList<>();
            Product forTest = new Product();
            forTest.setName("fish");
            forTest.setPrice(13.0);
            products1.add(forTest);

            assertEquals(products1, products);

        } catch (DBException | SQLException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void insertProduct(){
        Product product = new Product("test", 1.0, 1.0);
        try (Connection conn = dbm.getConnection()) {

            PreparedStatement preparedStatement = conn.prepareStatement(DbProductManager.SQL_INSERT_PRODUCT);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setDouble(3, product.getNumber());
            assertTrue(preparedStatement.executeUpdate()>0);

        } catch (DBException | SQLException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void deleteProduct(){
        Integer id = 1;
        try (Connection conn = dbm.getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(DbProductManager.SQL_DELETE_PRODUCT);
            preparedStatement.setInt(1, id);
            assertTrue(preparedStatement.executeUpdate()>0);
        } catch (DBException | SQLException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void getNumber(){
        Integer productId = 1;

        try (Connection conn = dbm.getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(DbProductManager.SQL_GET_PRODUCT_NUMBER_BY_ID);
            preparedStatement.setInt(1, productId);
            ResultSet resultSet = preparedStatement.executeQuery();
            assertTrue(resultSet.next());

            double number = resultSet.getDouble("number");

            assertEquals(200.0, number, 0.001);

        } catch (DBException | SQLException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void changeProductNumber(){
        int id =1;
        double num = 1.0;
        try (Connection conn = dbm.getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(DbProductManager.SQL_CHANGE_PROD_NUMBER);
            preparedStatement.setDouble(1, num);
            preparedStatement.setInt(2, id);
            assertTrue(preparedStatement.executeUpdate()>0);


            double num1 = -1.0;
            preparedStatement = conn.prepareStatement(DbProductManager.SQL_CHANGE_PROD_NUMBER);
            preparedStatement.setDouble(1, num1);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (DBException | SQLException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void changeProductPrice () {
        Integer id = 1;
        Double num =1.0;
        try (Connection conn = dbm.getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(DbProductManager.SQL_CHANGE_PROD_PRICE);
            preparedStatement.setDouble(1, num);
            preparedStatement.setInt(2, id);
            assertTrue(preparedStatement.executeUpdate()>0);
        } catch (DBException | SQLException e) {
            e.printStackTrace();
        }

    }


    @Test
    public void getProductById() {
        int id = 1;
        Product product = null;
        try (Connection conn = dbm.getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(DbProductManager.SQL_GET_PRODUCT_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            assertTrue(resultSet.next());
                String prName = resultSet.getString("name");
                Double price = resultSet.getDouble("price");
                Double number = resultSet.getDouble("number");
                Integer idp = resultSet.getInt("id");
                boolean isDelete = resultSet.getBoolean("is_deleted");
                product = new Product(prName, price, number, idp, isDelete);

                Product forTest = new Product("fish", 1.0, 1.0);

                assertEquals(forTest, product);


        } catch (DBException | SQLException e) {
            e.printStackTrace();
        }

    }


}
