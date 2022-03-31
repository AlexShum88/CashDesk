package my.project;

import my.project.model.User;
import my.project.db.DBException;
import my.project.db.DbManager;
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

public class DbManagerTest {

    DbManager dbm = mock(DbManager.class);
    String base = "jdbc:mysql://localhost:3306/cash_deck_test?" +
            "password=root&" +
            "user=root";

    @Before
    public void getConn() throws DBException, SQLException {
        when(dbm.getConnection()).thenReturn(DriverManager.getConnection(base));
    }

    @After
    public void after() throws DBException, SQLException {
        dbm.getConnection().close();
    }

    @Test
    public void findUserName() throws DBException {
        String userName = "root";
        try (Connection conn = dbm.getConnection()) {

            PreparedStatement preparedStatement = conn.prepareStatement(DbManager.SQL_FIND_USER_NAME);
            preparedStatement.setString(1, userName);
            ResultSet resultSet = preparedStatement.executeQuery();
            assertTrue(resultSet.next());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Test
    public  void InsertUser () {
        try (Connection conn = dbm.getConnection()) {
            User user = new User("vasia", "123456", "admin");
            PreparedStatement preparedStatement = conn.prepareStatement(DbManager.SQL_INSERT_USER);
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setInt(3, 1);
            assertTrue(preparedStatement.executeUpdate() > 0);
        } catch (SQLException | DBException throwables) {
            throwables.printStackTrace();
        }

    }

    @Test
    public void getUser() throws DBException {
        String login = "vasia";
        try (Connection conn = dbm.getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(DbManager.SQL_SELECT_USER_BY_NAME);
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            assertTrue(resultSet.next());
            String username = resultSet.getString("username");
            String password = resultSet.getString("password");
            String role = resultSet.getString("role");
            Integer id = resultSet.getInt("id");

            User user = new User(username, password, role, id);
            User user1 = new User("vasia", "123456", "admin");

            assertEquals(user1, user);

        } catch (SQLException | DBException throwables) {
            throwables.printStackTrace();
        }

    }

    @Test
    public void getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection conn = dbm.getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(DbManager.SQL_SELECT_ALL_USERS);
            ResultSet resultSet = preparedStatement.executeQuery();

            assertTrue(resultSet.next());

            String username = resultSet.getString("username");
            String password = resultSet.getString("password");
            String role = resultSet.getString("role");
            Integer id = resultSet.getInt("id");

            users.add(new User(username, password, role, id));

            List<User> users1 = new ArrayList<>();
            User user = new User("root", "123456", "admin");
            users1.add(user);
            assertEquals(users1, users);

        } catch (DBException | SQLException e) {
            e.printStackTrace();
        }

    }
    @Test
    public void changeRole(){
        String userName ="root";

        try (Connection conn = dbm.getConnection()) {
            int roleID = 1;
            String usname = userName.strip();
            PreparedStatement preparedStatement = conn.prepareStatement(DbManager.SQL_UPDATE_USER_ROLE);

            preparedStatement.setInt(1, roleID);
            preparedStatement.setString(2, usname);
            assertTrue(preparedStatement.executeUpdate() > 0);
        } catch (DBException | SQLException e) {
            e.printStackTrace();
        }

    }

}
