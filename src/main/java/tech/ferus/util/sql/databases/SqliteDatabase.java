package tech.ferus.util.sql.databases;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.annotation.Nonnull;

/**
 * {@link Database} implementation for SQLite.
 */
public class SqliteDatabase extends Database {

    /**
     * The path for the SQLite database.
     */
    @Nonnull private final String path;

    /**
     * Constructs a new {@link Database} in memory.
     */
    public SqliteDatabase() {
        this("");
    }

    /**
     * Constructs a new {@link Database} in a file.
     *
     * @param path the path to the SQLite file
     */
    public SqliteDatabase(@Nonnull final String path) {
        super(Protocol.SQLITE);
        this.path = path;
    }

    /**
     * Gets whether or not the database is located in memory.
     *
     * @return true if the database is in memory; false, otherwise
     */
    public boolean isMemory() {
        return this.path.isEmpty();
    }

    /**
     * Gets the path to the SQLite database.
     *
     * <p>Path will be empty, if database is in memory.</p>
     *
     * @return the path to the database; empty if in memory
     */
    @Nonnull public String getPath() {
        return this.path;
    }

    /**
     * Configures the {@link ComboPooledDataSource}.
     *
     * @param source the {@link ComboPooledDataSource} to configure
     */
    @Override protected void configure(@Nonnull final ComboPooledDataSource source) {
        if (this.isMemory()) {
            source.setJdbcUrl("jdbc:sqlite::memory");
        } else {
            source.setJdbcUrl("jdbc:sqlite:" + this.path);
        }
    }
}
