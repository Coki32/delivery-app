package controller;

import data.ConnectionPool;
import data.OrderRepository;
import entity.Item;
import entity.ItemExtra;
import entity.Order;
import entity.User;
import util.UIUtilities;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

public class OrderController {

    private static final Connection conn;

    static {
        try {
            conn = ConnectionPool.getInstance().checkOut();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Could not get a database connection for OrderController", "Connection issue", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException(e);
        }
    }

    public OrderController() {

    }

    /**
     * @param user    NotNull. Must specify a user.
     * @param address If null then user's default address is used.
     * @return
     */
    public Order createOrder(User user, String address) throws SQLException {
        /**
         * create procedure create_order(in pUserId int, in pAddressOverride varchar(100),
         *                               out pOrderId int, out pStatus bool, out pMsg varchar(255))
         */
        var call = "{call create_order(?, ?, ?, ?, ?)}";
        try (var cs = conn.prepareCall(call)) {
            cs.setInt(1, user.getId());
            cs.setString(2, address);
            cs.registerOutParameter(3, Types.INTEGER);//pOrderId
            cs.registerOutParameter(4, Types.BOOLEAN);//pStatus
            cs.registerOutParameter(5, Types.VARCHAR);//pMsg
            cs.execute();
            var pStatus = cs.getBoolean(4);
            if (pStatus) {
                return OrderRepository.getInstance().findById(cs.getInt(3));
            } else {
                UIUtilities.msg(cs.getString(5), "Error creating order!");
                return null;
            }
        }
    }

    public void orderItem(Item item, List<ItemExtra> extras) {

    }

    public double calculateCost(Order order) {
        return 0.0;
    }

    public void sendOrder(Order order) {

    }

    public boolean cancelOrder(Order o) {
        return false;
    }

}
