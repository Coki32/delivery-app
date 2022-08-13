import data.ConnectionPool;
import data.RestaurantRepository;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        var c = ConnectionPool.getInstance().checkOut();

        RestaurantRepository repo = new RestaurantRepository(c);
        var rs = repo.findAll();
//        rs.forEach(System.out::println);
        System.out.println(repo.findById(2));
    }
}
