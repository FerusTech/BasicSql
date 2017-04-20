package tech.ferus.util.sql;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface HandleResults {

    void execute(final ResultSet r) throws SQLException;
}
