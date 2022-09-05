package entity;

import java.util.Objects;

public class ItemKind {
    private int id;
    private String name;

    public ItemKind(int id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemKind itemKind = (ItemKind) o;
        return id == itemKind.id && Objects.equals(name, itemKind.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "ItemKind{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
