package components;

import entity.Item;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ItemSquare extends JPanel {

    private static final float BIG_FONT_SCALE = 1.2f;
    private static final float LIL_FONT_SCALE = 1.1f;

    public ItemSquare(Item item) {
        this.setBorder(new CompoundBorder(new BevelBorder(BevelBorder.RAISED, Color.BLACK, Color.DARK_GRAY), new EmptyBorder(5, 5, 5, 5)));
        var name = new JLabel(item.getName());
        var description = new JTextArea(item.getDescription());
        var price = new JLabel(String.format("%.2f KM", item.getPrice()));
        var kind = new JLabel(item.getKind());
        var from = new JLabel(item.getRestaurant() == null ? "Nepoznanto" : item.getRestaurant().getName());
        var bigFont = name.getFont().deriveFont(name.getFont().getSize() * BIG_FONT_SCALE);
        var lilFont = name.getFont().deriveFont(name.getFont().getSize() * LIL_FONT_SCALE);
        bigFont = bigFont.deriveFont(Font.BOLD);
        lilFont = lilFont.deriveFont(Font.ITALIC);
        name.setFont(bigFont);
        price.setFont(bigFont);
        kind.setFont(lilFont);
        from.setFont(lilFont);
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        description.setEditable(false);

        this.setLayout(new BorderLayout(10, 20));

        this.setMinimumSize(new Dimension(300, 150));
        this.setMaximumSize(new Dimension(300, 800));

        var bottom = new JPanel(new BorderLayout(10, 5));
        bottom.add(kind, BorderLayout.PAGE_START);
        bottom.add(from, BorderLayout.PAGE_END);

        this.add(name, BorderLayout.PAGE_START);
        this.add(description, BorderLayout.CENTER);
        this.add(price, BorderLayout.LINE_END);
        this.add(bottom, BorderLayout.PAGE_END);
    }

}
