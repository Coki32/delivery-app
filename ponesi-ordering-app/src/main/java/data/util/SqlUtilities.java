package data.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlUtilities {

    public static void close(Statement s, ResultSet r) {
        if (s != null) {
            try {
                s.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if (r != null) {
            try {
                r.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
