package my.project.db;

import my.project.model.Product;
import my.project.model.Transaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
/**
 * class for interact with db in case of check
 * */
public class DbCheckManager extends DbSuperManager{

    private static final Logger logger = LogManager.getLogger(DbManager.class);
    private static final DbCheckManager instance = new DbCheckManager();


    /**
     * section for db commands
     * */
    public static final String SQL_NEW_CHECK = "insert into transaction (user_id) values (?);";
    public static final String SQL_DELETE_CHECK = "update transaction set is_canseled = true where id = ?; ";
    public static final String SQL_ADD_PRODUCT = "insert into transaction_has_products (transaction_id, products_id, price ) values (?, ?, ?);";
    public static final String SQL_GET_PROD_PRICE_BY_ID = "select price from products where id = ?;";
    private static final String SQL_GET_PROD_ID_BY_NAME = "select id from products where name = ?;";
    private static final String SQL_GET_PROD_NAME_BY_ID = "select name from products where id = ?;";
    public static final String SQL_SET_PROD_NUMBER = "update  transaction_has_products set number = number + ? where transaction_id = ?  and products_id = ?;";
    public static final String SQL_SET_PROD_PRICE = "update  transaction_has_products set current_price = ? where transaction_id = ?  and products_id = ?;";
    private static final String SQL_GET_PRICE_AND_NUMBER = "select price, number from transaction_has_products where transaction_id = ?  and products_id = ?;";
    public static final String SQL_GET_CURRENT_PRICE = "select current_price from transaction_has_products where transaction_id = ?  and products_id = ?;";
    public static final String SQL_DELETE_PROD = "delete from transaction_has_products where transaction_id = ? and products_id = ?;";
    public static final String SQL_GET_ALL_PROD_OF_THIS_CHECK = "select products_id from transaction_has_products where transaction_id = ?;";
    public static final String SQL_GET_LAST_CHECK_ID_BY_USER_ID = "select id from transaction where user_id = ? order by id desc; ";
    public static final String SQL_GET_LAST_CHECK_BY_USER_ID = "select * from transaction where id = ?;";
    public static final String SQL_CLOSED_CHECK = "update transaction set is_closed = true where id = ?";
    public static final String SQL_SET_TOTAL_SUM = "update transaction set total = ? where id=?;";
    public static final String SQL_GET_TOTAL_SUM = "select total from transaction where id =?";
    public static final String SQL_GET_CURRENT_NUMBER = "select number from transaction_has_products where transaction_id = ? and products_id = ?;";
    public static final String SQL_SET_DATE = "update transaction set date = ? where id=?";
    public static final String SQL_GET_ALL_CHECKS = "select * from transaction ;";
    private static final String SQL_SET_CANSEL_AUTOR = "update transaction set cansel_autor = ? where id=?;";

    /**
     * init section
     * */
    public static synchronized DbCheckManager getInstance() {
        logger.debug("get db check manager instance");
        return instance;
    }

    private DbCheckManager() {
    }

    /**
     * creates a new check in db and connects it with the cashier who creates it
     * @param cashierID for connect check with cashier
     * */
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

    private void setCanselAutor(Connection conn, Integer author, Integer checkId) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(SQL_SET_CANSEL_AUTOR);
        preparedStatement.setInt(1, author);
        preparedStatement.setInt(2, checkId);
        preparedStatement.executeUpdate();
    }

    /**
     * mark check as deleted
     * @param author set author of cansel check
     * */
    public boolean deleteCheck(Integer checkID, Integer author){
        logger.debug("delete check");
        try (Connection conn = getConnection()) {
            setCanselAutor(conn, author, checkID);
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

    /**
     * add product to check
     * */
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

    /**
     * get current price of product
     * */
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

    /**
     * set number of product in check
     * @param transactionID check id
     * */
    public boolean setProdNumber(Double number, Integer transactionID, Integer prodID){
        logger.debug("set prod number");
        DbProductManager dbProductManager = DbProductManager.getInstance();
        Double number1 = dbProductManager.getNumber(prodID);
        if (number1-number<0){
            return false;
        }
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

    /**
     * delete product form check in db
     * @param transactionID check id
     * */
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

    /**
     * get all product form check
     * @param transactionID check id
     * */
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

    /**
     * get check
     * */
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
                Boolean iscanseled = resultSet.getBoolean("is_canseled");
                Integer canselAutor = resultSet.getInt("cansel_autor");
                transaction = new Transaction(id, total, userId, iscanseled, canselAutor);
            }
        } catch (SQLException | DBException throwables) {
            throwables.printStackTrace();
            logger.error("get error");
        }
        return transaction;

    }

    /**
     * get last check for this user
     * @return check id
     * @param userID cashier id
     * */
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

    /**
     * mark check as closed
     * */
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

    /**
     * set current price of product by
     * */
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
                return true;
            }
        } catch (SQLException | DBException throwables) {
            throwables.printStackTrace();
            logger.error("get error");
        }
        return false;

    }

    /**
     * make current price of product
     * by number*price
     * */
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
        Double res = number * price;
        logger.debug("result of mult in #getPriseAndNumber {}",res);
        return res;
    }

    /**
     * @return current price
     * */
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

    /**
     * set sum of all prices of products in check
     * and write it to total field in transaction table in db
     * */
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

    /**
     * get total sum from db
     * */
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

    /**
     * get number of such product in check
     * */
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

    /**
     * set date of create check or close check
     * */
    public void setDate(Date date, int id ) {
        logger.debug("set date");
        try (Connection conn = getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(SQL_SET_DATE);
            logger.debug("date = {}", date);
            preparedStatement.setString(1, date.toString());
            preparedStatement.setInt(2, id);
            if (preparedStatement.executeUpdate() > 0) {
                logger.debug("set total sum = true");
            }
        } catch (DBException | SQLException e) {
            logger.debug("get error");
            e.printStackTrace();
        }
    }

    /**
     * @return all checks form db
     * */
    public List<Transaction> getAllChecks() {
        List<Transaction> list = new ArrayList<>();
        logger.debug("set date");
        try (Connection conn = getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(SQL_GET_ALL_CHECKS);
            ResultSet resultSet = preparedStatement.executeQuery();
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
        } catch (DBException | SQLException e) {
            e.printStackTrace();

        }
        return list;
    }

}