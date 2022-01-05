package github.weichware10.util.db;

import github.weichware10.util.Logger;
import github.weichware10.util.ToolType;
import github.weichware10.util.config.CodeChartsConfiguration;
import github.weichware10.util.config.Configuration;
import github.weichware10.util.config.ZoomMapsConfiguration;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
    public Configuration get(String configId) {
        final String queryF = "SELECT * FROM %s.configurations WHERE configid LIKE '%s'";
        final String query = String.format(queryF, dataBaseClient.schema, configId);

        Configuration configuration = null;

        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;

        // get resultset
        try {
            conn = DriverManager.getConnection(dataBaseClient.url, dataBaseClient.props);
            st = conn.createStatement();
            rs = st.executeQuery(query);

            // nur wenn Ergebnis gefunden Verarbeitung starten
            if (rs.next()) {
                // bei jedem Typ existent
                ToolType toolType = ToolType.valueOf(rs.getString("tooltype"));
                String imageUrl = rs.getString("imageurl");
                boolean tutorial = rs.getBoolean("tutorial");
                String question = rs.getString("question");
                String intro = rs.getString("intro");
                String outro = rs.getString("outro");

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
                            strings, initialSize, timings, tutorial);

                    // komplette Konfiguration zurückgeben
                    configuration = new Configuration(
                            configId, question, imageUrl, intro, outro, codeChartsConfiguration);
                } else {
                    // ZOOMMAPS spezifische Werte
                    double speed = rs.getDouble("speed");
                    double imageViewWidth = rs.getDouble("imageview_width");
                    double imageViewHeight = rs.getDouble("imageview_height");

                    // ZoomMapsConfiguration erstellen
                    ZoomMapsConfiguration zoomMapsConfiguration = new ZoomMapsConfiguration(
                            speed, imageViewWidth, imageViewHeight, tutorial);

                    // komplette Konfiguration zurückgeben
                    configuration = new Configuration(
                            configId, question, imageUrl, intro, outro, zoomMapsConfiguration);
                }
            }

        } catch (SQLException e) {
            Logger.error("SQLException when executing getConfiguration", e);
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
    public String set(Configuration configuration) {
        final String queryF = String.format("""
                INSERT INTO %s.configurations
                (configid, tooltype, tutorial, question, imageurl,
                intro, outro,
                strings, initialsize_x, initialsize_y, timings_0, timings_1,
                imageview_width, imageview_height, speed)
                VALUES
                (?, ?, ?, ?, ?,
                ?, ?,
                ?, ?, ?, ?, ?,
                ?, ?, ?);""", dataBaseClient.schema);

        final String uniqueException =
                "ERROR: duplicate key value violates unique constraint \"configurations_pkey\"";

        boolean idUnique = false;
        String configId = null;

        Connection conn = null;
        PreparedStatement pst = null;

        while (!idUnique) {
            idUnique = true;
            configId = Util.generateId("con_", 7);
            try {
                conn = DriverManager.getConnection(dataBaseClient.url, dataBaseClient.props);
                pst = conn.prepareStatement(queryF);
                pst.setString(1, configId);
                // TODO: zu setBoolean ändern
                pst.setBoolean(3, configuration.getTutorial());
                pst.setString(4, configuration.getQuestion());
                pst.setString(5, configuration.getImageUrl());
                pst.setString(6, configuration.getIntro());
                pst.setString(7, configuration.getOutro());

                if (configuration.getToolType() == ToolType.CODECHARTS) {
                    CodeChartsConfiguration ccConfig = configuration.getCodeChartsConfiguration();
                    pst.setString(2, "CODECHARTS");
                    // Felder für CodeCharts
                    pst.setString(8, ccConfig.getStrings().toString());
                    pst.setInt(9, ccConfig.getInitialSize()[0]);
                    pst.setInt(10, ccConfig.getInitialSize()[1]);
                    pst.setLong(11, ccConfig.getTimings()[0]);
                    pst.setLong(12, ccConfig.getTimings()[1]);
                    // Felder für ZoomMaps
                    pst.setNull(13, java.sql.Types.DOUBLE);
                    pst.setNull(14, java.sql.Types.DOUBLE);
                    pst.setNull(15, java.sql.Types.DOUBLE);
                } else {
                    ZoomMapsConfiguration zmConfig = configuration.getZoomMapsConfiguration();
                    pst.setString(2, "ZOOMMAPS");
                    // Felder für CodeCharts
                    pst.setNull(8, java.sql.Types.LONGVARCHAR);
                    pst.setNull(9, java.sql.Types.INTEGER);
                    pst.setNull(10, java.sql.Types.INTEGER);
                    pst.setNull(11, java.sql.Types.BIGINT);
                    pst.setNull(12, java.sql.Types.BIGINT);
                    // Felder für ZoomMaps
                    pst.setDouble(13, zmConfig.getImageViewWidth());
                    pst.setDouble(14, zmConfig.getImageViewHeight());
                    pst.setDouble(15, zmConfig.getSpeed());
                }
                pst.executeUpdate();
            } catch (SQLException e) {
                if (e.toString().contentEquals(uniqueException)) {
                    idUnique = false;
                    Logger.info("duplicate key (configId)", e);
                } else {
                    Logger.error("SQLException when executing setConfiguration", e);
                }
            } finally {
                Util.closeQuietly(pst);
                Util.closeQuietly(conn);
            }
        }
        return configId;
    }

    /**
     * Überprüft, ob configID exisitiert.
     *
     * @param configId - ID der Konfiguration
     * @return Verfügbarkeitsboolean
     *
     * @since v0.3
     */
    public boolean getAvailability(String configId) {
        // Benutzung von tutorial um möglichst wenig Datenverbrauch zu erreichen
        final String queryF = "SELECT tutorial FROM %s.configurations WHERE configid LIKE '%s'";
        final String query = String.format(queryF, dataBaseClient.schema, configId);

        boolean availabilty = false;

        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(dataBaseClient.url, dataBaseClient.props);
            st = conn.createStatement();
            rs = st.executeQuery(query);

            // wenn es existiert, besteht Möglichkeit, dass availability true ist
            if (rs.next()) {
                availabilty = true;
            }
        } catch (SQLException e) {
            Logger.error("SQLException when executing getTrialAvailability", e);
        } finally {
            Util.closeQuietly(rs);
            Util.closeQuietly(st);
            Util.closeQuietly(conn);
        }

        return availabilty;
    }
}
