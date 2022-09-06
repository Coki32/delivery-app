package components;

import controllers.OrderController;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

public class CurrentOrderPanel extends JPanel {

    private final OrderController oc;

    public CurrentOrderPanel(OrderController oc) {
        this(oc, null);
    }

    /**
     * @param oc              Order controller used by the main form.
     * @param orderIdOverride Override the current order, testing purposes only.
     */
    public CurrentOrderPanel(OrderController oc, Integer orderIdOverride) {
        this.oc = oc;
        this.setBorder(new CompoundBorder(new EmptyBorder(5, 5, 5, 5), new TitledBorder(new LineBorder(Color.BLACK), "Current order")));
        this.setLayout(new GridLayout(0, 1));
        if (orderIdOverride != null) {
            oc.setCurrentOrder(orderIdOverride);
        }
        if (oc.getCurrentOrder() == null) {
            this.add(new JLabel("<html>No order<br />Start by adding items</html>"));
            return;
        }
        var order = oc.getCurrentOrder();

        var sendBtn = new JButton("Send order");
        var cancelBtn = new JButton("Cancel order");
        sendBtn.addActionListener(this::sendOrder);
        cancelBtn.addActionListener(this::cancelOrder);

        order.getOrderedItems().forEach(oi -> {
            this.add(new JLabel("naruceno:" + oi.getItem().getName()));
        });
        this.add(sendBtn);
        this.add(cancelBtn);
    }

    private void cancelOrder(ActionEvent actionEvent) {

    }

    private void sendOrder(ActionEvent actionEvent) {

    }


}
