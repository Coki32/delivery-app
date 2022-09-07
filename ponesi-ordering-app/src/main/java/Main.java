import com.formdev.flatlaf.FlatDarculaLaf;
import components.CurrentOrderPanel;
import components.ItemFiltersBar;
import components.ItemSquare;
import controllers.MainController;
import controllers.OrderController;
import forms.AddCreditCard;
import forms.PopularItemsPopup;
import forms.UserCreate;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Main extends JFrame {
    private final Executor executor = Executors.newCachedThreadPool();

    private final MainController mc;
    private OrderController oc;

    JLabel koSi = new JLabel("Nisi niko jos");

    private JScrollPane itemPane = null;
    private JPanel orderPanel = null;

    private JPanel top;


    public Main() throws SQLException {
        super("Ponesi food delivery");
        this.setLayout(new BorderLayout(5, 5));
        this.mc = new MainController(_void -> displayItems());

        this.oc = new OrderController(mc, () -> {
            if (orderPanel != null)
                this.remove(orderPanel);
            this.add((orderPanel = new CurrentOrderPanel(oc)), BorderLayout.LINE_END);
            this.validate();
        });
        orderPanel = new CurrentOrderPanel(oc);
        this.createMenu();

        top = new ItemFiltersBar(mc);

        this.add(top, BorderLayout.PAGE_START);

        this.displayItems();
        this.add(orderPanel, BorderLayout.LINE_END);
        this.pack();
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    private void displayItems() {
        executor.execute(() -> {
            var itemHolder = new JPanel();
            itemHolder.setLayout(new BoxLayout(itemHolder, BoxLayout.Y_AXIS));
            mc.getItems().forEach(i -> itemHolder.add(new ItemSquare(i, oc)));

            int height = Arrays.stream(itemHolder.getComponents()).mapToInt(c -> c.getPreferredSize().height + 20).sum();
            itemHolder.setPreferredSize(new Dimension(300, height));

            var jsp = new JScrollPane(itemHolder);
            SwingUtilities.invokeLater(() -> {
                if (itemPane != null) this.remove(itemPane);

                this.add(jsp, BorderLayout.CENTER);
                this.validate();
                itemPane = jsp;
            });
        });

    }


    //Just want CTRL+Q
    private void createMenu() {
        JMenuBar jmb = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenu users = new JMenu("Users");

        JMenuItem quitItem = new JMenuItem("Quit");
        quitItem.setAccelerator(KeyStroke.getKeyStroke('Q', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        quitItem.addActionListener(event -> {
            System.exit(0);
        });

        JMenuItem refreshItem = new JMenuItem("Refresh");
        refreshItem.setAccelerator(KeyStroke.getKeyStroke('R', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        refreshItem.addActionListener(event -> {
            if (top != null)
                this.remove(top);
            top = new ItemFiltersBar(mc);
            this.add(top, BorderLayout.PAGE_START);
            this.validate();
        });

        JMenuItem userAdd = new JMenuItem("Create a new user");
        userAdd.setAccelerator(KeyStroke.getKeyStroke('N', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        userAdd.addActionListener(e -> {
            new UserCreate();
        });

        JMenuItem creditCardAdd = new JMenuItem("Add users credit card");
        creditCardAdd.setAccelerator(KeyStroke.getKeyStroke('M', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        creditCardAdd.addActionListener(e -> {
            new AddCreditCard();
        });

        JMenuItem popularItems = new JMenuItem("Show popular items");
        popularItems.setAccelerator(KeyStroke.getKeyStroke('P', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        popularItems.addActionListener(e -> {
            new PopularItemsPopup();
        });

        file.add(quitItem);
        file.add(refreshItem);
        file.add(popularItems);

        users.add(userAdd);
        users.add(creditCardAdd);

        jmb.add(file);
        jmb.add(users);
        this.setJMenuBar(jmb);
    }

    public static void main(String[] args) throws SQLException {
        FlatDarculaLaf.setup();
        UIManager.put("Button.arc", 10);
        UIManager.put("Component.arc", 10);
        UIManager.put("ProgressBar.arc", 10);
        UIManager.put("TextComponent.arc", 10);
        System.setProperty("flatlaf.uiScale", "1.25");
        new Main();
    }
}
