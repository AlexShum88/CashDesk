package my.project.services.db;

import my.project.entity.User;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

// от марины подключение к базе данных,т.к подключение надо делать через мета-инф, а не апп.пропертиз
//public Connection getConnection(){
//        Context ctx;
//        Connection c = null;
//        try {
//        ctx = new InitialContext();
//        DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/postgres");
//        c = ds.getConnection();
//        } catch (NamingException e) {
//        e.printStackTrace();
//        } catch (SQLException e) {
//        e.printStackTrace();
//        }
//        return c;
//        }


public class DbManager extends DbSuperManager{
    static Logger logger = LogManager.getLogger("dbmLogger");


    private static final String SQL_INSERT_USER = "insert into users (username, password, roles_id) values (?, ?, ?)";
    private static final String SQL_SELECT_ALL_ROLES = "Select * from roles";








    private static String getURL() {
        Properties properties = new Properties();
        try (InputStream in = Files.newInputStream(Paths.get("app.properties"))) {
            properties.load(in);
        } catch (IOException exception) {
            logger.error("Cannot find file.");
        }
        return properties.getProperty("connection.url");
    }

    private static Connection getConnection(String connectionUrl) throws DBException {
        try {
            return DriverManager.getConnection(connectionUrl);
        } catch (SQLException throwables) {
            throw new DBException("err", throwables);
        }
    }

    private static final DbManager instance = new DbManager();

    public static synchronized DbManager getInstance() {
        System.out.println("get db manager instance");
        return instance;
    }


    private DbManager() {
    }

    private int roleID(User user, Connection conn) throws SQLException {
        logger.log(Level.INFO,"roleID");
        String role = user.getRole();
        PreparedStatement preparedStatement = conn.prepareStatement(SQL_SELECT_ALL_ROLES);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            if (resultSet.getString("role").equals(role)) return resultSet.getInt("id");
        }
        return 0;
    }

    private int roleID(String roleName, Connection conn) throws SQLException {
        logger.log(Level.INFO,"roleID");
        PreparedStatement preparedStatement = conn.prepareStatement(SQL_SELECT_ALL_ROLES);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            if (resultSet.getString("role").equals(roleName)) return resultSet.getInt("id");
        }
        return 0;
    }

    public boolean findUserName(String userName) throws DBException {
        try (Connection conn = getConnection()) {
            System.out.println(conn);
            PreparedStatement preparedStatement = conn.prepareStatement("Select username" +
                    "from users" +
                    " where login = ?");
            preparedStatement.setString(1, userName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public  boolean InsertUser (User user) throws DBException {
        try (Connection conn = getConnection()) {
            logger.log(Level.TRACE,"insert user");
            //__________________
            //check user if user in table. if it so need to send message to user.
            //TODO need to send message to user that he is on table.
            if (findUserName(user.getLogin())){return false;}
            //___________________
            PreparedStatement preparedStatement = conn.prepareStatement(SQL_INSERT_USER);
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setInt(3, roleID(user, conn));
            if (preparedStatement.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException | DBException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public User getUser(String login) throws DBException {

        //here new connection getter
        try (Connection conn = getConnection()) {
            logger.log(Level.TRACE,"get user");
            System.out.println(conn);
            PreparedStatement preparedStatement = conn.prepareStatement("select *  " +
                    "from users join roles " +
                    "where users.roles_id = roles.id " +
                    "and " +
                    "username = ?");
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String role = resultSet.getString("role");
                logger.log(Level.INFO,"get %s role %s", username, role);
                return new User(username, password, role);
            }

        } catch (SQLException | DBException throwables) {
            throwables.printStackTrace();
        }
        logger.log(Level.DEBUG, "cant get user: probably is absent in DB");
        return null;
    }


    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection conn = getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement("select *  " +
                    "from users join roles " +
                    "where users.roles_id = roles.id ");

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String role = resultSet.getString("role");
                logger.log(Level.INFO, "get %s role %s", username, role);
                users.add(new User(username, password, role));
            }
        } catch (DBException | SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public boolean changeRole(String userName, String newRole){
        try (Connection conn = getConnection()) {
            int roleID = roleID(newRole, conn);
            String usname = userName.strip();
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "update users set roles_id =? where username = ?;"
            );
            System.out.println("form dbm role "+ newRole +"+ role id " + roleID(newRole, conn) + " and username " + usname);
            preparedStatement.setInt(1, roleID);
            preparedStatement.setString(2, usname);
            if (preparedStatement.executeUpdate() > 0) {
                return true;
            }

        } catch (DBException | SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

}