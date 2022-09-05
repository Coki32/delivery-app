package entities;

import java.util.List;

public class OrderItem {

    private int id;
    private Item item;
    private List<OrderExtra> extras;
    private int quantity;

    public OrderItem(int id, Item item, List<OrderExtra> extras, int quantity) {
        this.id = id;
        this.item = item;
        this.extras = extras;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public List<OrderExtra> getExtras() {
        return extras;
    }

    public void setExtras(List<OrderExtra> extras) {
        this.extras = extras;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
