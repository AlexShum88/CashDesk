package my.project.db;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * super class for db managers
 * have method for get connection
 */
public class DbSuperManager {

    /**
     * create connection with db
     */
    public Connection getConnection() throws DBException {
        Context context;
        try {
            context = (Context) new InitialContext().lookup("java:/comp/env");
            DataSource dataSource = (DataSource) context.lookup("jdbc/mysql");

            return dataSource.getConnection();
        } catch (NamingException | SQLException e) {
            e.printStackTrace();
            throw new DBException(e);
        }
    }
}
