package components;

import entities.ItemExtra;
import entities.ItemExtraGroup;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class ItemExtraGroupView extends JPanel {


    public ItemExtraGroupView(ItemExtraGroup group, java.util.List<ItemExtra> extras) {
        this.setLayout(new GridLayout(0, 1, 5, 5));
        this.setBorder(new TitledBorder(new LineBorder(Color.BLACK), group.getName()));

        extras.forEach(ex -> {
            this.add(new ExtraAdder(ex));
        });

    }
}

class ExtraAdder extends JPanel {

    private int quantity = 0;
    private JLabel label;
    private ItemExtra extra;

    public ExtraAdder(ItemExtra extra) {
        this.setLayout(new BorderLayout(5, 5));
        this.extra = extra;
        var increase = new JButton("+1");
        var decrease = new JButton("-1");
        var buttonFont = increase.getFont().deriveFont(increase.getFont().getSize() * 2.0f);
        increase.setFont(buttonFont);
        decrease.setFont(buttonFont);
        increase.addActionListener(e -> modifyQuantity(1));
        decrease.addActionListener(e -> modifyQuantity(-11));
        label = new JLabel(extra.getName());

        this.add(decrease, BorderLayout.LINE_START);
        this.add(label, BorderLayout.CENTER);
        this.add(increase, BorderLayout.LINE_END);

    }

    private void modifyQuantity(int i) {
        quantity += i;
        label.setText(extra.getName() + (quantity == 0 ? "" : (" x" + quantity)));
    }


}