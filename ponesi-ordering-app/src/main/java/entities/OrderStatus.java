package entities;

import java.sql.Timestamp;

public class OrderStatus {

    private int id;
    private String status;

    private Timestamp when;

    public OrderStatus(int id, String status, Timestamp when) {
        this.id = id;
        this.status = status;
        this.when = when;
    }

    public Timestamp getWhen() {
        return when;
    }

    public void setWhen(Timestamp when) {
        this.when = when;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
