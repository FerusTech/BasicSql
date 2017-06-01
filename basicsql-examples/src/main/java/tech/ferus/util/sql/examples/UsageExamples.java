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
package tech.ferus.util.sql.examples;

import tech.ferus.util.sql.api.Database;
import tech.ferus.util.sql.core.BasicSql;
import tech.ferus.util.sql.core.DefaultDatabase;
import tech.ferus.util.sql.mysql.MySqlDatabase;

public class UsageExamples {

    /*
        Setting a default database is easy.
     */
    public static void setDefaultDatabase() {
        DefaultDatabase.setDefaultDatabase(new MySqlDatabase(
                "localhost", 3306, "basicsql",
                "root", "secret-password"
        ));
    }

    /*
        Once you've set up a database, using it is simple.
     */
    public static void execute(final String statement) {
        BasicSql.execute(statement);
    }

    /*
        I understand, though, that some projects may require several different databases.
        So you can also specify one, without using the default.
     */
    private static final Database DATABASE = new MySqlDatabase("localhost", 3306, "basicsql", "root", "secret-password");
    public static void executeSpecific(final String statement) {
        BasicSql.execute(DATABASE, statement);
    }

    /*
        Of course, not all statements are so simple.
     */
    public static void executeComplicated() {
        BasicSql.execute("DELETE FROM users WHERE name=?", s -> {
            s.setString(1, "FerusGrim");
        });
    }

    /*
        Similarly, queries are also simple.
     */
    public static void query(final String query) {
        BasicSql.query(query, h -> {
            while (h.next()) {
                // Do something
            }
        });
    }

    /*
        But can, easily, be just as complicated.
     */
    public static void queryComplicated() {
        BasicSql.query("SELECT * FROM users WHERE name=?", s -> {
            s.setString(1, "FerusGrim");
        }, h -> {
            while (h.next()) {
                // Do something
            }
        });
    }

    /*
        Though, it's always possible that you need to RETURN the data from those queries.
     */
    public static int getAge(final String user) {
        return BasicSql.returnQuery("SELECT * FROM users WHERE name=?",
                s -> s.setString(1, user),
                r -> r.next() ? r.getInt("age") : -1)
                .orElse(-1);
    }
}
