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
package tech.ferus.util.sql.api;

import javax.annotation.Nonnull;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * A simple {@link Connection} getter and configure-r.
 */
public interface Database {

    /**
     * Gets the protocol this {@link Database} is using.
     *
     * @return the protocol this {@link Database} is using
     */
    @Nonnull String getProtocol();

    /**
     * Gets the generated or provided {@link DataSource}.
     *
     * @return the source this {@link Database} is using
     */
    @Nonnull DataSource getDataSource();

    /**
     * Configures the {@link Database} as needed.
     */
    void configure();

    /**
     * Gets whether or not the {@link Database} has been configured.
     *
     * @return true if the {@link Database} has been configured; false otherwise
     */
    boolean isConfigured();

    /**
     * Gets the connection that has been configured by this {@link Database}.
     *
     * @return the connection to the configured {@link Database}
     * @throws SQLException if there is an exception during the connection or in syntax
     */
    Connection getConnection() throws SQLException;

    /**
     * Determines whether or not this {@link Database} is set as default.
     *
     * @return true if this {@link Database} has been set as default; false otherwise
     */
    boolean isDefaultDatabase();
}
