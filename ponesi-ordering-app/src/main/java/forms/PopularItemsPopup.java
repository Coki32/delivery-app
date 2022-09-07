package forms;

import data.ConnectionPool;
import util.UIUtilities;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.SQLException;

public class PopularItemsPopup extends JFrame {

    public PopularItemsPopup() {
        super("Popular items");
        var content = new JPanel(new GridLayout(0, 4, 5, 5));
        content.setBorder(new EmptyBorder(10, 10, 10, 10));
        content.add(new JLabel("Item ID"));
        content.add(new JLabel("Item name"));
        content.add(new JLabel("Restaurant"));
        content.add(new JLabel("Times ordered"));

        showPopularItems(content);

        var jsp = new JScrollPane(content);

        this.add(jsp);
        this.pack();
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void showPopularItems(JPanel content) {
        try {
            var conn = ConnectionPool.getInstance().checkOut();

            //              1    2         3            4
            var q = "select id, name, restaurant, times_ordered from item_ordering_frequency ";
            int rowNum = 0;
            try (var ps = conn.prepareStatement(q)) {
                try (var rs = ps.executeQuery()) {
                    while (rs.next()) {
                        content.add(new JLabel(Integer.toString(rs.getInt(1))));
                        content.add(new JLabel(rs.getString(2)));
                        content.add(new JLabel(rs.getString(3)));
                        content.add(new JLabel(Integer.toString(rs.getInt(4))));

                    }
                }
            }

            ConnectionPool.getInstance().checkIn(conn);
        } catch (SQLException e) {
            UIUtilities.msg("Could not load popular items\nMessage:" + e.getMessage(), "Error");
        }
    }
}

class PopularItem extends JPanel {

    public PopularItem(int itemId, String itemName, String restaurantName, int timesOrdered) {
        this.setLayout(new GridLayout(1, 0));

    }

}