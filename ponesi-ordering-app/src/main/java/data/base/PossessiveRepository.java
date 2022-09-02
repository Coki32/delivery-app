package data.base;

import data.ConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;

public class PossessiveRepository extends RepositoryBase {

    @Override
    protected synchronized Connection getConnection() throws SQLException {
        return conn != null ? conn : (conn = ConnectionPool.getInstance().checkOut());
    }

    @Override
    public void returnConnection(Connection conn) {
        //no-op, possessive never returns it's connection back, only consumes one.
    }
}
