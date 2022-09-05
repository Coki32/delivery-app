package components;

import controller.MainController;
import entity.ItemKind;
import entity.Restaurant;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ItemFiltersBar extends JPanel {

    private final MainController mc;

    private LockableJPanel<SelectBox<ItemKind>> itemKindSelector;

    public ItemFiltersBar(MainController mc) {
        this.mc = mc;
        this.setLayout(new GridLayout(1, 4, 5, 0));
        itemKindSelector = createItemKindSelector();
        this.add(createUserSelector());
        this.add(createRestaurantSelector());
        this.add(createItemNameFilter());
        this.add(itemKindSelector);
    }

    private JPanel createUserSelector() {
        var sbox = new SelectBox<>("Choose a user", mc.getUsers(), u -> u.getUsername() + " - " + u.getName());
        sbox.addItemSelectedListener(mc::setCurrentUser);

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
