package tech.ferus.util.sql;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ReturnResults<T> {

    T execute(final ResultSet r) throws SQLException;
}
