package data;

import data.base.GenerousRepository;
import entities.Country;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CountryRepository extends GenerousRepository {

    private static CountryRepository instance;

    private CountryRepository() {

    }

    public static synchronized CountryRepository getInstance() {
        if (instance == null)
            instance = new CountryRepository();
        return instance;
    }

    public List<Country> findAll() throws SQLException {
        var conn = this.getConnection();
        var result = new ArrayList<Country>();
        var query = "select id, shorthand, full_name from country";
        try (var ps = conn.prepareStatement(query)) {
            try (var rs = ps.executeQuery()) {
                while (rs.next()) {
                    result.add(new Country(rs.getInt("id"), rs.getString("shorthand"), rs.getString("full_name")));
                }
            }
        }
        this.returnConnection(conn);
        return result;
    }
}
