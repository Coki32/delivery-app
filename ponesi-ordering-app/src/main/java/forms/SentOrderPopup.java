package forms;

import components.OrderView;
import entities.Order;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.util.Random;

public class SentOrderPopup extends JFrame {

    private Random rand = new Random();
    private Order order;


    public SentOrderPopup(Order order) {
        super(String.format("Order #%d from %s", order.getId(), order.getOrderedItems().get(0).getItem().getRestaurant().getName()));
        this.order = order;
        var content = new JPanel();
        content.setBorder(new EmptyBorder(10, 10, 10, 10));

        content.add(new OrderView(order, true));
        this.add(content);
        this.pack();
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }


}
