package my.project.services.db;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DbSuperManager {

    protected Connection getConnection() throws DBException {
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
