package entity;

import java.util.List;
import java.util.Objects;

public class Restaurant {

    public static final String TABLE_NAME = "restaurant";

    private int id;
    private String name;
    private String waitTime;
    private String address;
    private boolean hasTakeout;
    private boolean acceptingOrders;

    private List<String> categories;


    public Restaurant(int id, String name, String waitTime, String address, boolean hasTakeout, boolean acceptingOrders, List<String> categories) {
        this.id = id;
        this.name = name;
        this.waitTime = waitTime;
        this.address = address;
        this.hasTakeout = hasTakeout;
        this.acceptingOrders = acceptingOrders;
        this.categories = categories;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", waitTime='" + waitTime + '\'' +
                ", address='" + address + '\'' +
                ", hasTakeout=" + hasTakeout +
                ", acceptingOrders=" + acceptingOrders +
                ", categories= " + categories.toString() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Restaurant that = (Restaurant) o;
        return id == that.id && hasTakeout == that.hasTakeout && acceptingOrders == that.acceptingOrders && name.equals(that.name) && Objects.equals(waitTime, that.waitTime) && address.equals(that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, waitTime, address, hasTakeout, acceptingOrders);
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

    public String getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(String waitTime) {
        this.waitTime = waitTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isHasTakeout() {
        return hasTakeout;
    }

    public void setHasTakeout(boolean hasTakeout) {
        this.hasTakeout = hasTakeout;
    }

    public boolean isAcceptingOrders() {
        return acceptingOrders;
    }

    public void setAcceptingOrders(boolean acceptingOrders) {
        this.acceptingOrders = acceptingOrders;
    }

}
