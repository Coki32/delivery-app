package data;


import util.Logger;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class ConnectionPool {

    // Properties file should be named as this class, and placed in the same package as this class.
    // Otherwise, set BUNDLE_NAME explicitly.
    private static final URL BUNDLE_NAME = ConnectionPool.class.getClassLoader().getResource("ConnectionPool.properties");

    private String jdbcURL;
    private String username;
    private String password;
    private int preconnectCount;
    private int maxIdleConnections;
    private int maxConnections;

    private int connectCount;
    private List<Connection> usedConnections;
    private List<Connection> freeConnections;

    private static ConnectionPool instance;

    public static ConnectionPool getInstance() {
        if (instance == null)
            instance = new ConnectionPool();
        return instance;
    }

    private ConnectionPool() {
        readConfiguration();
        try {
            freeConnections = new ArrayList<Connection>();
            usedConnections = new ArrayList<Connection>();

            for (int i = 0; i < preconnectCount; i++) {
                Connection conn = DriverManager.getConnection(jdbcURL, username, password);
                freeConnections.add(conn);
            }
            connectCount = preconnectCount;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readConfiguration() {
        ResourceBundle bundle = null;
        try {
            bundle = new PropertyResourceBundle(BUNDLE_NAME.openStream());
        } catch (IOException ex) {
            System.err.println("No configuration specified!");
            System.exit(1);
        }
        jdbcURL = bundle.getString("jdbcURL");
        username = bundle.getString("username");
        password = bundle.getString("password");
        preconnectCount = 0;
        maxIdleConnections = 10;
        maxConnections = 10;
        try {
            preconnectCount = Integer.parseInt(bundle
                    .getString("preconnectCount"));
            maxIdleConnections = Integer.parseInt(bundle
                    .getString("maxIdleConnections"));
            maxConnections = Integer.parseInt(bundle
                    .getString("maxConnections"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Logger.log(jdbcURL, this);
    }

    public synchronized Connection checkOut() throws SQLException {
        Connection conn = null;
        if (freeConnections.size() > 0) {
            conn = freeConnections.remove(0);
            usedConnections.add(conn);
        } else {
            if (connectCount < maxConnections) {
                conn = DriverManager.getConnection(jdbcURL, username, password);
                usedConnections.add(conn);
                connectCount++;
            } else {
                try {
                    wait();
                    conn = freeConnections.remove(0);
                    usedConnections.add(conn);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        Logger.log("Connection taken out!", this);
        return conn;
    }

    public synchronized void checkIn(Connection conn) {
        if (conn == null)
            return;
        if (usedConnections.remove(conn)) {
            freeConnections.add(conn);
            Logger.log("Connection returned out!", this);
            while (freeConnections.size() > maxIdleConnections) {
                int lastOne = freeConnections.size() - 1;
                Connection c = freeConnections.remove(lastOne);
                try {
                    c.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            notify();
        }
    }

}
