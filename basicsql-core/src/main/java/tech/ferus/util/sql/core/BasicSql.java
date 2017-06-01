/*
 * This file is part of BasicSql, licensed under the MIT License (MIT).
 *
 * Copyright (c) FerusTech LLC <https://ferus.tech>
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package tech.ferus.util.sql.core;

import tech.ferus.util.sql.api.Database;
import tech.ferus.util.sql.api.HandleResults;
import tech.ferus.util.sql.api.Preparer;
import tech.ferus.util.sql.api.ReturnResults;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

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
public class BasicSql {

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
    public static void execute(@Nonnull final String statement) {
        execute(DefaultDatabase.getDatabase(), statement, s -> {});
    }

    /**
     * Executes a statement.
     *
     * <p>Uses the default {@link Database}.</p>
     *
     * @param statement the statement to be executed
     * @param preparer the {@link Preparer} to set data in the statement.
     */
    public static void execute(@Nonnull final String statement,
                               @Nonnull final Preparer preparer) {
        execute(DefaultDatabase.getDatabase(), statement, preparer);
    }

    /**
     * Executes a statement.
     *
     * @param database the {@link Database} to execute the statement on
     * @param statement the statement to be executed
     */
    public static void execute(@Nonnull final Database database,
                               @Nonnull final String statement) {
        execute(database, statement, s -> {});
    }

    /**
     * Executes a statement.
     *
     * @param database the {@link Database} to execute the statement on
     * @param statement the statement to be executed
     * @param preparer the {@link Preparer} to set data in the statement.
     */
    public static void execute(@Nonnull final Database database,
                               @Nonnull final String statement,
                               @Nonnull final Preparer preparer) {
        Connection c = null;
        PreparedStatement s = null;

        LOGGER.debug("Attempting to execute statement: {}", statement);
        try {
            c = database.getConnection();
            s = c.prepareStatement(statement);
            preparer.prepare(s);
            s.execute();
            LOGGER.debug("Finished executing statement: {}", statement);
        } catch (final SQLException e) {
            LOGGER.error("Failed to execute statement: {}", statement, e);
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
     * @param handle determines what happens with the {@link ResultSet}
     */
    public static void query(@Nonnull final String statement,
                             @Nonnull final HandleResults handle) {
        query(DefaultDatabase.getDatabase(), statement, s -> {}, handle);
    }

    /**
     * Executes a query.
     *
     * <p>Uses the default {@link Database}.</p>
     *
     * @param statement the statement to be queried
     * @param preparer the {@link Preparer} to set the data in the statement
     * @param handle determines what happens with the {@link ResultSet}
     */
    public static void query(@Nonnull final String statement,
                             @Nonnull final Preparer preparer,
                             @Nonnull final HandleResults handle) {
        query(DefaultDatabase.getDatabase(), statement, preparer, handle);
    }

    /**
     * Executes a query.
     *
     * @param database the {@link Database} to execute the query on
     * @param statement the statement to be queried
     * @param handle determines what happens with the {@link ResultSet}
     */
    public static void query(@Nonnull final Database database,
                             @Nonnull final String statement,
                             @Nonnull final HandleResults handle) {
        query(database, statement, s -> {}, handle);
    }

    /**
     * Executes a query.
     *
     * @param database the {@link Database} to execute the query on
     * @param statement the statement to be queried
     * @param preparer the {@link Preparer} to set the data in the statement
     * @param handle determines what happens with the {@link ResultSet}
     */
    public static void query(@Nonnull final Database database,
                             @Nonnull final String statement,
                             @Nonnull final Preparer preparer,
                             @Nonnull final HandleResults handle) {
        Connection c = null;
        PreparedStatement s = null;
        ResultSet r = null;

        LOGGER.debug("Attempting to query: {}", statement);
        try {
            c = database.getConnection();
            s = c.prepareStatement(statement);
            preparer.prepare(s);
            r = s.executeQuery();
            handle.execute(r);
            LOGGER.debug("Finished querying: {}", statement);
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
     * @param handle determines which data to return from a {@link ResultSet}
     * @param <T> the Type of object being returned
     * @return the data from a {@link ResultSet} wrapped in an {@link Optional}. {@link Optional#empty()} otherwise.
     */
    public static <T> Optional<T> returnQuery(@Nonnull final String statement,
                                              @Nonnull final ReturnResults<T> handle) {
        return returnQuery(DefaultDatabase.getDatabase(), statement, s -> {}, handle);
    }

    /**
     * Returns the data gathered from a query.
     *
     * <p>Uses the default {@link Database}.</p>
     *
     * @param statement the statement to be queried
     * @param preparer preparer the {@link Preparer} to set the data in the statement
     * @param handle determines which data to return from a {@link ResultSet}
     * @param <T> the Type of object being returned
     * @return the data from a {@link ResultSet} wrapped in an {@link Optional}. {@link Optional#empty()} otherwise.
     */
    public static <T> Optional<T> returnQuery(@Nonnull final String statement,
                                              @Nonnull final Preparer preparer,
                                              @Nonnull final ReturnResults<T> handle) {
        return returnQuery(DefaultDatabase.getDatabase(), statement, preparer, handle);
    }

    /**
     * Returns the data gathered from a query.
     *
     * @param database the database to execute the query on
     * @param statement the statement to be queried
     * @param handle determines which data to return from a {@link ResultSet}
     * @param <T> the Type of object being returned
     * @return the data from a {@link ResultSet} wrapped in an {@link Optional}. {@link Optional#empty()} otherwise.
     */
    public static <T> Optional<T> returnQuery(@Nonnull final Database database,
                                              @Nonnull final String statement,
                                              @Nonnull final ReturnResults<T> handle) {
        return returnQuery(database, statement, s -> {}, handle);
    }

    /**
     * Returns the data gathered from a query.
     *
     * @param database the database to execute the query on
     * @param statement the statement to be queried
     * @param preparer the {@link Preparer} to set the data in the statement
     * @param handle determines which data to return from a {@link ResultSet}
     * @param <T> the Type of object being returned
     * @return the data from a {@link ResultSet} wrapped in an {@link Optional}. {@link Optional#empty()} otherwise.
     */
    public static <T> Optional<T> returnQuery(@Nonnull final Database database,
                                              @Nonnull final String statement,
                                              @Nonnull final Preparer preparer,
                                              @Nonnull final ReturnResults<T> handle) {
        Connection c = null;
        PreparedStatement s = null;
        ResultSet r = null;

        LOGGER.debug("Attempting to query: {}", statement);
        try {
            c = database.getConnection();
            s = c.prepareStatement(statement);
            preparer.prepare(s);
            r = s.executeQuery();
            final Optional<T> results = Optional.ofNullable(handle.execute(r));
            LOGGER.debug("Finished querying: {}", statement);
            return results;
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
     *     <li>{@link PreparedStatement}</li>
     *     <li>{@link ResultSet}</li>
     * </ul>
     *
     * @param statement the statement that was queried or executed
     * @param c the {@link Connection} that was used
     * @param s the {@link Statement} object generated from the statement
     * @param r the {@link ResultSet} that was gathered from a query
     */
    private static void close(@Nonnull final String statement,
                              @Nullable final Connection c,
                              @Nullable final Statement s,
                              @Nullable final ResultSet r) {
        if (r != null) try {
            r.close();
        } catch (final SQLException e) {
            LOGGER.error("Failed to close ResultSet for statement: {}", statement);
        }

        if (s != null) try {
            s.close();
        } catch (final SQLException e) {
            LOGGER.error("Failed to close Statement for statement: {}", statement);
        }

        if (c != null) try {
            c.close();
        } catch (final SQLException e) {
            LOGGER.error("Failed to close Connection for statement: {}", statement);
        }

        LOGGER.debug("Finished closing objects related to statement: {}", statement);
    }
}
