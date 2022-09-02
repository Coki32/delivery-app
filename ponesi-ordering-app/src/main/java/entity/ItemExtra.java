package entity;

import java.util.Objects;

public class ItemExtra {
    private int id;
    private ItemExtraGroup group;
    private Restaurant restaurant;
    private String name;
    private double additional_cost;

    public ItemExtra(int id, ItemExtraGroup group, Restaurant restaurant, String name, double additional_cost) {
        this.id = id;
        this.group = group;
        this.restaurant = restaurant;
        this.name = name;
        this.additional_cost = additional_cost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ItemExtraGroup getGroup() {
        return group;
    }

    public void setGroup(ItemExtraGroup group) {
        this.group = group;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAdditional_cost() {
        return additional_cost;
    }

    public void setAdditional_cost(double additional_cost) {
        this.additional_cost = additional_cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemExtra itemExtra = (ItemExtra) o;
        return id == itemExtra.id && Double.compare(itemExtra.additional_cost, additional_cost) == 0 && Objects.equals(group, itemExtra.group) && Objects.equals(restaurant, itemExtra.restaurant) && Objects.equals(name, itemExtra.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, group, restaurant, name, additional_cost);
    }

    @Override
    public String toString() {
        return "ItemExtra{" +
                "id=" + id +
                ", group=" + group +
                ", restaurant=" + restaurant +
                ", name='" + name + '\'' +
                ", additional_cost=" + additional_cost +
                '}';
    }
}
