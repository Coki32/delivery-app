package entity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.CheckedConsumer;

public abstract class BaseEntity {
    public static class FieldSpec {
        public final int position;



        public final CheckedConsumer<ResultSet, SQLException> readFromResultSet;

        public FieldSpec(int position, CheckedConsumer<ResultSet, SQLException> fromResultSet) {
            this.position = position;
            this.readFromResultSet = fromResultSet;
        }

    }

    protected Map<String, FieldSpec> fields = new HashMap<>();

    public abstract String getTableName();

    protected void registerField(String name, FieldSpec spec) {
        fields.put(name, spec);
    }

    public List<String> getFieldNames() {
        return fields.keySet().stream().toList();
    }

    protected void readFromResultSet(ResultSet rs) throws SQLException {
        fields.forEach((field, spec) -> {
            try {
                spec.readFromResultSet.supply(rs);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

}
