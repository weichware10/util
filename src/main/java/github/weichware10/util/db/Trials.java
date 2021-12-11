package github.weichware10.util.db;

import github.weichware10.util.Enums.ToolType;
import github.weichware10.util.Logger;
import github.weichware10.util.data.TrialData;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;


/**
 * Die Trials-Tabelle beinhaltet die gespeicherten Trials.
 */
public class Trials {

    private final DataBaseClient dataBaseClient;

    protected Trials(DataBaseClient dataBaseClient) {
        this.dataBaseClient = dataBaseClient;
    }

    /**
     * Gibt config ID zurück, welche zu dem Versuch zugeordnet ist.
     *
     * @param trialId - Trial ID des Versuchs
     * @return ID der Konfiguration
     */
    public String getConfigId(String trialId) {
        final String query = "SELECT configid FROM trials WHERE trialid LIKE '%s';";

        String configId = null;

        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;

        // get resultset
        try {
            conn = DriverManager.getConnection(dataBaseClient.url, dataBaseClient.props);
            st = conn.createStatement();
            rs = st.executeQuery(String.format(query, trialId));
            if (rs.next()) {
                configId = rs.getString("configid");
            }
        } catch (SQLException e) {
            Logger.error("SQLException when executing getConfigId", e);
        }
        return configId;
    }

    /**
     * Gibt den Versuch zu einer trialID zurück (falls vorhanden).
     *
     * @param trialId - ID des Versuchs
     * @return TrialData zu trialId
     */
    public TrialData getTrial(String trialId) {
        final String query = """
                SELECT t.configid, c.tooltype, t.starttime, t.answer
                FROM trials AS t, configurations AS c
                WHERE t.configid LIKE c.configid AND t.trialid LIKE '%s'""";

        TrialData trialData = null;

        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(dataBaseClient.url, dataBaseClient.props);
            st = conn.createStatement();
            rs = st.executeQuery(String.format(query, trialId));

            // nur wenn Ergebnis gefunden Verarbeitung starten
            if (rs.next()) {
                // bei jedem Typ existent
                String configId = rs.getString("configid");
                ToolType toolType = ToolType.valueOf(rs.getString("tooltype"));
                DateTime startTime = new DateTime(rs.getTimestamp("starttime"));
                String answer = rs.getString("answer");

                // komplette Konfiguration zurückgeben
                trialData = new TrialData(
                        toolType,
                        trialId,
                        configId,
                        startTime,
                        answer,
                        dataBaseClient.datapoints.getDataPoints(trialId));
            }
        } catch (SQLException e) {
            Logger.error("SQLException when executing getTrial", e);
        } finally {
            Util.closeQuietly(rs);
            Util.closeQuietly(st);
            Util.closeQuietly(conn);
        }

        return trialData;
    }


    /**
     * Setzt die erhobenen Daten aus dem Versuch.
     *
     * @param trialData - erhobene Daten
     * @return Erfolgsboolean
     */
    public boolean setTrial(TrialData trialData) {
        final String query = """
                UPDATE trials
                SET
                starttime = '%s',
                answer = '%s'
                WHERE trialid LIKE '%s';""";

        boolean success = false;

        if (!getTrialAvailability(trialData.trialId)) {
            return success;
        }

        Connection conn = null;
        Statement st = null;

        try {
            conn = DriverManager.getConnection(dataBaseClient.url, dataBaseClient.props);
            st = conn.createStatement();
            st.executeUpdate(String.format(query,
                    new Timestamp(trialData.startTime.getMillis()),
                    trialData.answer,
                    trialData.trialId));
            // DATAPOINTS setzen
            dataBaseClient.datapoints.setDataPoints(trialData.getData(), trialData.trialId);
            success = true;
        } catch (SQLException e) {
            Logger.error("SQLException when executing setTrial", e);
        } finally {
            Util.closeQuietly(st);
            Util.closeQuietly(conn);
        }
        return success;
    }

    /**
     * Überprüft, ob trialID exisitiert und der Versuch dieser trialID bereits durchgeführt wurde.
     *
     * @param trialId - ID des Versuchs
     * @return Verfügbarkeitsboolean
     */
    public boolean getTrialAvailability(String trialId) {
        // Benutzung von starttime um möglichst wenig Datenverbrauch zu erreichen
        // timestamp hat meistens eine kleiner Größe als text
        final String query = "SELECT starttime FROM trials WHERE trialid LIKE '%s'";

        boolean availabilty = false;

        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(dataBaseClient.url, dataBaseClient.props);
            st = conn.createStatement();
            rs = st.executeQuery(String.format(query, trialId));

            // wenn es existiert, besteht Möglichkeit, dass availability true ist
            if (rs.next()) {
                Logger.info("rs.next()");
                // falls starttime == null ist -> true
                availabilty = (rs.getString("starttime") == null) ? true : false;
                Logger.info("availability " + availabilty);
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

    /**
     * Erstellt bestimmte Anzahl (amount) an Versuche (leer) mit der Konfiguration von configID.
     *
     * @param configId - ID der Konfiguration
     * @param amount - Anzahl der Versuche
     * @return Liste mit vergebenen trialIDs
     */
    public List<String> addTrials(String configId, int amount) {
        final String query = """
                INSERT INTO trials
                (trialid, configid)
                VALUES
                ('%s', '%s')""";

        final String uniqueException =
                "ERROR: duplicate key value violates unique constraint \"trials_pkey\"";

        List<String> trialIds = new ArrayList<String>();

        Connection conn = null;
        Statement st = null;

        //  Verbindungs try
        try {
            conn = DriverManager.getConnection(dataBaseClient.url, dataBaseClient.props);
            st = conn.createStatement();
            for (int i = 0; i < amount; i++) {

                boolean idUnique = false;
                String trialId = Util.generateId("tri_", 5);

                while (!idUnique) {
                    idUnique = true;

                    // keine bereits enthaltenen benutzen
                    while (trialIds.contains(trialId)) {
                        trialId = Util.generateId("tri_", 5);
                    }

                    // INSERT try
                    try {
                        st.executeUpdate(String.format(query, trialId, configId));
                        trialIds.add(trialId);
                    } catch (SQLException e) {
                        if (e.toString().contentEquals(uniqueException)) {
                            idUnique = false;
                            Logger.info("duplicate key (trialId)", e);
                        } else {
                            Logger.error("SQLException when executing addTrials", e);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            Logger.error("SQLException when connecting to DB", e);
        } finally {
            Util.closeQuietly(st);
            Util.closeQuietly(conn);
        }

        return trialIds;
    }

    // TODO: getTrialList
}
