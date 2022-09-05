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

    private static final CompoundBorder border = new CompoundBorder(
            new CompoundBorder(new EmptyBorder(10, 5, 10, 5), new BevelBorder(BevelBorder.RAISED, Color.BLACK, Color.DARK_GRAY)),
            new EmptyBorder(10, 10, 10, 10)
    );

    public ItemSquare(Item item) {
        super(new BorderLayout(10, 10));
        this.setBorder(border);
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

//        this.setMaximumSize(new Dimension(1000, 200));

        var bottom = new JPanel(new BorderLayout(5, 5));
        bottom.add(kind, BorderLayout.PAGE_START);
        bottom.add(from, BorderLayout.PAGE_END);

        this.add(name, BorderLayout.PAGE_START);
        this.add(description, BorderLayout.CENTER);
        this.add(price, BorderLayout.LINE_END);
        this.add(bottom, BorderLayout.PAGE_END);
    }

}
