package entities;

import java.util.List;

public class Item {

    private int id;
    private String name;
    private String description;
    private double price;
    private String kind;
    private List<ItemExtra> extras;

    private Restaurant restaurant;

    public Item(int id, String name, String description, double price, String kind, List<ItemExtra> extras, Restaurant restaurant) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.kind = kind;
        this.extras = extras;
        this.restaurant = restaurant;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public List<ItemExtra> getExtras() {
        return extras;
    }

    public void setExtras(List<ItemExtra> extras) {
        this.extras = extras;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", kind='" + kind + '\'' +
                ", extras=" + extras +
                ", restaurant=" + (restaurant != null ? restaurant.getName() : "null") +
                '}';
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
