package data;

import data.base.PossessiveRepository;
import entities.*;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemRepository extends PossessiveRepository {

    private static ItemRepository instance;

    private ItemRepository() {

    }

    public static synchronized ItemRepository getInstance() {
        if (instance == null)
            instance = new ItemRepository();
        return instance;
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

        var query = "select " + "ie.id as extra_id," + "ie.name as extra_name," + "ie.additional_cost as extra_cost," + "eg.id as extra_group_id," + "eg.name as group_name," + "eg.min_choices as group_min," + "eg.max_choices as group_max" + " from item_has_item_extra ihie " + " join item_extra ie on ihie.item_extra_id = ie.id" + " join extra_group eg on ie.extra_group_id = eg.id" + " where ihie.item_id = ?";
        try (var ps = conn.prepareStatement(query)) {
            ps.setInt(1, item.getId());
            try (var rs = ps.executeQuery()) {
                while (rs.next()) {
                    result.add(new ItemExtra(rs.getInt("extra_id"), new ItemExtraGroup(rs.getInt("extra_group_id"), rs.getString("group_name"), rs.getInt("group_max"), rs.getInt("group_min")), item.getRestaurant(), rs.getString("extra_name"), rs.getDouble("extra_cost")));
                }
            }
        }
        this.returnConnection(conn);
        return result.size() == 0 ? null : result;
    }

    /**
     * @return All items from the database
     * @throws SQLException
     */
    public List<Item> findAllFiltered(ItemKind ik, Restaurant r, String name, Integer itemId) throws SQLException {
        var conn = this.getConnection();
        var result = new ArrayList<Item>();
        boolean hasWhere = false;
        int restIdPos = 0, nameLikePos = 0, ikIdPos = 0, itemIdPos = 0, currentPos = 1;
        String query = "select i.id as item_id," +
                "i.name as item_name," +
                "description," +
                "price," +
                "ik.name as kind," +
                "r.name as restaurant_name," +
                "r.id as restaurant_id" +
                " from item i " +
                "join item_kind ik on i.item_kind_id = ik.id " +
                "join restaurant r on i.restaurant_id = r.id ";
        if (r != null) {
            query += " where i.restaurant_id = ? ";
            restIdPos = currentPos++;
            hasWhere = true;
        }
        if (ik != null) {
            query += (hasWhere ? " and " : " where ") + " ik.id = ? ";
            ikIdPos = currentPos++;
            hasWhere = true;
        }
        if (name != null) {
            query += (hasWhere ? " and " : " where ") + " i.name like ?";
            nameLikePos = currentPos++;
            hasWhere = true;
        }
        if (itemId != null) {
            query += (hasWhere ? " and " : " where ") + " i.id = ?";
            itemIdPos = currentPos++;
        }
        try (var ps = conn.prepareStatement(query)) {
            if (restIdPos != 0)
                ps.setInt(restIdPos, r.getId());
            if (nameLikePos != 0)
                ps.setString(nameLikePos, "%" + name + "%");
            if (ikIdPos != 0)
                ps.setInt(ikIdPos, ik.getId());
            if (itemIdPos != 0)
                ps.setInt(itemIdPos, itemId);
            execute(result, ps);
        }

        this.returnConnection(conn);
        return result;
    }

    public List<Item> findAllFiltered(ItemKind ik, Restaurant r, String name) throws SQLException {
        return findAllFiltered(ik, r, name, null);
    }

    private void execute(ArrayList<Item> result, PreparedStatement ps) throws SQLException {
        try (var rs = ps.executeQuery()) {
            while (rs.next()) {
                Item it = new Item(
                        rs.getInt("item_id"),
                        rs.getString("item_name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getString("kind"),
                        null,
                        new Restaurant(
                                rs.getInt("restaurant_id"),
                                rs.getString("restaurant_name"),
                                null, null, false, false, null)
                );
                it.setExtras(loadItemExtras(it));
                result.add(it);
            }
        }
    }

    public Item findById(int id) throws SQLException {
        var res = findAllFiltered(null, null, null, id);
        if (res != null)
            return res.get(0);
        else return null;
    }

}
