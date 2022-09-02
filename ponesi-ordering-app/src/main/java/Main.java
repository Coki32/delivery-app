import data.ItemRepository;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {

        var rr = new ItemRepository();
        rr.findAll().stream().forEach(System.out::println);
//        System.out.println(rr.findOne(1));
//        System.out.println(rr.findOne(10));
        System.out.println("Čekaj šđšžčćčžčć");
    }
}
