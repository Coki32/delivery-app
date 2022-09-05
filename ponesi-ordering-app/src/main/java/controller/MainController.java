package controller;

import data.ItemRepository;
import data.RestaurantRepository;
import data.UserRepository;
import entity.Item;
import entity.ItemKind;
import entity.Restaurant;
import entity.User;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class MainController {

    private UserRepository users = UserRepository.getInstance();
    private RestaurantRepository restaurants = RestaurantRepository.getInstance();
    private ItemRepository items = ItemRepository.getInstance();

    private User currentUser = null;
    private Restaurant selectedRestaurant = null;
    private String itemNameLike = null;

    private ItemKind itemKind = null;

    private Consumer<Void> onFilterChanged = null;

    public MainController(Consumer<Void> onFilterChanged) {
        this.onFilterChanged = onFilterChanged;
    }

    public List<User> getUsers() {
        try {
            return this.users.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
            msg("Failed to load users");
        }
        return new ArrayList<>();
    }

    public List<Restaurant> getRestaurants() {
        try {
            return this.restaurants.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
            msg("Failed to load restaurants");
        }
        return new ArrayList<>();
    }

    public List<Item> getItems() {
        try {
            return this.items.findAllFiltered(itemKind, selectedRestaurant, itemNameLike);
        } catch (SQLException ex) {
            ex.printStackTrace();
            msg("Failed to load items!");
        }
        return new ArrayList<>();
    }

    public List<ItemKind> getItemKinds() {
        try {
            return this.restaurants.findItemKinds(selectedRestaurant);
        } catch (SQLException ex) {
            ex.printStackTrace();
            msg("Failed to load items!");
        }
        return new ArrayList<>();
    }

    public void selectRestaurant(Restaurant r) {
        this.selectedRestaurant = r;
        this.itemKind = null;
        onFilterChanged.accept(null);
    }

    public void setCurrentUser(User u) {
        this.currentUser = u;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setItemKind(ItemKind ik) {
        this.itemKind = ik;
        onFilterChanged.accept(null);
    }

    public void setItemNameLike(String itemNameLike) {
        this.itemNameLike = (itemNameLike != null && itemNameLike.length() > 0) ? itemNameLike : null;
        onFilterChanged.accept(null);
    }

    public String getItemNameLike() {
        return itemNameLike;
    }

    private void msg(String message) {
        JOptionPane.showMessageDialog(null, message, "Error interacting with the database!", JOptionPane.ERROR_MESSAGE);
    }
}
