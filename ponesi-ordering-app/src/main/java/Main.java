import com.formdev.flatlaf.FlatIntelliJLaf;
import components.ItemSquare;
import components.LockableJPanel;
import components.SelectBox;
import data.ItemRepository;
import data.RestaurantRepository;
import data.UserRepository;
import entity.Item;
import entity.Restaurant;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.SQLException;

public class Main extends JFrame {

    private UserRepository users = new UserRepository();
    private RestaurantRepository restaurants = new RestaurantRepository();
    private ItemRepository items = new ItemRepository();

    private Restaurant selectedRestaurant = null;
    JLabel koSi = new JLabel("Nisi niko jos");

    private JScrollPane itemPane = null;


    public Main() throws SQLException {
        super("Ponesi food delivery");
        this.setLayout(new BorderLayout(5, 5));
        this.createMenu();

        var top = new JPanel(new BorderLayout(5, 5));
        var userSelector = createUserSelector();
        var restaurantSelector = createRestaurantSelector();
        top.add(userSelector, BorderLayout.LINE_START);
        top.add(restaurantSelector, BorderLayout.LINE_END);
        top.add(koSi, BorderLayout.PAGE_END);

        this.add(top, BorderLayout.PAGE_START);

        this.displayItems(loadItems());

        this.setMaximumSize(new Dimension(500, 480));
        this.pack();
        this.setVisible(true);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void selectRestaurant(Restaurant r) {
        this.selectedRestaurant = r;
        try {
            displayItems(loadItems());
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error loading items!", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void displayItems(java.util.List<Item> currentItems) {

        var itemHolder = new JPanel();
        currentItems.forEach(i -> itemHolder.add(new ItemSquare(i)));
        itemHolder.setPreferredSize(new Dimension(300, 10000));

        var jsp = new JScrollPane(itemHolder);
        jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        jsp.setAutoscrolls(true);

        if (itemPane != null) this.remove(itemPane);

        this.add(jsp, BorderLayout.CENTER);
        this.validate();
        itemPane = jsp;
    }

    private java.util.List<Item> loadItems() throws SQLException {
        if (selectedRestaurant == null) return this.items.findAll();
        else return this.items.findAllByRestaurant(selectedRestaurant);
    }

    private JPanel createUserSelector() throws SQLException {
        var sbox = new SelectBox<>("Choose a user", users.findAll(), u -> u.getUsername() + " - " + u.getName());

        sbox.addItemSelectedListener(u -> {
            koSi.setText(u.getUsername());
        });
        koSi.setText(sbox.getCurrentItem().getUsername());
        var wrapper = new JPanel(new BorderLayout());
        wrapper.setBorder(new EmptyBorder(20, 0, 20, 0));
        wrapper.add(sbox, BorderLayout.CENTER);
        return wrapper;
    }

    private LockableJPanel<SelectBox<Restaurant>> createRestaurantSelector() throws SQLException {
        var sbox = new SelectBox<>("Choose a restaurant", restaurants.findAll(), Restaurant::getName);
        sbox.addItemSelectedListener(this::selectRestaurant);
        return new LockableJPanel<>(sbox, "Select a restaurant", true, box -> {
            selectRestaurant(null);
        }, box -> {
            selectRestaurant(box.getCurrentItem());
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
        FlatIntelliJLaf.setup();
        UIManager.put("Button.arc", 10);
        UIManager.put("Component.arc", 10);
        UIManager.put("ProgressBar.arc", 10);
        UIManager.put("TextComponent.arc", 10);
        System.setProperty("flatlaf.uiScale", "1.5");
        new Main();
    }
}
