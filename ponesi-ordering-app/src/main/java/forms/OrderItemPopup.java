package forms;

import components.ItemExtraGroupView;
import controllers.OrderController;
import data.ItemRepository;
import entities.Item;
import entities.ItemExtra;

import javax.swing.*;
import java.sql.SQLException;
import java.util.stream.Collectors;

public class OrderItemPopup extends JFrame {

    private final Item item;
    private final OrderController oc;

    public OrderItemPopup(Item item, OrderController oc) throws SQLException {
        super("Adding " + item.getName());
        this.item = item.getExtras() == null ? ItemRepository.getInstance().findById(item.getId()) : item;
        this.oc = oc;

        var grouped = item.getExtras().stream().collect(Collectors.groupingBy(ItemExtra::getGroup));
        grouped.forEach((group, extras) -> {
            this.add(new ItemExtraGroupView(group, extras));
        });

        this.pack();
        this.setVisible(true);
    }
}
