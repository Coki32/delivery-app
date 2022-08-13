package data;

import entity.Restaurant;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RestaurantRepository  {

    private Connection conn;

    private static final String TABLE = Restaurant.TABLE_NAME;


    public RestaurantRepository(Connection conn) {
        this.conn = conn;
    }

    public boolean delete(Integer id) throws SQLException {
        try (var ps = conn.prepareStatement("DELETE FROM " + TABLE + " where id=?")) {
            ps.setInt(1, id);
            return ps.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public List<Restaurant> findAll() throws SQLException {
        List<Restaurant> result = new ArrayList<>();
        try (var ps = conn.prepareStatement("SELECT * FROM " + TABLE)) {
            var rs = ps.executeQuery();
            while (rs.next()) {
                result.add(new Restaurant(rs));
            }
        }
        return result;
    }

    public Restaurant findById(Integer id) throws SQLException {
        try (var ps = conn.prepareStatement("SELECT * FROM " + TABLE + " WHERE id=?")) {
            ps.setInt(1, id);
            var rs = ps.executeQuery();//ResultSet is closed when its Statement closes
            if (!rs.next())
                return null;
            return new Restaurant(rs);
        }
    }

    public boolean insert(Restaurant entity) throws SQLException {
        String query = "INSERT INTO " + TABLE + " (name, wait_time, address, has_takeout,accepting_orders) values (?, ?, ?, ?, ?)";

        try (var ps = conn.prepareStatement(query)) {
            ps.setString(1, entity.getName());
            ps.setString(2, entity.getWaitTime());
            ps.setString(3, entity.getAddress());
            ps.setBoolean(4, entity.isHasTakeout());
            ps.setBoolean(5, entity.isAcceptingOrders());
            return ps.execute();
        }

    }

    public boolean update(Restaurant entity) throws SQLException {
        String query = "UPDATE "+TABLE+" SET name=? , wait_time=? , address=?, has_takeout=?, accepting_orders=?";
        try(var ps = conn.prepareStatement(query)){
            ps.setString(1,entity.getName());
            ps.setString(2,entity.getWaitTime());
            ps.setString(3, entity.getAddress());
            ps.setBoolean(4,entity.isHasTakeout());
            ps.setBoolean(5, entity.isAcceptingOrders());
            return ps.executeUpdate()==1;
        }

    }
}
