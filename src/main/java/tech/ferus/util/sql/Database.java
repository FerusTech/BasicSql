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

import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.sql.Connection;
import java.sql.SQLException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * A simple {@link Connection} getter for c3p0.
 */
public class Database {

    /**
     * The default database, to be set by utilizing developers.
     */
    @Nullable private static Database defaultDatabase = null;

    /**
     * The generated Source.
     */
    @Nonnull private final ComboPooledDataSource source;

    /**
     * Constructs a new {@link Database}.
     *
     * @param host the url or IP Address to which the host is located
     * @param port the port that's used to access the server
     * @param database the name of the accessing database
     * @param username the name of an active user on the server
     * @param password the password for the active user
     */
    public Database(final String host,
                    final int port,
                    final String database,
                    final String username,
                    final String password) {
        this.source = new ComboPooledDataSource();

        this.source.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + database +
                "?autoReconnect=true&useSSL=false&rewriteBatchedStatements=true");

        this.source.setUser(username);
        this.source.setPassword(password);
    }

    /**
     * Gets the connection that has been configured by this {@link Database}.
     *
     * @return the connection to the configured {@link Database}
     * @throws SQLException if there is an exception during the connection or in syntax
     */
    public Connection getConnection() throws SQLException {
        return this.source.getConnection();
    }

    /**
     * Gets the connection that has been configured by the default {@link Database}.
     *
     * @return the connection to the configured {@link Database}
     * @throws SQLException if there is an exception during the connection or in syntax
     */
    public static Connection getDefaultConnection() throws SQLException {
        if (Database.defaultDatabase == null) {
            throw new IllegalStateException("Default database hasn't been set.");
        }

        return Database.defaultDatabase.source.getConnection();
    }

    /**
     * Gets the default {@link Database}.
     *
     * @return the default {@link Database}
     */
    @Nullable public static Database getDefaultDatabase() {
        return Database.defaultDatabase;
    }

    /**
     * Determines whether or not a default {@link Database} has been configured.
     *
     * @return true if a {@link Database} has been set as default
     */
    public static boolean isDefaultDatabaseSet() {
        return Database.defaultDatabase != null;
    }

    /**
     * Sets the default {@link Database}.
     *
     * @param database the {@link Database} to be set as default
     */
    public static void setDefaultDatabase(@Nonnull final Database database) {
        Database.defaultDatabase = database;
    }
}
