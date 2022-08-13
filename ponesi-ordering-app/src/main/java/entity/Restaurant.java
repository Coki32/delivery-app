package entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class Restaurant extends BaseEntity {

    public static final String TABLE_NAME = "restaurant";

    private int id;
    private String name;
    private String waitTime;
    private String address;
    private boolean hasTakeout;
    private boolean acceptingOrders;


    public Restaurant(int id, String name, String waitTime, String address, boolean hasTakeout, boolean acceptingOrders) {
        this.id = id;
        this.name = name;
        this.waitTime = waitTime;
        this.address = address;
        this.hasTakeout = hasTakeout;
        this.acceptingOrders = acceptingOrders;
        registerFields();
    }


    public Restaurant(ResultSet rs) throws SQLException {
        registerFields();
        this.readFromResultSet(rs);
    }

    private void registerFields() {
        this.registerField("id", new FieldSpec(1, rs -> this.setId(rs.getInt("id"))));
        this.registerField("name", new FieldSpec(2, rs -> this.setName(rs.getString("name"))));
        this.registerField("wait_time", new FieldSpec(3, rs -> this.setWaitTime(rs.getString("wait_time"))));
        this.registerField("address", new FieldSpec(4, rs -> this.setAddress(rs.getString("address"))));
        this.registerField("has_takeout", new FieldSpec(5, rs -> this.setHasTakeout(rs.getBoolean("has_takeout"))));
        this.registerField("accepting_orders", new FieldSpec(6, rs -> this.setAcceptingOrders(rs.getBoolean("accepting_orders"))));
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

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }
}
