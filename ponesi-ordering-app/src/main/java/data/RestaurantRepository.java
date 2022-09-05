package data;

import data.base.PossessiveRepository;
import entities.ItemKind;
import entities.Restaurant;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO: Pretvori sve repositry-je u singletone
 */
public class RestaurantRepository extends PossessiveRepository {


    private static RestaurantRepository instance;

    private RestaurantRepository() {

    }

    public static synchronized RestaurantRepository getInstance() {
        if (instance == null)
            instance = new RestaurantRepository();
        return instance;
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


    /**
     * @param r Optional, if specified finds only kinds for that restaurant
     * @return
     * @throws SQLException
     */
    public List<ItemKind> findItemKinds(Restaurant r) throws SQLException {
        var conn = this.getConnection();
        var result = new ArrayList<ItemKind>();
        var query = "select " +
                "distinct ik.name, ik.id " +
                "from item_kind ik " +
                "join item i on ik.id = i.item_kind_id " +
                "join restaurant r on i.restaurant_id = r.id ";
//                "where r.id = ?";
        if (r != null) {
            query += " where r.id = ?";
        }
        try (var ps = conn.prepareStatement(query)) {
            if (r != null)
                ps.setInt(1, r.getId());
            try (var rs = ps.executeQuery()) {
                while (rs.next()) {
                    result.add(new ItemKind(rs.getInt("id"), rs.getString("name")));
                }
            }
        }
        this.returnConnection(conn);
        return result;
    }

}
