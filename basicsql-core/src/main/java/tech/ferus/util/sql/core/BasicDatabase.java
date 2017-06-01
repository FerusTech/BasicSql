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

import com.mchange.v2.c3p0.ComboPooledDataSource;
import javax.annotation.Nonnull;
import java.sql.Connection;
import java.sql.SQLException;

public abstract class BasicDatabase implements Database {

    /**
     * The protocol this database is using.
     */
    @Nonnull private final String protocol;

    /**
     * The generated (or provided) {@link ComboPooledDataSource} for this database.
     */
    @Nonnull private final ComboPooledDataSource dataSource;

    /**
     * Whether or not this database has been configured.
     */
    private boolean configured = false;

    /**
     * Constructs a new {@link Database}.
     *
     * @param protocol The type of {@link Database} being constructed
     */
    public BasicDatabase(@Nonnull final String protocol) {
        this.protocol = protocol;
        this.dataSource = new ComboPooledDataSource();
    }

    @Nonnull
    @Override
    public String getProtocol() {
        return this.protocol;
    }

    @Nonnull
    @Override
    public ComboPooledDataSource getDataSource() {
        if (!this.configured) {
            this.configure();
            this.configured = true;
        }

        return this.dataSource;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return this.getDataSource().getConnection();
    }

    @Override
    public boolean isDefaultDatabase() {
        return DefaultDatabase.isDefaultDatabase(this);
    }

    @Override
    public boolean isConfigured() {
        return this.configured;
    }
}
