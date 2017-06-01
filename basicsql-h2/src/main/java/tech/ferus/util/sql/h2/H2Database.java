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
package tech.ferus.util.sql.h2;

import tech.ferus.util.sql.api.Database;
import tech.ferus.util.sql.core.BasicDatabase;

import javax.annotation.Nonnull;

/**
 * The {@link Database} implementation for H2.
 */
public class H2Database extends BasicDatabase {

    /**
     * The following part to: <code>jdbc:h2:</code>.
     *
     * <p>Unlike SQLite, this can be any number of things.</p>
     *
     * <p>For more information, read "Database URL Overview" at:<br>
     * http://www.h2database.com/html/features.html</p>
     */
    @Nonnull private final String path;

    /**
     * Determines whether or not the database being used is stored in memory.
     */
    private final boolean memory;

    /**
     * Constructs a {@link Database} for H2.
     */
    public H2Database() {
        this("", true);
    }

    /**
     * Constructs a {@link Database} for H2.
     *
     * @param path the data to follow <code>jdbc:h2:</code>
     */
    public H2Database(@Nonnull final String path) {
        super("h2");

        this.path = path;
        this.memory = false;
    }

    /**
     * Constructs a {@link Database} for H2.
     *
     * @param path the data to follow <code>jdbc:h2:</code> or <code>jdbc:h2:mem:</code>
     * @param memory whether or not this database is found in memory
     */
    public H2Database(@Nonnull final String path, final boolean memory) {
        super("h2");

        this.path = path;
        this.memory = memory;
    }

    /**
     * Gets the following part to: <code>jdbc:h2:</code>.
     *
     * <p>Unlike SQLite, this can be any number of things.</p>
     *
     * <p>For more information, read "Database URL Overview" at:<br>
     * http://www.h2database.com/html/features.html</p>
     *
     * @return the following part to: <code>jdbc:h2:</code>
     */
    @Nonnull
    public String getPath() {
        return this.path;
    }

    /**
     * Determines whether or not the {@link Database} is to be found in memory.
     *
     * @return true if the {@link Database} is found in memory; false otherwise
     */
    public boolean isMemory() {
        return this.memory;
    }

    @Override
    public void configure() {
        if (this.isMemory()) {
            this.getDataSource().setJdbcUrl("jdbc:h2:mem:" + this.path);
        } else {
            this.getDataSource().setJdbcUrl("jdbc:h2:" + this.path);
        }
    }
}
