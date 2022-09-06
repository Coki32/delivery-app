package data;

import data.base.PossessiveRepository;
import entities.Country;
import entities.CreditCard;
import entities.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CreditCardRepository extends PossessiveRepository {

    private static CreditCardRepository instance;

    private CreditCardRepository() {

    }

    public static synchronized CreditCardRepository getInstance() {
        if (instance == null)
            instance = new CreditCardRepository();
        return instance;
    }

    public List<CreditCard> getUserCards(User u) throws SQLException {
        var conn = this.getConnection();
        var result = new ArrayList<CreditCard>();
        //User is ommited because we'll reuse the object that came in
        var query = "select " +
                "c.id as cid," +
                "c.name as cname," +
                "c.card_number as cnum," +
                "c.exp_date as cexp," +
                "c.cvc as ccvc," +
                "c.label as clbl," +
                "c2.id as country_id," +
                "c2.shorthand as country_sh," +
                "c2.full_name as country_full" +
                " from credit_card c join country c2 on c.country_id = c2.id where c.user_id = ?";
        try (var ps = conn.prepareStatement(query)) {
            ps.setInt(1, u.getId());
            try (var rs = ps.executeQuery()) {
                result.add(
                        new CreditCard(
                                rs.getInt("cid"), rs.getString("cname"), rs.getString("cnum"),
                                rs.getString("cexp"), rs.getString("ccvc"),
                                new Country(rs.getInt("country_id"), rs.getString("country_sh"), rs.getString("country_full")),
                                rs.getString("clbl"), u));
            }
        }
        this.returnConnection(conn);
        return result;
    }
}
