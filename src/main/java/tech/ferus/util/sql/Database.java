package tech.ferus.util.sql;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class Database {

    private static Database database = null;

    private final ComboPooledDataSource source;

    private Database(final String host,
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

    public Connection getConnection() throws SQLException {
        return this.source.getConnection();
    }

    public static Connection getDefaultConnection() throws SQLException {
        return Database.database.source.getConnection();
    }

    public static Database getDefaultDatabase() {
        return Database.database;
    }

    public static boolean isDefaultDatabaseSet() {
        return Database.database != null;
    }

    public static void setDefaultDatabase(final Database database) {
        Database.database = database;
    }
}
