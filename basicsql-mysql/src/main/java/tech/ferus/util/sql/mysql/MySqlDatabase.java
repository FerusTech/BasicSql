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
package tech.ferus.util.sql.mysql;

import tech.ferus.util.sql.api.Database;
import tech.ferus.util.sql.core.BasicDatabase;

import javax.annotation.Nonnull;

/**
 * The {@link Database} implementation for MySQL.
 */
public class MySqlDatabase extends BasicDatabase {

    /**
     * The location of the remote/local host of this database.
     */
    @Nonnull private final String host;

    /**
     * The port used to connect to this database.
     */
    private final int port;

    /**
     * The name of the database that you're connecting to.
     */
    @Nonnull private final String database;

    /**
     * The username of the database that you're connecting to.
     */
    @Nonnull private final String username;

    /**
     * The password for the user of the database that you're connecting to.
     */
    @Nonnull private final String password;

    /**
     * Constructs a {@link Database} for MySQL.
     *
     * @param host the location of the remote/local host of this database
     * @param port the port used to connect to this database
     * @param database the name of the database that you're connecting to
     * @param username the username of the database that you're connecting to
     * @param password the password for the user of the database that you're connecting to
     */
    public MySqlDatabase(@Nonnull final String host,
                         final int port,
                         @Nonnull final String database,
                         @Nonnull final String username,
                         @Nonnull final String password) {
        super("mysql");

        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;

        this.configure();
    }

    @Override
    public void configure() {
        this.getDataSource().setJdbcUrl("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database +
                "?autoReconnect=true&useSSL=false&rewriteBatchedStatements=true");
        this.getDataSource().setUser(this.username);
        this.getDataSource().setPassword(this.password);
    }

    /**
     * Gets the location of the remote/local host of this database.
     *
     * @return the location of the remote/local host of this database
     */
    @Nonnull
    public String getHost() {
        return this.host;
    }

    /**
     * Gets the port used to connect to this database.
     *
     * @return the port used to connect to this database
     */
    public int getPort() {
        return this.port;
    }

    /**
     * Gets the name of the database that you're connecting to.
     *
     * @return the name of the database that you're connecting to
     */
    @Nonnull
    public String getDatabase() {
        return this.database;
    }

    /**
     * Gets the username of the database that you're connecting to.
     *
     * @return the username of the database that you're connecting to
     */
    @Nonnull
    public String getUsername() {
        return this.username;
    }

    /**
     * Gets the password for the user of the database that you're connecting to.
     *
     * @return the password for the user of the database that you're connecting to
     */
    @Nonnull
    public String getPassword() {
        return this.password;
    }
}
