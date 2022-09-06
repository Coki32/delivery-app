package entities;

public class CreditCardPayment extends Payment {

    private CreditCard creditCard;

    public CreditCardPayment(int id, CreditCard creditCard) {
        super(id);
        this.creditCard = creditCard;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }
}
