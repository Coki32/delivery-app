package data;

import data.base.GenerousRepository;
import data.base.PossessiveRepository;
import entity.Restaurant;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Pretvori sve repositry-je u singletone
 */
public class RestaurantRepository extends GenerousRepository {


    public RestaurantRepository() {

    }

    private List<String> loadCategories(int id) throws SQLException {
        var conn = this.getConnection();
        var result = new ArrayList<String>();
        try (var ps =
                     conn.prepareStatement(
                             "select " +
                                     "name " +
                                     "from category c" +
                                     " join restaurant_has_category rhc on c.id = rhc.category_id " +
                                     "where rhc.restaurant_id = ?")) {
            ps.setInt(1, id);
            try (var rs = ps.executeQuery()) {
                while (rs.next()) {
                    result.add(rs.getString("name"));
                }
            }
        }
        this.returnConnection(conn);
        return result;
    }

    public List<Restaurant> findAll() throws SQLException {
        var conn = this.getConnection();
        var result = new ArrayList<Restaurant>();
        try (var ps = conn.prepareStatement(
                "select id, name, wait_time, address, has_takeout, accepting_orders from restaurant")) {
            try (var rs = ps.executeQuery()) {
                while (rs.next()) {
                    var r = new Restaurant(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("wait_time"),
                            rs.getString("address"),
                            rs.getBoolean("has_takeout"),
                            rs.getBoolean("accepting_orders"),
                            null
                    );
                    r.setCategories(this.loadCategories(r.getId()));
                    result.add(r);
                }
            }
        }
        this.returnConnection(conn);
        return result;
    }

    public Restaurant findOne(int id) throws SQLException {
        var conn = this.getConnection();
        Restaurant result = null;
        try (var ps = conn.prepareStatement(
                "select id, name, wait_time, address, has_takeout, accepting_orders from restaurant where id = ?")) {
            ps.setInt(1, id);
            try (var rs = ps.executeQuery()) {
                if (rs.next()) {
                    result = new Restaurant(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("wait_time"),
                            rs.getString("address"),
                            rs.getBoolean("has_takeout"),
                            rs.getBoolean("accepting_orders"),
                            null
                    );
                    result.setCategories(this.loadCategories(result.getId()));
                }
            }
        }
        this.returnConnection(conn);
        return result;
    }

}
