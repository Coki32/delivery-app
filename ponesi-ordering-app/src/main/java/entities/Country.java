package entities;

import java.util.Objects;

public class Country {
    private int id;
    private String shorthand;
    private String fullName;

    public Country(int id, String shorthand, String fullName) {
        this.id = id;
        this.shorthand = shorthand;
        this.fullName = fullName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country = (Country) o;
        return id == country.id && Objects.equals(shorthand, country.shorthand) && Objects.equals(fullName, country.fullName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, shorthand, fullName);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShorthand() {
        return shorthand;
    }

    public void setShorthand(String shorthand) {
        this.shorthand = shorthand;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
