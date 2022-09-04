package controller;

import data.ItemRepository;
import data.RestaurantRepository;
import data.UserRepository;
import entity.Item;
import entity.Restaurant;
import entity.User;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainController {

    private UserRepository users = new UserRepository();
    private RestaurantRepository restaurants = new RestaurantRepository();
    private ItemRepository items = new ItemRepository();

    private Restaurant selectedRestaurant = null;

    public MainController() {
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
            if (selectedRestaurant == null) return this.items.findAll();
            else return this.items.findAllByRestaurant(selectedRestaurant);
        } catch (SQLException ex) {
            ex.printStackTrace();
            msg("Failed to load items!");
        }
        return new ArrayList<>();
    }

    public void selectRestaurant(Restaurant r) {
        this.selectedRestaurant = r;
    }

    private void msg(String message) {
        JOptionPane.showMessageDialog(null, message, "Error interacting with the database!", JOptionPane.ERROR_MESSAGE);
    }
}
