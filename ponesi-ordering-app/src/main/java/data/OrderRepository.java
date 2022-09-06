package data;

import data.base.PossessiveRepository;
import entities.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderRepository extends PossessiveRepository {

    private static OrderRepository instance;

    private OrderRepository() {

    }

    public static synchronized OrderRepository getInstance() {
        if (instance == null)
            instance = new OrderRepository();
        return instance;
    }

    public Order findById(int id) throws SQLException {
        var conn = this.getConnection();
        Order order = null;
        var query = "select u.id as u_id, u.name as u_name, u.address as u_address, u.username as u_uname, u.email as u_email, u.password as u_password," +
                "o.order_address as order_address," +
                "dt.id as ot_id, dt.name as ot_name," +
                "c.id as c_id, c.name as c_name " +
                "from `order` o " +
                "join user u on o.user_id = u.id " +
                "join courier c on o.courier_id = c.id " +
                "join delivery_type dt on o.delivery_type_id = dt.id " +
                "where o.id = ?";
        try (var ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            try (var rs = ps.executeQuery()) {
                if (rs.next()) {
                    //procitaj narudzbu
                    order = new Order(id,
                            new User(rs.getInt("u_id"), rs.getString("u_uname"), rs.getString("u_email"), rs.getString("u_address"), rs.getString("u_name"), rs.getString("u_password")),
                            null,
                            rs.getString("order_address"),
                            new OrderType(rs.getInt("ot_id"), rs.getString("ot_name")),
                            new Courier(rs.getInt("c_id"), rs.getString("c_name")),
                            null
                    );
                    order.setOrderedItems(this.loadOrderedItems(order.getId()));
                }
            }
        }

        this.returnConnection(conn);
        return order;
    }

    private List<OrderItem> loadOrderedItems(int id) throws SQLException {
        var conn = this.getConnection();
        List<OrderItem> result = new ArrayList<>();
        var query = "select " +
                "oi.id as oi_id," +
                "oi.item_id as oi_item_id," +
                "oi.quantity as oi_quantity," +
                "oi.ordered_item_price as oi_ordered_price," +
                "i.id as i_id," +
                "i.name as i_name," +
                "i.description as i_description," +
                "i.price as i_price," +
                "ik.name as i_kind," +
                "r.id as i_r_id," +
                "r.name as i_r_name" +
                " from order_item oi " +
                " join item i on oi.item_id = i.id " +
                " join item_kind ik on i.item_kind_id = ik.id" +
                " join restaurant r on i.restaurant_id = r.id" +
                " where oi.order_id = ?";
        try (var ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            try (var rs = ps.executeQuery()) {
                while (rs.next()) {
                    var oi = new OrderItem(rs.getInt("oi_id"),
                            new Item(rs.getInt("i_id"), rs.getString("i_name"), rs.getString("i_description"), rs.getDouble("i_price"), rs.getString("i_kind"), null,
                                    new Restaurant(rs.getInt("i_r_id"), rs.getString("i_r_name"), null, null, false, false, null)),
                            null,
                            rs.getInt("oi_quantity"));
                    oi.setExtras(loadExtras(oi));
                    result.add(oi);
                }
            }
        }
        this.returnConnection(conn);
        return result;
    }

    private List<OrderExtra> loadExtras(OrderItem oi) throws SQLException {
        var conn = this.getConnection();
        var result = new ArrayList<OrderExtra>();
        var query = "select " +
                "oihie.ordered_extra_price as extra_price," +
                "oihie.ordered_extra_quantity as extra_quantity," +
                "ie.name as extra_name," +
                "ie.id as extra_id," +
                "eg.id as ieg_id," +
                "eg.name as ieg_name," +
                "eg.min_choices as ieg_min," +
                "eg.max_choices as ieg_max" +
                " from order_item_has_item_extra oihie" +
                " join item_extra ie on ie.id = oihie.item_extra_id" +
                " join extra_group eg on eg.id = ie.extra_group_id" +
                " where oihie.order_item_id = ?";
        try (var ps = conn.prepareStatement(query)) {
            ps.setInt(1, oi.getId());
            try (var rs = ps.executeQuery()) {
                while (rs.next()) {
                    var extra = new OrderExtra(rs.getInt("extra_id"),
                            new ItemExtraGroup(rs.getInt("ieg_id"), rs.getString("ieg_name"), rs.getInt("ieg_max"), rs.getInt("ieg_min")),
                            null,
                            rs.getString("extra_name"), rs.getDouble("extra_price"), rs.getInt("extra_quantity"));
                    result.add(extra);
                }
            }
        }
        this.returnConnection(conn);
        return result;
    }

    //Utility, just for updating thread to have something to go by
    public List<OrderStatus> getAllStatuses() throws SQLException {
        var conn = this.getConnection();
        var result = new ArrayList<OrderStatus>();
        var query = "select " +
                "os.id as id," +
                "os.status as status " +
                "from  order_status os order by os.id ASC ";
        try (var ps = conn.prepareStatement(query)) {
            try (var rs = ps.executeQuery()) {
                while (rs.next()) {
                    result.add(new OrderStatus(rs.getInt("id"), rs.getString("status"), null));
                }
            }
        }
        this.returnConnection(conn);
        return result;
    }

    public OrderStatus getOrderStatus(Order order) throws SQLException {
        var conn = this.getConnection();
        OrderStatus result = null;
        var query = "select " +
                "os.id as id," +
                "os.status as status," +
                "ohos.timestamp as ts " +
                "from order_has_order_status ohos " +
                "join order_status os on ohos.order_status_id = os.id " +
                "where ohos.order_id = ? " +
                "order by ohos.timestamp DESC " +
                "limit 1";
        try (var ps = conn.prepareStatement(query)) {
            ps.setInt(1, order.getId());
            try (var rs = ps.executeQuery()) {
                if (rs.next()) {
                    result = new OrderStatus(rs.getInt("id"), rs.getString("status"), rs.getTimestamp("ts"));
                }
            }
        }
        this.returnConnection(conn);
        return result;
    }

}
