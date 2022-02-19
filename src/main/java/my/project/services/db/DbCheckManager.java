package my.project.services.db;

import my.project.entity.Product;
import my.project.entity.Transaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.SplittableRandom;

public class DbCheckManager extends DbSuperManager{

    private static final Logger logger = LogManager.getLogger(DbManager.class);
    private static final DbCheckManager instance = new DbCheckManager();



    private static final String SQL_NEW_CHECK = "insert into transaction (user_id) values (?);";
    private static final String SQL_DELETE_CHECK = "update transaction set iscanseled = true where id = ?; ";
    private static final String SQL_ADD_PRODUCT = "insert into transaction_has_products (transaction_id, products_id, price ) values (?, ?, ?);";
    private static final String SQL_GET_PROD_PRICE_BY_ID = "select price from products where id = ?;";
    private static final String SQL_GET_PROD_ID_BY_NAME = "select id from products where name = ?;";
    private static final String SQL_GET_PROD_NAME_BY_ID = "select name from products where id = ?;";
    private static final String SQL_SET_PROD_NUMBER = "update  transaction_has_products set number = number + ? where transaction_id = ?  and products_id = ?;";
    private static final String SQL_SET_PROD_PRICE = "update  transaction_has_products set current_price = ? where transaction_id = ?  and products_id = ?;";
    private static final String SQL_GET_PRICE_AND_NUMBER = "select price, number from transaction_has_products where transaction_id = ?  and products_id = ?;";
    private static final String SQL_GET_CURRENT_PRICE = "select current_price from transaction_has_products where transaction_id = ?  and products_id = ?;";
    private static final String SQL_DELETE_PROD = "delete from transaction_has_products where transaction_id = ? and products_id = ?;";
    private static final String SQL_GET_ALL_PROD_OF_THIS_CHECK = "select products_id from transaction_has_products where transaction_id = ?;";
    private static final String SQL_GET_LAST_CHECK_ID_BY_USER_ID = "select id from transaction where user_id = ? order by id desc; ";
    private static final String SQL_GET_LAST_CHECK_BY_USER_ID = "select * from transaction where id = ?;";
    private static final String SQL_CLOSED_CHECK = "update transaction set is_closed = true where id = ?";
    private static final String SQL_SET_TOTAL_SUM = "update transaction set total = ? where id=?;";
    private static final String SQL_GET_TOTAL_SUM = "select total from transaction where id =?";
    private static final String SQL_GET_CURRENT_NUMBER = "select number from transaction_has_products where transaction_id = ? and products_id = ?;";


    public static synchronized DbCheckManager getInstance() {
        logger.debug("get db check manager instance");
        return instance;
    }

    private DbCheckManager() {
    }

    public boolean newCheck(Integer cashierID) {
        logger.debug("new check");
        try (Connection conn = getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(SQL_NEW_CHECK);
            preparedStatement.setInt(1, cashierID);
            if (preparedStatement.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException | DBException throwables) {
            throwables.printStackTrace();
            logger.error("get error");
        }
        return false;
    }

    public boolean deleteCheck(Integer checkID){
        logger.debug("delete check");
        try (Connection conn = getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(SQL_DELETE_CHECK);
            preparedStatement.setInt(1, checkID);
            if (preparedStatement.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException | DBException throwables) {
            throwables.printStackTrace();
            logger.error("get error");
        }
        return false;
    }

    public boolean addProd (Integer transactionID, Integer prodID){
        logger.debug("add prod");
        try (Connection conn = getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(SQL_ADD_PRODUCT);
            preparedStatement.setInt(1, transactionID);
            preparedStatement.setInt(2, prodID);
            preparedStatement.setDouble(3, getProdPrice(prodID));
            if (preparedStatement.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException | DBException throwables) {
            throwables.printStackTrace();
            logger.error("get error");
        }
        return false;
    }

    private Double getProdPrice(Integer prodID){
        logger.debug("get prod price");
        try (Connection conn = getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(SQL_GET_PROD_PRICE_BY_ID);
            preparedStatement.setInt(1, prodID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                return resultSet.getDouble("price");
            }
        } catch (SQLException | DBException throwables) {
            throwables.printStackTrace();
            logger.error("get error");
        }
        return 0d;
    }

    private Integer getProdId(String name){
        logger.debug("get prod id");
        try (Connection conn = getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(SQL_GET_PROD_ID_BY_NAME);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                return resultSet.getInt("id");
            }
        } catch (SQLException | DBException throwables) {
            throwables.printStackTrace();
            logger.error("get error");
        }
        return 0;
    }

    private String getProdName (Integer prodID){
        logger.debug("get prod name");
        try (Connection conn = getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(SQL_GET_PROD_NAME_BY_ID);
            preparedStatement.setInt(1, prodID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                return resultSet.getString("name");
            }
        } catch (SQLException | DBException throwables) {
            throwables.printStackTrace();
            logger.error("get error");
        }
        return "noname";

    }

    public boolean setProdNumber(Double number, Integer transactionID, Integer prodID){
        logger.debug("set prod number");
        try (Connection conn = getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(SQL_SET_PROD_NUMBER);
            preparedStatement.setDouble(1, number);
            preparedStatement.setInt(2, transactionID);
            preparedStatement.setInt(3, prodID);
            if (preparedStatement.executeUpdate() > 0) {
                logger.debug("set prod number = true");
                return true;
            }
        } catch (SQLException | DBException throwables) {
            throwables.printStackTrace();
            logger.error("get error");
        }
        return false;
    }

    public boolean deleteProd (Integer transactionID, Integer prodID){
        logger.debug("delete prod");
        try (Connection conn = getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(SQL_DELETE_PROD);
            preparedStatement.setInt(1, transactionID);
            preparedStatement.setDouble(2, prodID);
            if (preparedStatement.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException | DBException throwables) {
            throwables.printStackTrace();
            logger.error("get error");
        }
        return false;
    }

    public List<Product> getAllProdOfCheck(Integer transactionID){
        logger.debug("get all product of check");
        List<Product> products = new ArrayList<>();
        try (Connection conn = getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(SQL_GET_ALL_PROD_OF_THIS_CHECK);
            preparedStatement.setInt(1, transactionID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                products.add(DbProductManager.getInstance().getProductById(resultSet.getInt("products_id")));
            }
        } catch (SQLException | DBException throwables) {
            throwables.printStackTrace();
            logger.error("get error");
        }
        return products;
    }

    public Transaction getCheck(Integer userID){
        logger.debug("get check");
        Transaction transaction = new Transaction();
        try (Connection conn = getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(SQL_GET_LAST_CHECK_BY_USER_ID);
            preparedStatement.setInt(1, getCheckId(userID));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                Integer userId = resultSet.getInt("user_id");
                Double total = resultSet.getDouble("total");
                Boolean iscanseled = resultSet.getBoolean("iscanseled");
                Date date = resultSet.getDate("date");
                Integer cansel_autor = resultSet.getInt("cansel_autor");
                transaction = new Transaction(id, total, userId, iscanseled, cansel_autor);
            }
        } catch (SQLException | DBException throwables) {
            throwables.printStackTrace();
            logger.error("get error");
        }
        return transaction;

    }

    public Integer getCheckId(Integer userID){
        logger.debug("get check id");
        try (Connection conn = getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(SQL_GET_LAST_CHECK_ID_BY_USER_ID );
            preparedStatement.setInt(1, userID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                return resultSet.getInt("id");
            }
        } catch (SQLException | DBException throwables) {
            throwables.printStackTrace();
            logger.error("get error");
        }
        return -1;
    }

    public boolean closedCheck(Integer checkID){
        logger.debug("closed check");
        try (Connection conn = getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(SQL_CLOSED_CHECK);
            preparedStatement.setInt(1, checkID);
            if (preparedStatement.executeUpdate() > 0) {
                logger.info("check {} is closed", checkID);
                return true;
            }
        } catch (SQLException | DBException throwables) {
            throwables.printStackTrace();
            logger.error("get error");
        }
        return false;
    }

    public boolean setCurrentPrice (Integer transactionId, Integer productId) {
        logger.debug("set current price");
        try (Connection conn = getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(SQL_SET_PROD_PRICE);
            Double currentPrice = makeCurrentPrice(transactionId, productId);
            preparedStatement.setDouble(1,currentPrice);
            preparedStatement.setInt(2, transactionId);
            preparedStatement.setInt(3, productId);
            if (preparedStatement.executeUpdate() > 0) {
                logger.debug("current price is {}", currentPrice);
                logger.debug("current price = true");
                return true;
            }
        } catch (SQLException | DBException throwables) {
            throwables.printStackTrace();
            logger.error("get error");
        }
        return false;

    }
    private Double makeCurrentPrice(Integer transactionId, Integer productId) {
        logger.debug("make current price");
        Double number = 0d;
        Double price = 0d;
        try (Connection conn = getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(SQL_GET_PRICE_AND_NUMBER);
            preparedStatement.setInt(1, transactionId);
            preparedStatement.setInt(2, productId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                number = resultSet.getDouble("number");
                logger.debug("number in set curr = {}", number);
                price = resultSet.getDouble("price");
                logger.debug("price in set curr = {}", price);
            }
        } catch (DBException | SQLException e) {
            e.printStackTrace();
        }
        var res = number * price;
        logger.debug("result of mult in #getPriseAndNumber {}",res);
        return res;
    }

    public Double getCurrentPrice(Integer transactionId, Integer productId) {
        logger.debug("get current price");
        Double current_price = 0d;
        try (Connection conn = getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(SQL_GET_CURRENT_PRICE);
            preparedStatement.setInt(1, transactionId);
            preparedStatement.setInt(2, productId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                current_price = resultSet.getDouble("current_price");
                logger.debug("current price = {}", current_price);
            }
        } catch (DBException | SQLException e) {
            logger.error("get error");
            e.printStackTrace();
        }
        return current_price;
    }


    public boolean setTotalSum(Double totalSum, Integer transactionId) {
        logger.debug("set total sum");
        try (Connection conn = getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(SQL_SET_TOTAL_SUM);
            preparedStatement.setDouble(1, totalSum);
            preparedStatement.setInt(2, transactionId);
            if (preparedStatement.executeUpdate() > 0) {
                logger.debug("set total sum = true");
                return true;
            }
        } catch (DBException | SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Double getTotalSum(int id) {
        logger.debug("get total sum");
        try (Connection conn = getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(SQL_GET_TOTAL_SUM);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                return resultSet.getDouble("total");
            }
        } catch (DBException | SQLException e) {
            e.printStackTrace();
        }
        return 0d;
    }

    public Double getNumber(Integer checkId, Integer productId){
        logger.debug("get number");
        try (Connection conn = getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(SQL_GET_CURRENT_NUMBER);
            preparedStatement.setInt(1, checkId);
            preparedStatement.setInt(2, productId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                return resultSet.getDouble("number");
            }
        } catch (DBException | SQLException e) {
            e.printStackTrace();
        }
        return 0d;
    }
}