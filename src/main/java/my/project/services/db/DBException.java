package my.project.services.db;

import java.io.IOException;

public class DBException extends IOException {
    public DBException(String message, Throwable cause) {
    }

    public DBException(Exception e) {
    }
}
