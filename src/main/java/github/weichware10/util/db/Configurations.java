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
                String question = rs.getString("question");
                String intro = rs.getString("intro");
                String outro = rs.getString("outro");
                boolean tutorial = rs.getBoolean("tutorial");

                if (toolType == ToolType.CODECHARTS) {
                    // CODECHARTS spezifische Werte
                    String stringId = rs.getString("stringid");
                    int[] initialSize = new int[] {
                            rs.getInt("initialsize_x"),
                            rs.getInt("initialsize_y") };
                    long[] timings = new long[] {
                            rs.getLong("timings_0"),
                            rs.getLong("timings_1") };
                    boolean showGrid = rs.getBoolean("show_grid");
                    boolean relativeSize = rs.getBoolean("relative_size");
                    boolean randomized = rs.getBoolean("randomized");
                    int iterations = rs.getInt("iterations");
                    int maxDepth = rs.getInt("max_depth");
                    int defaultHorizontal = rs.getInt("default_horizontal");
                    int defaultVertical = rs.getInt("default_vertical");

                    List<String> strings = dataBaseClient.strings.get(stringId);

                    // CodeChartsConfiguration erstellen
                    CodeChartsConfiguration codeChartsConfiguration = new CodeChartsConfiguration(
                            stringId, strings, initialSize, timings, showGrid, relativeSize,
                            randomized, maxDepth, iterations, defaultHorizontal, defaultVertical);

                    // komplette Konfiguration zurückgeben
                    configuration = new Configuration(configId, question, imageUrl, intro, outro,
                            tutorial, codeChartsConfiguration);
                } else {
                    // ZOOMMAPS spezifische Werte
                    double speed = rs.getDouble("speed");
                    double imageViewWidth = rs.getDouble("imageview_width");
                    double imageViewHeight = rs.getDouble("imageview_height");

                    // ZoomMapsConfiguration erstellen
                    ZoomMapsConfiguration zoomMapsConfiguration = new ZoomMapsConfiguration(
                            speed, imageViewWidth, imageViewHeight);

                    // komplette Konfiguration zurückgeben
                    configuration = new Configuration(configId, question, imageUrl, intro, outro,
                            tutorial, zoomMapsConfiguration);
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
                stringid, initialsize_x, initialsize_y, timings_0, timings_1,
                show_grid, relative_size, randomized,
                iterations, max_depth, default_horizontal, default_vertical,
                imageview_width, imageview_height, speed)
                VALUES
                (?, ?, ?, ?, ?,
                ?, ?,
                ?, ?, ?, ?, ?,
                ?, ?, ?,
                ?, ?, ?, ?,
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
                pst.setBoolean(3, configuration.getTutorial());
                pst.setString(4, configuration.getQuestion());
                pst.setString(5, configuration.getImageUrl());
                pst.setString(6, configuration.getIntro());
                pst.setString(7, configuration.getOutro());

                if (configuration.getToolType() == ToolType.CODECHARTS) {
                    CodeChartsConfiguration ccConfig = configuration.getCodeChartsConfiguration();

                    // strings setzen
                    if (dataBaseClient.strings.sizeOf(ccConfig.getStringId()) == 0) {
                        dataBaseClient.strings.set(ccConfig.getStringId(), ccConfig.getStrings());
                    }

                    pst.setString(2, "CODECHARTS");
                    // Felder für CodeCharts
                    pst.setString(8, ccConfig.getStringId());
                    pst.setInt(9, ccConfig.getInitialSize()[0]);
                    pst.setInt(10, ccConfig.getInitialSize()[1]);
                    pst.setLong(11, ccConfig.getTimings()[0]);
                    pst.setLong(12, ccConfig.getTimings()[1]);
                    pst.setBoolean(13, ccConfig.getShowGrid());
                    pst.setBoolean(14, ccConfig.getRelativeSize());
                    pst.setBoolean(15, ccConfig.getRandomized());
                    pst.setInt(16, ccConfig.getInterations());
                    pst.setInt(17, ccConfig.getMaxDepth());
                    pst.setInt(18, ccConfig.getDefaultHorizontal());
                    pst.setInt(19, ccConfig.getDefaultVertical());
                    // Felder für ZoomMaps
                    pst.setNull(20, java.sql.Types.DOUBLE);
                    pst.setNull(21, java.sql.Types.DOUBLE);
                    pst.setNull(22, java.sql.Types.DOUBLE);
                } else {
                    ZoomMapsConfiguration zmConfig = configuration.getZoomMapsConfiguration();
                    pst.setString(2, "ZOOMMAPS");
                    // Felder für CodeCharts
                    pst.setNull(8, java.sql.Types.VARCHAR);
                    pst.setNull(9, java.sql.Types.INTEGER);
                    pst.setNull(10, java.sql.Types.INTEGER);
                    pst.setNull(11, java.sql.Types.BIGINT);
                    pst.setNull(12, java.sql.Types.BIGINT);
                    pst.setNull(13, java.sql.Types.BOOLEAN);
                    pst.setNull(14, java.sql.Types.BOOLEAN);
                    pst.setNull(15, java.sql.Types.BOOLEAN);
                    pst.setNull(16, java.sql.Types.INTEGER);
                    pst.setNull(17, java.sql.Types.INTEGER);
                    pst.setNull(18, java.sql.Types.INTEGER);
                    pst.setNull(19, java.sql.Types.INTEGER);
                    // Felder für ZoomMaps
                    pst.setDouble(20, zmConfig.getImageViewWidth());
                    pst.setDouble(21, zmConfig.getImageViewHeight());
                    pst.setDouble(22, zmConfig.getSpeed());
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
