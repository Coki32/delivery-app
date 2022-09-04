import components.LockableJPanel;
import components.SelectBox;
import data.RestaurantRepository;
import data.UserRepository;
import entity.Restaurant;
import entity.User;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class Main extends JFrame {

    private UserRepository users = new UserRepository();
    private RestaurantRepository restaurants = new RestaurantRepository();

    JLabel koSi = new JLabel("Nisi niko jos");

    public Main() throws SQLException {
        super("Ponesi food delivery");
        this.setLayout(new BorderLayout(5, 5));
        this.createMenu();

        var userSelector = createUserSelector();
        var restaurantSelector = createRestaurantSelector();
        this.add(userSelector, BorderLayout.LINE_START);
        this.add(restaurantSelector, BorderLayout.LINE_END);
        this.add(koSi, BorderLayout.PAGE_END);
        this.pack();
        this.setVisible(true);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private LockableJPanel<SelectBox<User>> createUserSelector() throws SQLException {
        var sbox = new SelectBox<>("Chose a user", users.findAll(), u -> u.getUsername() + " - " + u.getName());

        sbox.addItemSelectedListener(u -> {
            koSi.setText(u.getUsername());
        });
        koSi.setText(sbox.getCurrentItem().getUsername());
        return new LockableJPanel<>(sbox, "Select a user", box -> {
            System.out.println("Iskljucio si filter!");
        }, box -> {
            System.out.println("Ukljucio si filter!");
        });
    }

    private LockableJPanel<SelectBox<Restaurant>> createRestaurantSelector() throws SQLException {
        var sbox = new SelectBox<>("Chose a restaurant", restaurants.findAll(), Restaurant::getName);

        return new LockableJPanel<>(sbox, "Select a restauratn", box -> {
            System.out.println("Iskljucio si filter!");
        }, box -> {
            System.out.println("Ukljucio si filter!");
        });
    }


    //Just want CTRL+Q
    private void createMenu() {
        JMenuBar jmb = new JMenuBar();
        JMenu jm = new JMenu("File");
        JMenuItem mi = new JMenuItem("Quit");
        mi.setAccelerator(KeyStroke.getKeyStroke('Q', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        mi.addActionListener(event -> {
            System.exit(0);
        });
        jm.add(mi);
        jmb.add(jm);
        this.setJMenuBar(jmb);
    }

    public static void main(String[] args) throws SQLException {
        new Main();
    }
}
