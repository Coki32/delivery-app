package data;

import data.base.PossessiveRepository;
import entity.User;

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
                            rs.getString("name")
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
                            rs.getString("name")
                    );
                }
            }
        }

        this.returnConnection(conn);
        return result;
    }

}
