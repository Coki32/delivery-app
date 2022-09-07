package components;

import controllers.OrderController;
import data.OrderRepository;
import entities.Order;
import entities.OrderItem;
import entities.OrderStatus;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class OrderView extends JPanel {

    private static final int PERIOD = 10 * 1000;
    private Order order;
    private JLabel status;

    public OrderView(Order order) {
        this(order, false);
    }


    public OrderView(Order order, boolean shouldRefresh) {
        this.setLayout(new GridLayout(0, 1, 5, 5));
        this.order = order;
        this.add(new JLabel(String.format("Order #%d", order.getId())));
        this.add(new JLabel("Order from: " + this.getRestaurant()));
        for (var item : order.getOrderedItems()) {
            this.add(itemBox(item));
        }
        try {
            this.add(new JLabel("Total price: " + OrderController.calculateCost(order.getId()) + " KM"));
        } catch (SQLException e) {
            this.add(new JLabel("Total price: unknown"));
        }
        if (shouldRefresh) {//I wanted to use a timer, I really did. But it's terrible. Sleepy thread it is.
            new Thread(() -> {
                try {
                    refresh();
                } catch (InterruptedException | SQLException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }
    }

    private void refresh() throws InterruptedException, SQLException {
        OrderStatus currentStatus = OrderRepository.getInstance().getOrderStatus(order);
        final String stat = currentStatus.getStatus();
        SwingUtilities.invokeLater(() -> {
            if (status != null)
                this.remove(status);
            this.status = new JLabel("Status: " + stat);
            this.add(status);
            this.validate();
        });
        do {
            System.out.println("Bio:" + currentStatus.getStatus());
            var prev = currentStatus;
            Thread.sleep(PERIOD);
            currentStatus = OrderRepository.getInstance().getOrderStatus(order);
            System.out.println("Postao:" + currentStatus.getStatus());
            if (prev != currentStatus) {
                final String newStat = currentStatus.getStatus();
                SwingUtilities.invokeLater(() -> {
                    if (status != null)
                        this.remove(status);
                    this.status = new JLabel("Status: " + newStat);
                    this.add(status);
                    this.validate();
                });
            }
        } while (this.isVisible());

    }

    private JPanel itemBox(OrderItem oi) {
        var box = new JPanel(new BorderLayout(5, 5));
        var extraBox = new JPanel(new GridLayout(0, 1, 5, 5));
        var itemName = new JLabel(oi.getQuantity() + "x " + oi.getItem().getName());
        itemName.setFont(itemName.getFont().deriveFont(Font.BOLD));
        box.add(itemName, BorderLayout.PAGE_START);

        var extraSize = oi.getExtras().size();
        for (int i = 0; i < extraSize; i++) {
            var extra = oi.getExtras().get(i);
            extraBox.add(new JLabel((i == extraSize - 1 ? "  ╚" : "  ╠") + extra.getQuantity() + "x " + extra.getName()));
        }
        box.add(extraBox);
        return box;
    }

    private String getRestaurant() {
        //Ifs are broken into two because it got out of hand with order.getOrderedItems().get(0).getRestaurant().getName() and the null checks
        if (order != null && order.getOrderedItems() != null && order.getOrderedItems().size() > 0) {
            var candidate = order.getOrderedItems().get(0).getItem();
            if (candidate != null && candidate.getRestaurant() != null && candidate.getRestaurant().getName() != null) {
                return candidate.getRestaurant().getName();
            }
        }
        return "Unknown";
    }

}
