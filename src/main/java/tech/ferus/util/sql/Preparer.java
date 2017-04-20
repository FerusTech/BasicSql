package tech.ferus.util.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface Preparer {

    void prepare(final PreparedStatement s) throws SQLException;
}
