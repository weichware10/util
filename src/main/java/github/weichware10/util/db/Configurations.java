package github.weichware10.util.db;

import github.weichware10.util.Enums.ToolType;
import github.weichware10.util.Logger;
import github.weichware10.util.config.CodeChartsConfiguration;
import github.weichware10.util.config.Configuration;
import github.weichware10.util.config.ZoomMapsConfiguration;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

/**
 * Die Configurations-Tabelle beinhaltet die gespeicherten Konfigurationen.
 */
public class Configurations {
    private final String url;
    private final Properties props;

    /**
     * Erstellt eine neue Verbindung zur Configurations-Tabelle.
     *
     * @param url - URL der Datenbank
     * @param props - Benutzername und Passwort
     */
    protected Configurations(String url, Properties props) {
        this.url = url;
        this.props = props;
    }

    /**
     * Gibt die Konfiguration mit der configId aus der Datenbank zurück.
     *
     * @param configId - ID der Konfiguration
     * @return Konfigurationsobjekt
     */
    public Configuration getConfiguration(String configId) {
        final String query = "SELECT * FROM configurations WHERE configid LIKE %s";

        ResultSet rs;

        // get resultset
        try {
            Connection conn = DriverManager.getConnection(url, props);
            Statement st = conn.createStatement();
            rs = st.executeQuery(String.format(query, configId));
        } catch (SQLException e) {
            Logger.error("SQLException when executing query", e);
            return null;
        }

        // process resultset
        try {
            // kein Ergebnis gefunden
            if (!rs.next()) {
                return null;
            }

            // bei jedem Typ existent
            ToolType toolType = ToolType.valueOf(rs.getString("tooltype"));
            List<String> imageUrls = Util.stringsToList(rs.getString("imageurls"));
            boolean tutorial = (rs.getInt("tutorial") == 1) ? true : false;

            if (toolType == ToolType.CODECHARTS) {
                // CODECHARTS spezifische Werte
                List<String> strings = Util.stringsToList(rs.getString("strings"));
                int[] initialSize = new int[] {
                        rs.getInt("initialsize_x"),
                        rs.getInt("initialsize_y") };
                long[] timings = new long[] { rs.getLong("timings0"), rs.getLong("timings1") };

                // CodeChartsConfiguration erstellen
                CodeChartsConfiguration codeChartsConfiguration = new CodeChartsConfiguration(
                        strings, initialSize, timings, tutorial, imageUrls);

                // komplette Konfiguration zurückgeben
                return new Configuration(configId, codeChartsConfiguration);
            } else {
                // ZOOMMAPS spezifische Werte
                float speed = rs.getFloat("speed");

                // ZoomMapsConfiguration erstellen
                ZoomMapsConfiguration zoomMapsConfiguration = new ZoomMapsConfiguration(
                        speed, tutorial, imageUrls);

                // komplette Konfiguration zurückgeben
                return new Configuration(configId, zoomMapsConfiguration);
            }
        } catch (SQLException e) {
            Logger.error("SQLException when processing query result", e);
            return null;
        }
    }

    public String setConfiguration(Configuration configuration) {
        final String ccQuery = """
                INSERT INTO configurations
                (configid, tooltype, tutorial, question, imageurls,
                strings, initialsize_x, initialsize_y, timings_0, timings_1, speed)
                VALUES
                ('%s', '%s', %d, '%s', '%s',
                '%s', %d, %d, %d, %d, null)""";

        final String zmQuery = """
                INSERT INTO configurations
                (configid, tooltype, tutorial, question, imageurls,
                strings, initialsize_x, initialsize_y, timings_0, timings_1, speed)
                VALUES
                ('%s', '%s', %d, '%s', '%s',
                null, null, null, null, null, %f)""";

        final String uniqueException =
                "ERROR: duplicate key value violates unique constraint \"configurations_pkey\"";

        boolean idUnique = false;
        String id = null;

        while (!idUnique) {
            idUnique = true;
            id = Util.generateId("con_", 5);
            try {
                Connection conn = DriverManager.getConnection(url, props);
                Statement st = conn.createStatement();
                if (configuration.getToolType() == ToolType.CODECHARTS) {
                    st.executeQuery(String.format(zmQuery, id, "CODECHARTS", 1, "tst", "tst", 3.0f));
                } else {
                    st.executeQuery(String.format(zmQuery, id, "ZOOMMAPS", 1, "tst", "tst", 3.0f));
                }
            } catch (SQLException e) {
                if (e.toString().contentEquals(uniqueException)) {
                    idUnique = false;
                }
                Logger.error("SQLException when executing query", e);
            }
        }
        return id;
    }
}
