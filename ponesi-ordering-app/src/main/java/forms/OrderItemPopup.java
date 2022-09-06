package forms;

import components.ItemExtraGroupView;
import controllers.OrderController;
import data.ItemRepository;
import entities.Item;
import entities.ItemExtra;
import util.UIUtilities;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class OrderItemPopup extends JFrame {
    private final Executor executor = Executors.newSingleThreadExecutor();

    private final Item item;
    private final OrderController oc;

    private List<ItemExtraGroupView> groups = new ArrayList<>();

    public OrderItemPopup(Item item, OrderController oc) throws SQLException {
        super("Adding " + item.getName());
        this.item = item.getExtras() == null ? ItemRepository.getInstance().findById(item.getId()) : item;
        this.oc = oc;


        var panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        var name = new JLabel(item.getName());
        var price = new JLabel(String.format("%.2f KM", item.getPrice()));

        var addBtn = new JButton("Add");
        var cancelBtn = new JButton("Cancel");
        addBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        cancelBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        addBtn.addActionListener(this::addItem);
        cancelBtn.addActionListener(this::cancel);

        Font bigFont = name.getFont().deriveFont(name.getFont().getSize() * 2.0f);

        name.setFont(bigFont);
        name.setAlignmentX(Component.LEFT_ALIGNMENT);
        price.setFont(bigFont);
        price.setAlignmentX(Component.LEFT_ALIGNMENT);


        panel.add(name);
        panel.add(price);
        if (item.getExtras() != null && item.getExtras().size() > 0) {
            var grouped = item.getExtras().stream().collect(Collectors.groupingBy(ItemExtra::getGroup));
            grouped.forEach((group, extras) -> {
                var ieg = new ItemExtraGroupView(group, extras);
                ieg.setAlignmentX(Component.LEFT_ALIGNMENT);
                groups.add(ieg);
                panel.add(ieg);
            });
        } else {
            panel.add(new JLabel("No extras for item"));
        }
        panel.add(addBtn);
        panel.add(cancelBtn);
        this.add(panel);
        this.pack();
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void cancel(ActionEvent actionEvent) {
        this.setVisible(false);
        this.dispose();
    }

    private void addItem(ActionEvent actionEvent) {
        executor.execute(() -> {
            try {
                oc.orderItem(item, groups.stream().map(ItemExtraGroupView::getExtrasForGroup).reduce((l1, l2) -> {
                    var result = new ArrayList<>(l1);
                    result.addAll(l2);
                    return result;
                }).orElse(new ArrayList<>()));
                SwingUtilities.invokeLater(() -> {
                    this.setVisible(false);
                    this.dispose();
                });
            } catch (SQLException e) {
                UIUtilities.msg("Error:" + e.getMessage(), "Could not add item to order");
            }
        });
    }
}
