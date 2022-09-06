package components;

import controllers.MainController;
import entities.ItemKind;
import entities.Restaurant;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ItemFiltersBar extends JPanel {

    private final Executor executor = Executors.newSingleThreadExecutor();
    private static final int WIDTH_BREAKPOINT = 850;

    private final MainController mc;
    private int rows = 1, cols = 4;
    private LockableJPanel<SelectBox<ItemKind>> itemKindSelector;

    public ItemFiltersBar(MainController mc) {
        this.mc = mc;
        this.setLayout(new GridLayout(rows, cols, 5, 0));
        itemKindSelector = createItemKindSelector();
        this.add(createUserSelector());
        this.add(createRestaurantSelector());
        this.add(createItemNameFilter());
        this.add(itemKindSelector);
        //OPA RESPONSIVE
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                if (e.getComponent() instanceof ItemFiltersBar) {
                    var bar = (ItemFiltersBar) e.getComponent();
                    if (bar.getSize().width < WIDTH_BREAKPOINT && bar.rows == 1) {
                        System.out.println("Mijenjam, sirina=" + bar.getSize().width);
                        bar.setLayout(new GridLayout((bar.rows = 2), (bar.cols = 2), 5, 5));
                        bar.validate();
                    } else if (bar.getSize().width > WIDTH_BREAKPOINT && bar.rows == 2) {

                        System.out.println("Mijenjam, sirina=" + bar.getSize().width);
                        bar.setLayout(new GridLayout((bar.rows = 1), (bar.cols = 4), 5, 0));
                        bar.validate();
                    }
                }
            }
        });
    }

    private JPanel createUserSelector() {
        var users = mc.getUsers();
        var sbox = new SelectBox<>("Choose a user", users, u -> u.getUsername() + " - " + u.getName());
        sbox.addItemSelectedListener(mc::setCurrentUser);
        mc.setCurrentUser(users.get(0));
        var wrapper = new JPanel(new BorderLayout());
        wrapper.setBorder(new EmptyBorder(20, 0, 20, 0));
        wrapper.add(sbox, BorderLayout.CENTER);
        return wrapper;
    }

    private JPanel createRestaurantSelector() {
        var sbox = new SelectBox<>("Choose a restaurant", mc.getRestaurants(), Restaurant::getName);
        sbox.addItemSelectedListener(r -> {
            this.itemKindSelector.setIsLocked(true);
            mc.setItemKind(null);
            mc.selectRestaurant(r);
            this.refreshKinds();
        });


        return new LockableJPanel<>(sbox, "Select a restaurant", true, box -> {
            mc.selectRestaurant(null);
            this.refreshKinds();
//            mc.setItemKind(itemKindSelector.getChild().getCurrentItem());
        }, box -> {
            this.itemKindSelector.setIsLocked(true);
            mc.setItemKind(null);
            mc.selectRestaurant(box.getCurrentItem());
            this.refreshKinds();
        });
    }

    private void refreshKinds() {
        if (itemKindSelector != null)
            this.remove(itemKindSelector);
        this.itemKindSelector = createItemKindSelector();
        this.add(itemKindSelector);
        this.validate();

    }

    private LockableJPanel<SelectBox<ItemKind>> createItemKindSelector() {
        var sbox = new SelectBox<>("Choose an item kind", mc.getItemKinds(), ItemKind::getName);
        sbox.addItemSelectedListener(mc::setItemKind);

        return new LockableJPanel<>(sbox, "Select kind", true, box -> {
            mc.setItemKind(null);
        }, box -> {
            mc.setItemKind(box.getCurrentItem());
        });
    }

    private JPanel createItemNameFilter() {
        return new LockableJPanel<LabeledInput>(new LabeledInput("Item name:", mc::setItemNameLike), "Enter item name", true, li -> {
            //disabled
            mc.setItemNameLike(null);
        }, li -> {
            mc.setItemNameLike(li.getText());
        });
    }

}
