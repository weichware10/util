package github.weichware10.util.db;

import github.weichware10.util.Logger;
import java.security.InvalidParameterException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Datenbank-Klasse, durch die auf die Datenbank zugegriffen wird.
 */
public class DataBaseClient {
    private final String url;
    private Properties props = new Properties();
    public final Configurations configurations;
    public final Trials trials;
    public final Datapoints datapoints;

    /**
     * Erstellt einen neuen DatenbankClient.
     *
     * @param url - URL des Servers/Datenbank
     * @param user - Benutzername
     * @param password - Passwort
     */
    public DataBaseClient(String url, String user, String password) {
        this.url = url;
        props.setProperty("user", user);
        props.setProperty("password", password);

        // Verbindung ausprobieren
        try {
            Connection conn = DriverManager.getConnection(url, props);
            conn.close();
        } catch (SQLException e) {
            Logger.warn("Couldn't connect to server", e);
            throw new InvalidParameterException("Couldn't connect to server");
        }

        // Tabellen
        this.configurations = new Configurations(url, props);
        this.trials = new Trials(url, props);
        this.datapoints = new Datapoints(url, props);
    }
}
