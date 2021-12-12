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

/**
 * Die Configurations-Tabelle beinhaltet die gespeicherten Konfigurationen.
 */
public class Configurations {
    private final DataBaseClient dataBaseClient;

    /**
     * Erstellt eine neue Verbindung zur Configurations-Tabelle.
     *
     * @param dataBaseClient - übergeordneter DataBadeClient
     */
    protected Configurations(DataBaseClient dataBaseClient) {
        this.dataBaseClient = dataBaseClient;
    }

    /**
     * Gibt die Konfiguration mit der configId aus der Datenbank zurück.
     *
     * @param configId - ID der Konfiguration
     * @return Konfigurationsobjekt
     */
    public Configuration getConfiguration(String configId) {
        final String query = "SELECT * FROM %s.configurations WHERE configid LIKE '%s'";

        Configuration configuration = null;

        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;

        // get resultset
        try {
            conn = DriverManager.getConnection(dataBaseClient.url, dataBaseClient.props);
            st = conn.createStatement();
            rs = st.executeQuery(String.format(query, dataBaseClient.schema, configId));

            // nur wenn Ergebnis gefunden Verarbeitung starten
            if (rs.next()) {
                // bei jedem Typ existent
                ToolType toolType = ToolType.valueOf(rs.getString("tooltype"));
                List<String> imageUrls = Util.stringsToList(rs.getString("imageurls"));
                boolean tutorial = (rs.getInt("tutorial") == 1) ? true : false;
                String question = rs.getString("question");

                if (toolType == ToolType.CODECHARTS) {
                    // CODECHARTS spezifische Werte
                    List<String> strings = Util.stringsToList(rs.getString("strings"));
                    int[] initialSize = new int[] {
                            rs.getInt("initialsize_x"),
                            rs.getInt("initialsize_y") };
                    long[] timings = new long[] {
                            rs.getLong("timings_0"),
                            rs.getLong("timings_1") };

                    // CodeChartsConfiguration erstellen
                    CodeChartsConfiguration codeChartsConfiguration = new CodeChartsConfiguration(
                            strings, initialSize, timings, tutorial, imageUrls);

                    // komplette Konfiguration zurückgeben
                    configuration = new Configuration(configId, question, codeChartsConfiguration);
                } else {
                    // ZOOMMAPS spezifische Werte
                    float speed = rs.getFloat("speed");

                    // ZoomMapsConfiguration erstellen
                    ZoomMapsConfiguration zoomMapsConfiguration = new ZoomMapsConfiguration(
                            speed, tutorial, imageUrls);

                    // komplette Konfiguration zurückgeben
                    configuration = new Configuration(configId, question, zoomMapsConfiguration);
                }
            }

        } catch (SQLException e) {
            Logger.error("SQLException when executing query", e);
        } finally {
            Util.closeQuietly(rs);
            Util.closeQuietly(st);
            Util.closeQuietly(conn);
        }

        return configuration;
    }

    /**
     * Fügt die Konfiguration der Datenbank hinzu.
     *
     * @param configuration - Konfiguration die hinzugefügt werden soll
     * @return Erstellte ID der hinzugefügten Konfiguration
     */
    public String setConfiguration(Configuration configuration) {
        final String ccQuery = """
                INSERT INTO %s.configurations
                (configid, tooltype, tutorial, question, imageurls,
                strings, initialsize_x, initialsize_y, timings_0, timings_1, speed)
                VALUES
                ('%s', '%s', %d, '%s', '%s',
                '%s', %d, %d, %d, %d, null);""";

        final String zmQuery = """
                INSERT INTO %s.configurations
                (configid, tooltype, tutorial, question, imageurls,
                strings, initialsize_x, initialsize_y, timings_0, timings_1, speed)
                VALUES
                ('%s', '%s', %d, '%s', '%s',
                null, null, null, null, null, %f);""";

        final String uniqueException =
                "ERROR: duplicate key value violates unique constraint \"configurations_pkey\"";

        boolean idUnique = false;
        String configId = null;

        Connection conn = null;
        Statement st = null;

        while (!idUnique) {
            idUnique = true;
            configId = Util.generateId("con_", 5);
            try {
                conn = DriverManager.getConnection(dataBaseClient.url, dataBaseClient.props);
                st = conn.createStatement();
                if (configuration.getToolType() == ToolType.CODECHARTS) {
                    CodeChartsConfiguration ccConfiguration =
                            configuration.getCodeChartsConfiguration();
                    st.executeUpdate(String.format(ccQuery,
                            dataBaseClient.schema,
                            configId,
                            "CODECHARTS",
                            ccConfiguration.getTutorial() ? 1 : 0,
                            configuration.getQuestion(),
                            ccConfiguration.getImageUrls(),
                            ccConfiguration.getStrings(),
                            ccConfiguration.getInitialSize()[0],
                            ccConfiguration.getInitialSize()[1],
                            ccConfiguration.getTimings()[0],
                            ccConfiguration.getTimings()[1]
                    ));
                } else {
                    ZoomMapsConfiguration zmConfiguration =
                            configuration.getZoomMapsConfiguration();
                    st.executeUpdate(String.format(zmQuery,
                            dataBaseClient.schema,
                            configId,
                            "ZOOMMAPS",
                            zmConfiguration.getTutorial() ? 1 : 0,
                            configuration.getQuestion(),
                            zmConfiguration.getImageUrls(),
                            zmConfiguration.getSpeed()));
                }
            } catch (SQLException e) {
                if (e.toString().contentEquals(uniqueException)) {
                    idUnique = false;
                    Logger.info("duplicate key (configId)", e);
                } else {
                    Logger.error("SQLException when executing query", e);
                }
            } finally {
                Util.closeQuietly(st);
                Util.closeQuietly(conn);
            }
        }
        return configId;
    }
}
