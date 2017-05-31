package tech.ferus.util.sql.databases;

/**
 * The type of databases that BasicSql supports.
 */
public enum Protocol {

    MYSQL("mysql"),
    SQLITE("sqlite"),
    ;

    /**
     * The protocol type for the JDBC URL.
     */
    private final String protocol;

    /**
     * Constructs a new Protocol enum.
     *
     * @param protocol the protocol type for JDBC
     */
    Protocol(final String protocol) {
        this.protocol = protocol;
    }

    /**
     * Gets the protocol type for the JDBC URL.
     *
     * @return the protocol type for JDBC
     */
    public String getProtocol() {
        return this.protocol;
    }
}
