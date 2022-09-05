package controllers;

import data.ConnectionPool;
import entities.*;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
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


    private Order currentOrder = null;

    private Runnable onChange;

    public OrderController(Runnable onChange) {
        this.onChange = onChange;
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
//        var call = "{call create_order(?, ?, ?, ?, ?)}";
//        try (var cs = conn.prepareCall(call)) {
//            cs.setInt(1, user.getId());
//            cs.setString(2, address);
//            cs.registerOutParameter(3, Types.INTEGER);//pOrderId
//            cs.registerOutParameter(4, Types.BOOLEAN);//pStatus
//            cs.registerOutParameter(5, Types.VARCHAR);//pMsg
//            cs.execute();
//            var pStatus = cs.getBoolean(4);
//            if (pStatus) {
//                return currentOrder = OrderRepository.getInstance().findById(cs.getInt(3));
//            } else {
//                UIUtilities.msg(cs.getString(5), "Error creating order!");
//                return null;
//            }
//        }
        currentOrder = new Order(1, user, null, address, null, null, new ArrayList<>());
        onChange.run();
        return currentOrder;
    }

    public void orderItem(Item item, List<ItemExtra> extras) {
        if (currentOrder != null) {
            currentOrder.getOrderedItems().add(new OrderItem(0, item, null, 1));
            onChange.run();
        }
    }

    public double calculateCost() {
        return 0.0;
    }

    public void sendOrder() {

    }

    public boolean cancelOrder() {
        return false;
    }


    public Order getCurrentOrder() {
        return currentOrder;
    }
}
