package github.weichware10.util.db;

import github.weichware10.util.Logger;
import java.security.InvalidParameterException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * Datenbank-Klasse, durch die auf die Datenbank zugegriffen wird.
 */
public class DataBaseClient {

    protected final String url;
    protected final Properties props;
    protected final String schema;

    public final Configurations configurations;
    public final Trials trials;
    public final Datapoints datapoints;

    /**
     * Erstellt einen neuen DatenbankClient.
     *
     * @param url - URL des Servers/Datenbank
     * @param user - Benutzername
     * @param password - Passwort
     * @param schema - Das zu verwendende Schema
     */
    public DataBaseClient(String url, String user, String password, String schema) {
        this.url = url;
        this.schema = schema;
        props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", password);

        // Verbindung ausprobieren
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, props);
        } catch (SQLException e) {
            Logger.info("Couldn't connect to server", e);
            throw new InvalidParameterException("Couldn't connect to server: " + e.getMessage());
        } finally {
            Util.closeQuietly(conn);
        }

        // Zugriff überprüfen
        final List<String> tables = Arrays.asList("configurations", "trials", "datapoints");
        for (String table : tables) {
            if (!hasAccess(table)) {
                String msg = String.format("Couldn't find table %s.%s", schema, table);
                throw new InvalidParameterException(msg);
            }
        }

        // Tabellen
        this.configurations = new Configurations(this);
        this.trials = new Trials(this);
        this.datapoints = new Datapoints(this);
    }

    /**
     * Überprüft, ob Zugriff auf die spezifizierte Tabelle im Schema besteht.
     *
     * @param table - die zu überprüfende Tabelle
     * @return Zugriffsboolean
     */
    private boolean hasAccess(String table) {
        // query
        final String queryFormat = "SELECT * FROM %s.%s LIMIT 1";
        final String query = String.format(queryFormat, schema, table);

        // result
        boolean exists = false;

        // database objects
        Connection conn = null;
        Statement st = null;

        try {
            conn = DriverManager.getConnection(url, props);
            st = conn.createStatement();
            st.executeQuery(query);
            // kein Error aufgetreten -> Tabelle existiert und Zugriff besteht
            exists = true;
        } catch (SQLException e) {
            Logger.info("SQLException when checking access", e);
        } finally {
            Util.closeQuietly(st);
            Util.closeQuietly(conn);
        }

        return exists;
    }
}
