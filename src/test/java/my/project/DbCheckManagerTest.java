package my.project;

import my.project.entity.Product;
import my.project.entity.Transaction;
import my.project.services.db.DBException;
import my.project.services.db.DbCheckManager;
import my.project.services.db.DbProductManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static my.project.services.db.DbCheckManager.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class DbCheckManagerTest {
    DbCheckManager dbm = mock(DbCheckManager.class);

    @Before
    public void getConn() throws DBException, SQLException {
        when(dbm.getConnection()).thenReturn(DriverManager.getConnection("jdbc:mysql://localhost:3306/cash_deck_test?" +
                "password=root&" +
                "user=root"
        ));
    }

    @After
    public void after() {

    }



    @Test
    public void newCheck() {
        try (Connection conn = dbm.getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(SQL_NEW_CHECK);
            preparedStatement.setInt(1, 1);
            assertTrue((preparedStatement.executeUpdate() > 0));

        } catch (DBException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteCheck() {
        int checkId = 1;
        try (Connection conn = dbm.getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(SQL_DELETE_CHECK);
            preparedStatement.setInt(1, checkId);
            assertTrue((preparedStatement.executeUpdate() > 0));
        } catch (SQLException | DBException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addProd() {
        int transactionId = 1;
        int productId = 1;
        double price = 1.0;
        try (var conn = dbm.getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(SQL_ADD_PRODUCT);
            preparedStatement.setInt(1, transactionId);
            preparedStatement.setInt(2, productId);
            preparedStatement.setDouble(3, price);
            assertTrue((preparedStatement.executeUpdate() > 0));

        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    @Test
    public void setProdNumber() {
        int transactionId = 1;
        int productId = 1;
        double number = 1.0;
        try (Connection conn = dbm.getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(SQL_SET_PROD_NUMBER);
            preparedStatement.setDouble(1, number);
            preparedStatement.setInt(2, transactionId);
            preparedStatement.setInt(3, productId);

            assertTrue(preparedStatement.executeUpdate() > 0);

        } catch (SQLException | DBException throwables) {
            throwables.printStackTrace();
        }

    }


    @Test
    public void deleteProd (){
        int transactionId = 1;
        int productId = 1;
        try (Connection conn = dbm.getConnection()) {
            when(dbm.addProd(1,1)).thenReturn(true);
            PreparedStatement preparedStatement = conn.prepareStatement(SQL_DELETE_PROD);
            preparedStatement.setInt(1, transactionId);
            preparedStatement.setInt(2, productId);
            assertTrue((preparedStatement.executeUpdate() > 0));
        } catch (SQLException | DBException throwables) {
            throwables.printStackTrace();

        }

    }

    @Test
    public void closedCheck(){
        int checkID = 1;
        try (Connection conn = dbm.getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(SQL_CLOSED_CHECK);
            preparedStatement.setInt(1, checkID);

            assertTrue((preparedStatement.executeUpdate() > 0));

        } catch (SQLException | DBException throwables) {
            throwables.printStackTrace();

        }

    }


    @Test
    public void setCurrentPrice () {
        int transactionId = 1;
        int productId = 1;
        try (Connection conn = dbm.getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(SQL_SET_PROD_PRICE);

            preparedStatement.setDouble(1,1d);
            preparedStatement.setInt(2, transactionId);
            preparedStatement.setInt(3, productId);

            assertTrue((preparedStatement.executeUpdate() > 0));

        } catch (SQLException | DBException throwables) {
            throwables.printStackTrace();
        }


    }

    @Test
    public void getProdPrice(){
        int productId = 1;
        try (Connection conn = dbm.getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(SQL_GET_PROD_PRICE_BY_ID);
            preparedStatement.setInt(1, productId);
            ResultSet resultSet = preparedStatement.executeQuery();
            assertTrue(resultSet.next());
            assertEquals(1.0,resultSet.getDouble("price"), 0.00001);
        } catch (SQLException | DBException throwables) {
            throwables.printStackTrace();

        }
    }
    @Test
    public void getCurrentPrice() {
        Integer transactionId =1;
        Integer productId = 1;

        try (Connection conn = dbm.getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(SQL_GET_CURRENT_PRICE);
            preparedStatement.setInt(1, transactionId);
            preparedStatement.setInt(2, productId);
            ResultSet resultSet = preparedStatement.executeQuery();
            assertTrue(resultSet.next());
            assertEquals(0.0,resultSet.getDouble("current_price"), 0.001);

        } catch (DBException | SQLException e) {

            e.printStackTrace();
        }

    }
    @Test
    public void setTotalSum() {
        Double totalSum = 1d;
        Integer transactionId = 1;
        try (Connection conn = dbm.getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(SQL_SET_TOTAL_SUM);
            preparedStatement.setDouble(1, totalSum);
            preparedStatement.setInt(2, transactionId);
            assertTrue(preparedStatement.executeUpdate() > 0);

        } catch (DBException | SQLException e) {
            e.printStackTrace();
        }

    }
    @Test
    public void getTotalSum() {
        int id = 1;

        try (Connection conn = dbm.getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(SQL_GET_TOTAL_SUM);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            assertTrue(resultSet.next());
            assertEquals(1.0, resultSet.getDouble("total"), 0.001);

        } catch (DBException | SQLException e) {
            e.printStackTrace();
        }

    }
    @Test
    public void getNumber(){
        Integer checkId =1;
        Integer productId= 1;
        try (Connection conn = dbm.getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(SQL_GET_CURRENT_NUMBER);
            preparedStatement.setInt(1, checkId);
            preparedStatement.setInt(2, productId);
            ResultSet resultSet = preparedStatement.executeQuery();
            assertTrue(resultSet.next());
            assertEquals(0.0,resultSet.getDouble("number"), 0.001);

        } catch (DBException | SQLException e) {
            e.printStackTrace();
        }

    }
    @Test
    public void setDate( ) {
        Date date = new Date();
        int id = 1;
        try (Connection conn = dbm.getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(SQL_SET_DATE);

            preparedStatement.setString(1, date.toString());
            preparedStatement.setInt(2, id);
            assertTrue(preparedStatement.executeUpdate() > 0);
        } catch (DBException | SQLException e) {

            e.printStackTrace();
        }
    }

    @Test
    public void getAllChecks() {
        List<Transaction> list = new ArrayList<>();
        Transaction transaction = new Transaction();
        transaction.setId(2);
        transaction.setTotal(1.0);
        transaction.setAutorId(1);

        List<Transaction> list1 = new ArrayList<>();
        list1.add(transaction);

        try (Connection conn = dbm.getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(SQL_GET_ALL_CHECKS);
            ResultSet resultSet = preparedStatement.executeQuery();
            assertTrue(resultSet.next());
            while (resultSet.next()) {
                list.add(
                        new Transaction(
                                resultSet.getInt("id"),
                                resultSet.getDouble("total"),
                                resultSet.getInt("user_id"),
                                resultSet.getBoolean("is_canseled"),
                                resultSet.getInt("cansel_autor"),
                                resultSet.getBoolean("is_closed")
                        )
                );

            }
            assertEquals(list1.get(0),list.get(0));

        } catch (DBException | SQLException e) {
            e.printStackTrace();

        }
    }



    @Test
    public void getCheckId(){
        int userID = 1;
        int expectedCheckId = 1;
        try (Connection conn = dbm.getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(SQL_GET_LAST_CHECK_ID_BY_USER_ID );
            preparedStatement.setInt(1, userID);
            ResultSet resultSet = preparedStatement.executeQuery();
            assertTrue(resultSet.next());
//            assertEquals(expectedCheckId, resultSet.getInt("id"));

        } catch (SQLException | DBException throwables) {
            throwables.printStackTrace();
        }

    }
    @Test
    public void getCheck(){

        int getCheckId = 1;
        try (Connection conn = dbm.getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(SQL_GET_LAST_CHECK_BY_USER_ID);
            preparedStatement.setInt(1, getCheckId);
            ResultSet resultSet = preparedStatement.executeQuery();

            assertTrue(resultSet.next());

            Integer id = resultSet.getInt("id");
            Integer userId = resultSet.getInt("user_id");
            Double total = resultSet.getDouble("total");
            Boolean iscanseled = resultSet.getBoolean("is_canseled");
            Integer canselAutor = resultSet.getInt("cansel_autor");
            Transaction transaction = new Transaction(id, total, userId, iscanseled, canselAutor);
            Transaction expTransaction = new Transaction();
            expTransaction.setAutorId(1);
            expTransaction.setId(1);
            expTransaction.setTotal(1.0);
            expTransaction.setList(null);
            expTransaction.setAutorId(1);
            expTransaction.setCanceled(true);

            assertEquals(expTransaction, transaction);

        } catch (SQLException | DBException throwables) {
            throwables.printStackTrace();

        }

    }
    @Test
    public void getAllProdOfCheck(){
        Integer transactionID = 1;
        List<Product> products = new ArrayList<>();
        try (Connection conn = dbm.getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(SQL_GET_ALL_PROD_OF_THIS_CHECK);
            preparedStatement.setInt(1, transactionID);
            ResultSet resultSet = preparedStatement.executeQuery();

            assertTrue(resultSet.next());

            DbProductManager dpm = mock(DbProductManager.class);
            when(dpm.getConnection()).thenReturn(DriverManager.getConnection("jdbc:mysql://localhost:3306/cash_deck?" +
                    "password=root&" +
                    "user=root"
            ));

            products.add(dpm.getProductById(resultSet.getInt("products_id")));
            List<Product> products1 = new ArrayList<>();
            products1.add(null);
            assertEquals(products1, products);

        } catch (SQLException | DBException throwables) {
            throwables.printStackTrace();
        }

    }
}
