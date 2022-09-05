package entity;

import java.util.List;

public class Order {

    private int id;
    private User user;
    private Payment payment;
    private String orderAddress;
    private OrderType type;
    private Courier courier;
    private List<OrderItem> orderedItems;

    public Order(int id, User user, Payment payment, String orderAddress, OrderType type, Courier courier, List<OrderItem> orderedItems) {
        this.id = id;
        this.user = user;
        this.payment = payment;
        this.orderAddress = orderAddress;
        this.type = type;
        this.courier = courier;
        this.orderedItems = orderedItems;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public String getOrderAddress() {
        return orderAddress;
    }

    public void setOrderAddress(String orderAddress) {
        this.orderAddress = orderAddress;
    }

    public OrderType getType() {
        return type;
    }

    public void setType(OrderType type) {
        this.type = type;
    }

    public Courier getCourier() {
        return courier;
    }

    public void setCourier(Courier courier) {
        this.courier = courier;
    }

    public List<OrderItem> getOrderedItems() {
        return orderedItems;
    }

    public void setOrderedItems(List<OrderItem> orderedItems) {
        this.orderedItems = orderedItems;
    }


}
