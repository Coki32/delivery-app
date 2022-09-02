package data.base;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class RepositoryBase {

    protected Connection conn;

    abstract protected Connection getConnection() throws SQLException;

    abstract protected void returnConnection(Connection conn);
}
