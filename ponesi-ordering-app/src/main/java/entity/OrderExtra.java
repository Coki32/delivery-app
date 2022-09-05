package entity;

public class OrderExtra extends ItemExtra {

    private int quantity;

    public OrderExtra(int id, ItemExtraGroup group, Restaurant restaurant, String name, double additional_cost, int quantity) {
        super(id, group, restaurant, name, additional_cost);
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrderExtra{" + "quantity=" + quantity + "extra=" + super.toString() + '}';
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
