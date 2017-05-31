package tech.ferus.util.sql.databases;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.annotation.Nonnull;

/**
 * A {@link Database} implementation for MySQL.
 */
public class MySqlDatabase extends Database {

    @Nonnull private final String host;
    private final int port;
    @Nonnull private final String database;
    @Nonnull private final String username;
    @Nonnull private final String password;

    /**
     * Constructs a new {@link Database}.
     *
     * @param host the url or IP Address to which the host is located
     * @param port the port that's used to access the server
     * @param database the name of the accessing database
     * @param username the name of an active user on the server
     * @param password the password for the active user
     */
    public MySqlDatabase(@Nonnull final String host,
                         final int port,
                         @Nonnull final String database,
                         @Nonnull final String username,
                         @Nonnull final String password) {
        super(Protocol.MYSQL);
        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
    }

    /**
     * Gets the host for the MySQL database.
     *
     * @return the MySQL database host
     */
    @Nonnull public String getHost() {
        return this.host;
    }

    /**
     * Gets the port for the MySQL database.
     *
     * @return the MySQL database port
     */
    public int getPort() {
        return this.port;
    }

    /**
     * Gets the database name for the MySQL database.
     *
     * @return the MySQL database name
     */
    @Nonnull public String getDatabase() {
        return this.database;
    }

    /**
     * Gets the user for the MySQL database.
     *
     * @return the MySQL database user
     */
    @Nonnull public String getUsername() {
        return this.username;
    }

    /**
     * Gets the password for the user for the MySQL database.
     *
     * @return the MySQL database user's password
     */
    @Nonnull public String getPassword() {
        return this.password;
    }

    /**
     * Configures the {@link ComboPooledDataSource}.
     *
     * @param source the {@link ComboPooledDataSource} to configure
     */
    @Override protected void configure(@Nonnull final ComboPooledDataSource source) {
        source.setJdbcUrl("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database +
                "?autoReconnect=true&useSSL=false&rewriteBatchedStatements=true");
        source.setUser(this.username);
        source.setPassword(this.password);
    }
}
