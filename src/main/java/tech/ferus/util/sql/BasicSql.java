/*
 * Copyright 2017 FerusTech LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package tech.ferus.util.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.ferus.util.sql.databases.Database;

/**
 * A utility class meant to make use of the tools provided in BasicSql.
 *
 * <p>Check out the following classes for more details:</p>
 * <ul>
 *     <li>{@link Database}</li>
 *     <li>{@link HandleResults}</li>
 *     <li>{@link Preparer}</li>
 *     <li>{@link ReturnResults}</li>
 * </ul>
 */
public final class BasicSql {

    /**
     * The logger for {@link BasicSql}.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BasicSql.class);

    /**
     * Executes a statement.
     *
     * <p>Uses the default {@link Database}.</p>
     *
     * @param statement the statement to be executed
     */
    public static void execute(final String statement) {
        execute(Database.getDefaultDatabase(), statement, s -> {});
    }

    /**
     * Executes a statement.
     *
     * @param database the {@link Database} to execute the statement on
     * @param statement the statement to be executed
     */
    public static void execute(final Database database, final String statement) {
        execute(database, statement, s -> {});
    }

    /**
     * Executes a statement.
     *
     * <p>Uses the default {@link Database}.</p>
     *
     * @param statement the statement to be executed
     * @param preparer the {@link Preparer} to set data in the statement
     */
    public static void execute(final String statement, final Preparer preparer) {
        execute(Database.getDefaultDatabase(), statement, preparer);
    }

    /**
     * Executes a statement.
     *
     * @param database the {@link Database} to execute the statement on
     * @param statement the statement to be executed
     * @param preparer the {@link Preparer} to set data in the statement.
     */
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

    /**
     * Executes a query.
     *
     * <p>Uses the default {@link Database}.</p>
     *
     * @param statement the statement to be queried
     * @param results determines what happens with the {@link ResultSet}
     */
    public static void query(final String statement, final HandleResults results) {
        query(Database.getDefaultDatabase(), statement, s -> {}, results);
    }

    /**
     * Executes a query.
     *
     * @param database the {@link Database} to execute the query on
     * @param statement the statement to be queried
     * @param results determines what happens with the {@link ResultSet}
     */
    public static void query(final Database database, final String statement, final HandleResults results) {
        query(database, statement, s -> {}, results);
    }

    /**
     * Executes a query.
     *
     * <p>Uses the default {@link Database}.</p>
     *
     * @param statement the statement to be queried
     * @param preparer the {@link Preparer} to set the data in the statement
     * @param results determines what happens with the {@link ResultSet}
     */
    public static void query(final String statement, final Preparer preparer, final HandleResults results) {
        query(Database.getDefaultDatabase(), statement, preparer, results);
    }

    /**
     * Executes a query.
     *
     * @param database the {@link Database} to execute the query on
     * @param statement the statement to be queried
     * @param preparer the {@link Preparer} to set the data in the statement
     * @param results determines what happens with the {@link ResultSet}
     */
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

    /**
     * Returns the data gathered from a query.
     *
     * <p>Uses the default {@link Database}.</p>
     *
     * @param statement the statement to be queried
     * @param returnResults determines which data to return from a {@link ResultSet}
     * @param <T> the Type of object being returned
     * @return the data from a {@link ResultSet} wrapped in an {@link Optional}. {@link Optional#empty()} otherwise.
     */
    public static <T> Optional<T> returnQuery(final String statement, final ReturnResults<T> returnResults) {
        return returnQuery(Database.getDefaultDatabase(), statement, s -> {}, returnResults);
    }

    /**
     * Returns the data gathered from a query.
     *
     * @param database the database to execute the query on
     * @param statement the statement to be queried
     * @param returnResults determines which data to return from a {@link ResultSet}
     * @param <T> the Type of object being returned
     * @return the data from a {@link ResultSet} wrapped in an {@link Optional}. {@link Optional#empty()} otherwise.
     */
    public static <T> Optional<T> returnQuery(final Database database, final String statement, final ReturnResults<T> returnResults) {
        return returnQuery(database, statement, s -> {}, returnResults);
    }

    /**
     * Returns the data gathered from a query.
     *
     * <p>Uses the default {@link Database}.</p>
     *
     * @param statement the statement to be queried
     * @param preparer the {@link Preparer} to set the data in the statement
     * @param returnResults determines which data to return from a {@link ResultSet}
     * @param <T> the Type of object being returned
     * @return the data from a {@link ResultSet} wrapped in an {@link Optional}. {@link Optional#empty()} otherwise.
     */
    public static <T> Optional<T> returnQuery(final String statement, final Preparer preparer, final ReturnResults<T> returnResults) {
        return returnQuery(Database.getDefaultDatabase(), statement, preparer, returnResults);
    }

    /**
     * Returns the data gathered from a query.
     *
     * @param database the database to execute the query on
     * @param statement the statement to be queried
     * @param preparer the {@link Preparer} to set the data in the statement
     * @param returnResults determines which data to return from a {@link ResultSet}
     * @param <T> the Type of object being returned
     * @return the data from a {@link ResultSet} wrapped in an {@link Optional}. {@link Optional#empty()} otherwise.
     */
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

    /**
     * Closes any open objects from the attempted queries and executions.
     *
     * <p>Should work on:</p>
     * <ul>
     *     <li>{@link Connection}</li>
     *     <li>{@link Statement}</li>
     *     <li>{@link ResultSet}</li>
     * </ul>
     *
     * @param statement the statement that was queried or executed
     * @param c the {@link Connection} that was used
     * @param s the {@link Statement} object generated from the statement
     * @param r the {@link ResultSet} that was gathered from a query
     */
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
