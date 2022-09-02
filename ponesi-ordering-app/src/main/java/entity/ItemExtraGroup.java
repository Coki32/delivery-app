package entity;

import java.util.Objects;

public class ItemExtraGroup {
    private int id;
    private String name;
    private int max_choices;
    private int min_choices;

    public ItemExtraGroup(int id, String name, int max_choices, int min_choices) {
        this.id = id;
        this.name = name;
        this.max_choices = max_choices;
        this.min_choices = min_choices;
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

    public int getMax_choices() {
        return max_choices;
    }

    public void setMax_choices(int max_choices) {
        this.max_choices = max_choices;
    }

    public int getMin_choices() {
        return min_choices;
    }

    public void setMin_choices(int min_choices) {
        this.min_choices = min_choices;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemExtraGroup that = (ItemExtraGroup) o;
        return id == that.id && max_choices == that.max_choices && min_choices == that.min_choices && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, max_choices, min_choices);
    }

    @Override
    public String toString() {
        return "ItemExtraGroup{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", max_choices=" + max_choices +
                ", min_choices=" + min_choices +
                '}';
    }
}
