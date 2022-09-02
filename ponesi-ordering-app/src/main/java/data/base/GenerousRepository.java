package data.base;

import data.ConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;

public class GenerousRepository extends RepositoryBase {
    @Override
    protected synchronized Connection getConnection() throws SQLException {
        return ConnectionPool.getInstance().checkOut();
    }

    @Override
    protected synchronized void returnConnection(Connection conn) {
        ConnectionPool.getInstance().checkIn(conn);
    }
}
