package data;

import data.base.PossessiveRepository;
import entities.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository extends PossessiveRepository {

    private static UserRepository instance;

    private UserRepository() {

    }

    public static synchronized UserRepository getInstance() {
        if (instance == null)
            instance = new UserRepository();
        return instance;
    }

    public boolean createNew(User u) throws SQLException {
        var conn = this.getConnection();
        //                                  1       2      3          4      5
        var query = "insert into user ( username, email, password, address, name) VALUES (?, ?, ?, ?, ?)";
        try (var ps = conn.prepareStatement(query)) {
            ps.setString(1, u.getUsername());
            ps.setString(2, u.getEmail());
            ps.setString(3, u.getPassword());
            ps.setString(4, u.getAddress());
            ps.setString(5, u.getName());
            ps.executeUpdate();
        }
        this.returnConnection(conn);
        return true;
    }

    public List<User> findAll() throws SQLException {
        var conn = this.getConnection();
        var result = new ArrayList<User>();

        var query = "select id, username, email, password, address, name from user";
        try (var ps = conn.prepareStatement(query)) {
            try (var rs = ps.executeQuery()) {
                while (rs.next()) {
                    result.add(new User(
                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("email"),
                            rs.getString("address"),
                            rs.getString("name"),
                            rs.getString("password")
                    ));
                }
            }
        }

        this.returnConnection(conn);
        return result;
    }

    public User findById(int id) throws SQLException {
        var conn = this.getConnection();
        User result = null;

        var query = "select id, username, email, password, address, name from user where id = ?";
        try (var ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            try (var rs = ps.executeQuery()) {
                if (rs.next()) {
                    result = new User(
                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("email"),
                            rs.getString("address"),
                            rs.getString("name"),
                            rs.getString("password")
                    );
                }
            }
        }

        this.returnConnection(conn);
        return result;
    }

}
