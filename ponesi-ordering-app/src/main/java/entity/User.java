package entity;

public class User {
    private int id;
    private String email;

    private String username;
    private String address;
    private String name;

    public User(int id, String username, String email, String address, String name) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.address = address;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", username='" + username + '\'' + ", email='" + email + '\'' + ", address='" + address + '\'' + ", name='" + name + '\'' + '}';
    }
}
