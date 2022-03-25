package my.project.db;

import my.project.model.User;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * class to work with db to interact with users and their roles
 */
public class DbManager extends DbSuperManager{
    private static final Logger logger = LogManager.getLogger(DbManager.class);

    /**
     * section for db commands
     */
    public static final String SQL_INSERT_USER = "insert into users (username, password, roles_id) values (?, ?, ?)";
    private static final String SQL_SELECT_ALL_ROLES = "Select * from roles";
    public static final String SQL_SELECT_USER_BY_NAME = "select * from users join roles where users.roles_id = roles.id and username = ?";
    public static final String SQL_SELECT_ALL_USERS = "select *  from users join roles where users.roles_id = roles.id ";
    public static final String SQL_UPDATE_USER_ROLE = "update users set roles_id =? where username = ?;";
    public static final String SQL_FIND_USER_NAME = "Select username from users where login = ?";
    private static final String SQL_GET_USER_ID = "select id from users where username = ?";


    private static final DbManager instance = new DbManager();

    public static synchronized DbManager getInstance() {
        logger.error( "db manager get instance");
        return instance;
    }


    private DbManager() {
    }

    private int roleID(User user, Connection conn) throws SQLException {
        logger.info("in roleID");
        String role = user.getRole();
        PreparedStatement preparedStatement = conn.prepareStatement(SQL_SELECT_ALL_ROLES);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            if (resultSet.getString("role").equals(role)) return resultSet.getInt("id");
        }
        return 0;
    }

    private int roleID(String roleName, Connection conn) throws SQLException {
        logger.info("in roleID");
        PreparedStatement preparedStatement = conn.prepareStatement(SQL_SELECT_ALL_ROLES);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            if (resultSet.getString("role").equals(roleName)) return resultSet.getInt("id");
        }
        return 0;
    }
    /**
     * find user in db by name
     * */
    public boolean findUserName(String userName) throws DBException {
        try (Connection conn = getConnection()) {
            logger.info("find user name");
            PreparedStatement preparedStatement = conn.prepareStatement(SQL_FIND_USER_NAME);
            preparedStatement.setString(1, userName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            logger.error("cant find user by name");
        }
        return false;
    }

    /**
     * insert user to db
     * if user is existing user will see error page
     * @param user get user
     * */
    public  boolean InsertUser (User user) throws DBException {
        try (Connection conn = getConnection()) {
            logger.info("insert user");
            if (findUserName(user.getLogin())){return false;}
            PreparedStatement preparedStatement = conn.prepareStatement(SQL_INSERT_USER);
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setInt(3, roleID(user, conn));
            if (preparedStatement.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException | DBException throwables) {
            throwables.printStackTrace();
            logger.error("cant insert user");
        }
        return false;
    }

    /**
     * get user from db
     * @return user by name
     * @param login username
     * */
    public User getUser(String login) throws DBException {
        try (Connection conn = getConnection()) {
            logger.debug("get user");

            PreparedStatement preparedStatement = conn.prepareStatement(SQL_SELECT_USER_BY_NAME);
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String role = resultSet.getString("role");
                Integer id = resultSet.getInt("id");
                logger.info("get {} role {}", username, role);
                return new User(username, password, role, id);
            }

        } catch (SQLException | DBException throwables) {
            throwables.printStackTrace();
        }
        logger.error( "cant get user: probably is absent in DB");
        return null;
    }

    /**
     * get all users from db
     * @return list of users in db */
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection conn = getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(SQL_SELECT_ALL_USERS);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String role = resultSet.getString("role");
                Integer id = resultSet.getInt("id");
                logger.log(Level.INFO, "get %s role %s", username, role);
                users.add(new User(username, password, role, id));
            }
        } catch (DBException | SQLException e) {
            e.printStackTrace();
            logger.error( "get all user exception");
        }
        return users;
    }

    /**
     * change role of user
     * @param newRole new role
     * @param userName by username
     * */
    public boolean changeRole(String userName, String newRole){
        try (Connection conn = getConnection()) {
            int roleID = roleID(newRole, conn);
            String usname = userName.strip();
            PreparedStatement preparedStatement = conn.prepareStatement(SQL_UPDATE_USER_ROLE);

            logger.info("form dbm role {} role id {}, username {}", newRole, roleID(newRole, conn), usname);

            preparedStatement.setInt(1, roleID);
            preparedStatement.setString(2, usname);
            if (preparedStatement.executeUpdate() > 0) {
                return true;
            }

        } catch (DBException | SQLException e) {
            e.printStackTrace();
            logger.error( "cant change role");
        }

        return false;
    }

    /**
     * @deprecated
     */
    public Integer UserId(String userName){
        try (Connection conn = getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(SQL_GET_USER_ID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                return resultSet.getInt("id");
            }
        } catch (DBException | SQLException e) {
            e.printStackTrace();
            logger.error( "get user id exception");
        }
        return -1;

    }

}