package tech.ferus.util.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasicSql {

    private static final Logger LOGGER = LoggerFactory.getLogger(BasicSql.class);

    public static void execute(final String statement) {
        execute(Database.getDefaultDatabase(), statement, s -> {});
    }

    public static void execute(final Database database, final String statement) {
        execute(database, statement, s -> {});
    }

    public static void execute(final Database database, final String statement, final Preparer preparer) {
        Connection c = null;
        PreparedStatement s = null;
        try {
            c = database.getConnection();
            s = c.prepareStatement(statement);
            preparer.prepare(s);
            s.execute();
        } catch (final SQLException e) {
            LOGGER.error("Failed to execute SQL: {}", statement, e);
        } finally {
            close(statement, c, s, null);
        }
    }

    public static void query(final String statement, final HandleResults results) {
        query(Database.getDefaultDatabase(), statement, s -> {}, results);
    }

    public static void query(final Database database, final String statement, final HandleResults results) {
        query(database, statement, s -> {}, results);
    }

    public static void query(final Database database, final String statement, final Preparer preparer, final HandleResults results) {
        Connection c = null;
        PreparedStatement s = null;
        ResultSet r = null;

        try {
            c = database.getConnection();
            s = c.prepareStatement(statement);
            preparer.prepare(s);
            r = s.executeQuery();
            results.execute(r);
        } catch (final SQLException e) {
            LOGGER.error("Failed to execute query: {}", statement, e);
        } finally {
            close(statement, c, s, r);
        }
    }

    public static <T> Optional<T> returnQuery(final String statement, final ReturnResults<T> returnResults) {
        return returnQuery(Database.getDefaultDatabase(), statement, s -> {}, returnResults);
    }

    public static <T> Optional<T> returnQuery(final Database database, final String statement, final ReturnResults<T> returnResults) {
        return returnQuery(database, statement, s -> {}, returnResults);
    }

    public static <T> Optional<T> returnQuery(final Database database, final String statement, final Preparer preparer, final ReturnResults<T> returnResults) {
        Connection c = null;
        PreparedStatement s = null;
        ResultSet r = null;

        try {
            c = database.getConnection();
            s = c.prepareStatement(statement);
            preparer.prepare(s);
            r = s.executeQuery();
            return Optional.ofNullable(returnResults.execute(r));
        } catch (final SQLException e) {
            LOGGER.error("Failed to execute query: {}", statement, e);
            return Optional.empty();
        } finally {
            close(statement, c, s, r);
        }
    }

    private static void close(final String statement, final Connection c, final Statement s, final ResultSet r) {
        if (r != null) {
            try {
                r.close();
            } catch (final SQLException e) {
                LOGGER.error("Failed to close ResultSet ({}): {}", statement, e.getMessage(), e);
            }
        }

        if (s != null) {
            try {
                s.close();
            } catch (final SQLException e) {
                LOGGER.error("Failed to close Statement ({}): {}", statement, e.getMessage(), e);
            }
        }

        if (c != null) {
            try {
                c.close();
            } catch (final SQLException e) {
                LOGGER.error("Failed to close Connection ({}): {}", statement, e.getMessage(), e);
            }
        }
    }
}
