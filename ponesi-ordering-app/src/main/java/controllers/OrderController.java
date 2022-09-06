package controllers;

import data.ConnectionPool;
import data.OrderRepository;
import entities.*;
import forms.SentOrderPopup;
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

    private final MainController mc;
    private final Runnable onChange;

    private Order currentOrder = null;


    public OrderController(MainController mc, Runnable onChange) {
        this.onChange = onChange;
        this.mc = mc;
    }

    /**
     * @return Newly created order without Address or Payment
     */
    public Order createOrder() throws SQLException {
        /**
         * create procedure create_order(in pUserId int, out pOrderId int, out pStatus bool, out pMsg varchar(255))
         */
        var call = "{call create_order(?, ?, ?, ?)}";
        try (var cs = conn.prepareCall(call)) {
            cs.setInt(1, mc.getCurrentUser().getId());
            cs.registerOutParameter(2, Types.INTEGER);//pOrderId
            cs.registerOutParameter(3, Types.BOOLEAN);//pStatus
            cs.registerOutParameter(4, Types.VARCHAR);//pMsg
            cs.execute();
            var pStatus = cs.getBoolean(3);
            if (pStatus) {
                currentOrder = OrderRepository.getInstance().findById(cs.getInt(2));
                onChange.run();
            } else {
                UIUtilities.msg(cs.getString(5), "Error creating order!");
                return null;
            }
        }
        return currentOrder;
    }

    public void orderItem(Item item, List<OrderExtra> extras) throws SQLException {
        /**
         * create procedure add_order_item_extra(in pOrderId int, in pOrderItemId int, in pItemExtraId int, in pExtraQuantity int,
         *                                       out pStatus bool, out pMsg varchar(255))
         * <p>
         * AND
         * <p>
         * create procedure add_order_item_extra(in pOrderItemId int, in pItemExtraId int, in pExtraQuantity int,
         *                                       out pStatus bool, out pMsg varchar(255))
         */
        if (currentOrder == null) {//if there's no order and you're adding the first item
            createOrder();//create the order
        }
        var call = "{call add_order_item(?, ?, ?, ?, ? )}";
        try (var cs = conn.prepareCall(call)) {
            cs.setInt(1, currentOrder.getId());
            cs.setInt(2, item.getId());
            cs.registerOutParameter(3, Types.INTEGER);//pOrderItemId
            cs.registerOutParameter(4, Types.BOOLEAN);//pStatus
            cs.registerOutParameter(5, Types.VARCHAR);//pMsg
            cs.execute();
            var status = cs.getBoolean(4);
            if (status) {
                var orderedItemId = cs.getInt(3);
                var add_extra_call = "{call add_order_item_extra(?, ?, ?, ?, ?)}";
                try (var extra_cs = conn.prepareCall(add_extra_call)) {
                    extra_cs.setInt(1, orderedItemId);//stays fixed
                    extra_cs.registerOutParameter(4, Types.BOOLEAN);//pStatus
                    extra_cs.registerOutParameter(5, Types.VARCHAR);//pMsg
                    for (var extra : extras) {//add each extra
                        extra_cs.setInt(2, extra.getId());
                        extra_cs.setInt(3, extra.getQuantity());
                        extra_cs.execute();
                        var success = extra_cs.getBoolean(4);
                        var msg = extra_cs.getString(5);
                        if (!success)
                            UIUtilities.msg(
                                    "Could not add " +
                                            extra.getName() +
                                            " to " + item.getName() +
                                            ". You won't be charged for it.\n"
                                            + "Reason:" + msg,
                                    "Extra adding failed");
                    }
                }
            }
        }
        currentOrder = OrderRepository.getInstance().findById(currentOrder.getId());
        onChange.run();
    }

    public static double calculateCost(int orderId) throws SQLException {
        var call = "{ call calculate_order_total(?, ?)}";
        try (var cs = conn.prepareCall(call)) {
            cs.setInt(1, orderId);
            cs.registerOutParameter(2, Types.DECIMAL);
            cs.execute();
            return cs.getDouble(2);
        }
    }

    public double calculateCost() throws SQLException {
        return OrderController.calculateCost(currentOrder.getId());
    }

    public boolean addPaymentMethod(Payment payment) throws SQLException {
        /**
         *                                          1                      2                                  3
         * create procedure add_payment_method(in pOrderId int, in pPaymentType enum ('cash','card'), in pCardId int,
         *                                     in pCashAmount decimal(10, 2), out pStatus bool, out pMsg varchar(255))
         *                                          4                               5                 6
         */
        var call = "{call add_payment_method(?, ?, ?, ?, ?, ?)}";
        try (var cs = conn.prepareCall(call)) {
            cs.setInt(1, currentOrder.getId());
            cs.setString(2, payment instanceof CashPayment ? "cash" : "card");
            cs.setInt(3, payment instanceof CashPayment ? -1 : ((CreditCardPayment) payment).getCreditCard().getId());
            cs.setDouble(4, payment instanceof CashPayment ? ((CashPayment) payment).getUserHasAmount() : 0);
            cs.registerOutParameter(5, Types.BOOLEAN);//status
            cs.registerOutParameter(6, Types.VARCHAR);//msg
            cs.execute();
            var success = cs.getBoolean(5);
            if (!success) {
                UIUtilities.msg(cs.getString(6), "Could not process your payment");
                return false;
            }
        }
        return true;
    }

    /**
     * @param address Delivery address. Pass null to use user's default address.
     * @throws SQLException
     */
    public boolean sendOrder(String address) throws SQLException {
        /**
         *                                  1                   2                               3                4
         * create procedure send_order(in pOrderId int, in pAddressOverride varchar(100), out pStatus bool, out pMsg varchar(255))
         */
        var call = "{call send_order(?, ?, ?, ?)}";
        try (var cs = conn.prepareCall(call)) {
            cs.setInt(1, currentOrder.getId());
            cs.setString(2, address);
            cs.registerOutParameter(3, Types.BOOLEAN);//status
            cs.registerOutParameter(4, Types.VARCHAR);//msg
            cs.execute();
            var success = cs.getBoolean(3);
            if (!success) {
                UIUtilities.msg(cs.getString(4), "Could not send order");
                return false;
            }
        }
        onChange.run();
        return true;
    }

    public boolean cancelOrder() throws SQLException {
        if (currentOrder == null)
            return true;
        var orderItemIds = currentOrder.getOrderedItems().stream().mapToInt(OrderItem::getId).toArray();
        //Delete extras first
        var deleteExtras = "delete from order_item_has_item_extra where order_item_id = ?";
        var deleteOrderItem = "delete from order_item where id = ?";
        var deleteOrder = "delete from `order` where id = ?";
        for (var i : orderItemIds) {
            try (var ps = conn.prepareStatement(deleteExtras)) {
                ps.setInt(1, i);
                ps.executeUpdate();
            }
            try (var ps = conn.prepareStatement(deleteOrderItem)) {
                ps.setInt(1, i);
                ps.executeUpdate();
            }
        }
        try (var ps = conn.prepareStatement(deleteOrder)) {
            ps.setInt(1, currentOrder.getId());
            ps.executeUpdate();
        }
        this.currentOrder = null;
        onChange.run();
        return true;
    }


    public Order getCurrentOrder() {
        return currentOrder;
    }

    public void setCurrentOrder(Order order) {
        this.currentOrder = order;
        onChange.run();
    }

    /**
     * Testing purposes only, should be unused by the end of it.
     *
     * @param orderIdOverride
     */
    public void setCurrentOrder(Integer orderIdOverride) {
        try {
            this.currentOrder = OrderRepository.getInstance().findById(orderIdOverride);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Moves current active order to a new window and clears current order
    public void finishOrder() throws SQLException {
        new SentOrderPopup(currentOrder);
        currentOrder = null;
        onChange.run();
    }
}
