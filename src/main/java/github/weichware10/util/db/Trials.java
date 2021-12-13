package github.weichware10.util.db;

import github.weichware10.util.Enums.ToolType;
import github.weichware10.util.Logger;
import github.weichware10.util.config.Configuration;
import github.weichware10.util.data.TrialData;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
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
        final String query = "SELECT configid FROM %s.trials WHERE trialid LIKE '%s';";

        String configId = null;

        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;

        // get resultset
        try {
            conn = DriverManager.getConnection(dataBaseClient.url, dataBaseClient.props);
            st = conn.createStatement();
            rs = st.executeQuery(String.format(query, dataBaseClient.schema, trialId));
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
                FROM %s.trials AS t, %s.configurations AS c
                WHERE t.configid LIKE c.configid AND t.trialid LIKE '%s'""";

        TrialData trialData = null;

        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(dataBaseClient.url, dataBaseClient.props);
            st = conn.createStatement();
            rs = st.executeQuery(String.format(query,
                    dataBaseClient.schema,
                    dataBaseClient.schema,
                    trialId));

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
        Configuration conf = dataBaseClient.configurations.getConfiguration(trialData.configId);
        if (conf == null || conf.getToolType() != trialData.toolType) {
            return false;
        } else if (!getTrialAvailability(trialData.trialId)) {
            return false;
        } else if (!conf.getConfigId().equals(getConfigId(trialData.trialId))) {
            return false;
        }

        final String query = """
                UPDATE %s.trials
                SET
                starttime = '%s',
                answer = '%s'
                WHERE trialid LIKE '%s';""";

        boolean success = false;

        Connection conn = null;
        Statement st = null;

        try {
            conn = DriverManager.getConnection(dataBaseClient.url, dataBaseClient.props);
            st = conn.createStatement();
            st.executeUpdate(String.format(query,
                    dataBaseClient.schema,
                    new Timestamp(trialData.startTime.getMillis()),
                    trialData.getAnswer(),
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
     * Überprüft, ob trialID exisitiert und der Versuch dieser trialID bereits
     * durchgeführt wurde.
     *
     * @param trialId - ID des Versuchs
     * @return Verfügbarkeitsboolean
     */
    public boolean getTrialAvailability(String trialId) {
        // Benutzung von starttime um möglichst wenig Datenverbrauch zu erreichen
        // timestamp hat meistens eine kleiner Größe als text
        final String query = "SELECT starttime FROM %s.trials WHERE trialid LIKE '%s'";

        boolean availabilty = false;

        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(dataBaseClient.url, dataBaseClient.props);
            st = conn.createStatement();
            rs = st.executeQuery(String.format(query, dataBaseClient.schema, trialId));

            // wenn es existiert, besteht Möglichkeit, dass availability true ist
            if (rs.next()) {
                // falls starttime == null ist -> true
                availabilty = (rs.getString("starttime") == null) ? true : false;
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
     * Erstellt bestimmte Anzahl (amount) an Versuche (leer) mit der Konfiguration
     * von configID.
     *
     * @param configId - ID der Konfiguration
     * @param amount   - Anzahl der Versuche
     * @return Liste mit vergebenen trialIDs
     */
    public List<String> addTrials(String configId, int amount) {
        // Überprüft, ob amount <= 0 ist
        // oder Verfügbarkeit der Konfiguration mit der configId false ist
        if (amount <= 0 || !dataBaseClient.configurations.getConfigAvailability(configId)) {
            return null;
        }
        final String query = """
                INSERT INTO %s.trials
                (trialid, configid)
                VALUES
                ('%s', '%s')""";

        final String uniqueException =
                "ERROR: duplicate key value violates unique constraint \"trials_pkey\"";

        List<String> trialIds = new ArrayList<String>();

        Connection conn = null;
        Statement st = null;

        // Verbindungs try
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
                        st.executeUpdate(String.format(query,
                                dataBaseClient.schema,
                                trialId,
                                configId));
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

    /**
     * Sucht nach Trials in der Trials-Tabelle, die den Suchparametern entsprechen.
     * Es wird eine "Vorschau" der Daten geliefert, d.h. die Objekte beinhalten
     * keine DataPoints.
     *
     * @param configId - configId nach der gesucht wird, kann `null` sein
     * @param toolType - ToolType nach dem gesucht wird, kann `null` sein
     * @param minTime  - Anfang des Zeitspannenfilters, kann `null` sein
     * @param maxTime  - Ende des Zeitspannenfilters, kann `null` sein
     * @param amount   - maximale Anzahl der zurückzugebenden Ergebnisse,
     *                 bei Werten <= 0 wird default 50 benutzt.
     * @return Liste von TrialData Objekten, die keine DataPoints besitzen.
     */
    public List<TrialData> getTrialList(String configId, ToolType toolType,
            DateTime minTime, DateTime maxTime, int amount) {

        // QUERY
        final String queryFormat = """
                SELECT t.trialid, t.configid, c.tooltype, t.starttime, t.answer
                FROM %s.trials AS t
                LEFT JOIN %s.configurations AS c
                    ON t.configid LIKE c.configid
                WHERE t.configid LIKE '%s'
                    AND c.tooltype LIKE '%s'
                    AND %s
                    AND %s
                LIMIT %d;
                """;

        // only query for minTime if provided
        final String minTimeQuery = (minTime != null)
                ? String.format("t.starttime >= timestamp '%s'",
                        new Timestamp(minTime.getMillis()).toString())
                : "true";

        // only query for maxTime if provided
        final String maxTimeQuery = (maxTime != null)
                ? String.format("t.starttime <= timestamp '%s'",
                        new Timestamp(maxTime.getMillis()).toString())
                : "true";

        final String query = String.format(queryFormat,
                dataBaseClient.schema,
                dataBaseClient.schema,
                (configId != null) ? configId : '%', // match every configId
                (toolType != null) ? toolType.toString() : '%', // match every tooltype
                minTimeQuery,
                maxTimeQuery,
                (amount > 0) ? amount : 50);

        // RESULT
        List<TrialData> result = new ArrayList<TrialData>();

        // DATABASE OBJECTS
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(dataBaseClient.url, dataBaseClient.props);
            st = conn.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                TrialData td = new TrialData(
                        ToolType.valueOf(rs.getString("tooltype")),
                        rs.getString("trialid"),
                        rs.getString("configid"),
                        new DateTime(rs.getTimestamp("starttime")),
                        rs.getString("answer"),
                        Arrays.asList()); // leere Liste

                result.add(td);
            }
        } catch (SQLException e) {
            Logger.error("SQLException when executing getTrialAvailability", e);
        } finally {
            Util.closeQuietly(rs);
            Util.closeQuietly(st);
            Util.closeQuietly(conn);
        }

        return result;
    }
}
