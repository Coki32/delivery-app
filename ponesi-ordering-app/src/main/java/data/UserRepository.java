package data;

import data.base.PossessiveRepository;
import entity.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository extends PossessiveRepository {

    public UserRepository() {

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

}
