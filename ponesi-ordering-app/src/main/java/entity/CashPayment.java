package entity;

public class CashPayment extends Payment {
    private double userHasAmount;

    public CashPayment(int id, double userHasAmount) {
        super(id);
        this.userHasAmount = userHasAmount;
    }

    public double getUserHasAmount() {
        return userHasAmount;
    }

    public void setUserHasAmount(double userHasAmount) {
        this.userHasAmount = userHasAmount;
    }
}
