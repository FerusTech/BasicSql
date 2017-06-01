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

import javax.annotation.Nonnull;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Contains and manages the configured default {@link Database}.
 */
public final class DefaultDatabase {

    /**
     * The default database, to be set by utilizing developers.
     */
    private static Database defaultDatabase = null;

    /**
     * Gets the connection that has been configured by the default {@link Database}.
     *
     * @return the connection to the configured {@link Database}
     * @throws SQLException if there is an exception during the connection or in syntax
     */
    public static Connection getConnection() throws SQLException {
        if (DefaultDatabase.isDefaultDatabaseSet()) {
            throw new IllegalStateException("Default database hasn't been set.");
        }

        return DefaultDatabase.defaultDatabase.getConnection();
    }

    /**
     * Gets the configured default {@link Database}.
     *
     * @return the configured default {@link Database}
     */
    public static Database getDatabase() {
        return DefaultDatabase.defaultDatabase;
    }

    /**
     * Determines whether or not a default {@link Database} has been configured.
     *
     * @return true if a {@link Database} has been set as default
     */
    public static boolean isDefaultDatabaseSet() {
        return DefaultDatabase.defaultDatabase != null;
    }

    /**
     * Sets the default {@link Database}.
     *
     * @param defaultDatabase the {@link Database} to be set as default
     */
    public static void setDefaultDatabase(@Nonnull final Database defaultDatabase) {
        DefaultDatabase.defaultDatabase = defaultDatabase;
    }

    /**
     * Determines whether or not the provided {@link Database} is configured
     * as the default database.
     *
     * @param database the {@link Database} to check for default status
     * @return true if the provided {@link Database} is set as default; false otherwise
     */
    public static boolean isDefaultDatabase(@Nonnull final Database database) {
        return DefaultDatabase.isDefaultDatabaseSet()
                && DefaultDatabase.defaultDatabase == database;
    }
}
