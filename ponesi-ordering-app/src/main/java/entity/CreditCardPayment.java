package entity;

public class CreditCardPayment extends Payment {

    private int creditCardId;

    public CreditCardPayment(int id, int creditCardId) {
        super(id);
        this.creditCardId = creditCardId;
    }

    public int getCreditCardId() {
        return creditCardId;
    }

    public void setCreditCardId(int creditCardId) {
        this.creditCardId = creditCardId;
    }
}
