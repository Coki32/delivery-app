import com.formdev.flatlaf.FlatDarculaLaf;
import components.ItemFiltersBar;
import components.ItemSquare;
import controllers.MainController;
import controllers.OrderController;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.Arrays;

public class Main extends JFrame {

    private MainController mc;
    private OrderController oc;

    JLabel koSi = new JLabel("Nisi niko jos");

    private JScrollPane itemPane = null;


    public Main() throws SQLException {
        super("Ponesi food delivery");
        this.setLayout(new BorderLayout(5, 5));
        this.mc = new MainController(_void -> displayItems());
        this.oc = new OrderController();

        this.createMenu();

        var top = new ItemFiltersBar(mc);

        this.add(top, BorderLayout.PAGE_START);

        this.displayItems();

        this.pack();
        this.setVisible(true);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    private void displayItems() {
        var itemHolder = new JPanel();
        itemHolder.setLayout(new BoxLayout(itemHolder, BoxLayout.Y_AXIS));
        mc.getItems().forEach(i -> itemHolder.add(new ItemSquare(i, oc)));

        int height = Arrays.stream(itemHolder.getComponents()).mapToInt(c -> c.getPreferredSize().height + 20).sum();
        itemHolder.setPreferredSize(new Dimension(300, height));

        var jsp = new JScrollPane(itemHolder);
//        jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//        jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        if (itemPane != null) this.remove(itemPane);

        this.add(jsp, BorderLayout.CENTER);
        this.validate();
        itemPane = jsp;
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
        FlatDarculaLaf.setup();
        UIManager.put("Button.arc", 10);
        UIManager.put("Component.arc", 10);
        UIManager.put("ProgressBar.arc", 10);
        UIManager.put("TextComponent.arc", 10);
        System.setProperty("flatlaf.uiScale", "1.25");
        new Main();
    }
}
