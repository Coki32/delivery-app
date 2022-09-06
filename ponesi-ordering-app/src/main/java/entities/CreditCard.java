package entities;

import java.util.Objects;

public class CreditCard {

    private int id;
    private String name;
    private String number;
    private String exp;
    private String cvc;
    private Country country;
    private String label;//optional
    private User user;

    public CreditCard(int id, String name, String number, String exp, String cvc, Country country, String label, User user) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.exp = exp;
        this.cvc = cvc;
        this.country = country;
        this.label = label;
        this.user = user;
    }

    @Override
    public String toString() {
        return "CreditCard{" +
                "name='" + name + '\'' +
                ", number='" + number + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreditCard that = (CreditCard) o;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(number, that.number) && Objects.equals(exp, that.exp) && Objects.equals(cvc, that.cvc) && Objects.equals(country, that.country) && Objects.equals(label, that.label) && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, number, exp, cvc, country, label, user);
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public String getCvc() {
        return cvc;
    }

    public void setCvc(String cvc) {
        this.cvc = cvc;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
