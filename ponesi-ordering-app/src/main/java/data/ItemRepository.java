package data;

import data.base.PossessiveRepository;
import entity.Item;
import entity.ItemExtra;
import entity.ItemExtraGroup;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemRepository extends PossessiveRepository {

    public ItemRepository() {
    }


    /**
     * Takes in the whole item as an argument. Why? Because that allows the use of same method
     * with both findAll() and findAllWithRestaurant() methods. If the item had restaurant, same
     * be reused here.
     *
     * @param item item whose extras you want to load
     * @return List of extras.
     * @throws SQLException
     */
    private List<ItemExtra> loadItemExtras(Item item) throws SQLException {
        var conn = this.getConnection();
        var result = new ArrayList<ItemExtra>();

        var query = "select " +
                "ie.id as extra_id," +
                "ie.name as extra_name," +
                "ie.additinal_cost as extra_cost," +
                "eg.id as extra_group_id," +
                "eg.name as group_name," +
                "eg.min_choices as group_min," +
                "eg.max_choices as group_max" +
                " from item_has_item_extra ihie " +
                " join item_extra ie on ihie.item_extra_id = ie.id" +
                " join extra_group eg on ie.extra_group_id = eg.id" +
                " where ihie.item_id = ?";
        try (var ps = conn.prepareStatement(query)) {
            ps.setInt(1, item.getId());
            try (var rs = ps.executeQuery()) {
                while (rs.next()) {
                    result.add(new ItemExtra(
                            rs.getInt("extra_id"),
                            new ItemExtraGroup(
                                    rs.getInt("extra_group_id"),
                                    rs.getString("group_name"),
                                    rs.getInt("group_max"),
                                    rs.getInt("group_min")
                            ), item.getRestaurant(), rs.getString("extra_name"), rs.getDouble("extra_cost")
                    ));
                }
            }
        }

        this.returnConnection(conn);
        return result.size() == 0 ? null : result;
    }

    /**
     * Leaves restaurant property of items set to null, use findAllWithRestaurant() to eagerly load restaurants also
     *
     * @return All items from the database
     * @throws SQLException
     */
    public List<Item> findAll() throws SQLException {
        var conn = this.getConnection();
        var result = new ArrayList<Item>();

        String query = "select i.id as item_id, i.name as item_name, description, price, ik.name as kind from item i join item_kind ik on i.item_kind_id = ik.id";

        try (var ps = conn.prepareStatement(query)) {
            try (var rs = ps.executeQuery()) {
                while (rs.next()) {
                    Item it = new Item(rs.getInt("item_id"), rs.getString("item_name"), rs.getString("description"), rs.getDouble("price"), rs.getString("kind"), null, null);
                    it.setExtras(loadItemExtras(it));
                    result.add(it);
                }
            }
        }

        this.returnConnection(conn);
        return result;
    }

    public List<Item> findAllWithRestaurant() {
        return null;
    }
}
