package components;

import controllers.OrderController;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class CurrentOrderPanel extends JPanel {

    private final OrderController oc;

    public CurrentOrderPanel(OrderController oc) {
        this.oc = oc;
        this.setBorder(new CompoundBorder(new EmptyBorder(5, 5, 5, 5), new TitledBorder(new LineBorder(Color.BLACK), "Current order")));
        this.setLayout(new GridLayout(0, 1));
        if (oc.getCurrentOrder() == null) {
            this.add(new JLabel("<html>No order<br />Start by adding items</html>"));
            return;
        }
        var order = oc.getCurrentOrder();
        order.getOrderedItems().forEach(oi -> {
            this.add(new JLabel("naruceno:" + oi.getItem().getName()));
        });

    }


}
