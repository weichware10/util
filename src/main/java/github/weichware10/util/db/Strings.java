package github.weichware10.util.db;

import github.weichware10.util.Logger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Die Strings-Tabelle beinhaltet Strings für CodeCharts-Versuche.
 */
public class Strings {
    private final DataBaseClient dataBaseClient;

    protected Strings(DataBaseClient dataBaseClient) {
        this.dataBaseClient = dataBaseClient;
    }

    /**
     * Gibt die Größe einer in der Datenbank gespeicherten Liste zurück.
     *
     * @param stringId - die zu überprüfende stringId
     * @return die Größe der Liste, 0 wenn nicht vorhanden, {@code null bei Fehler}
     */
    public Integer sizeOf(String stringId) {
        final String queryF = String.format("""
                SELECT COUNT(*) AS size
                FROM %s.strings
                WHERE stringid LIKE ?""", dataBaseClient.schema);

        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        Integer size = null;

        try {
            conn = DriverManager.getConnection(dataBaseClient.url, dataBaseClient.props);
            pst = conn.prepareStatement(queryF);
            pst.setString(1, stringId);
            rs = pst.executeQuery();
            if (rs.next()) {
                size = rs.getInt("size");
            }
        } catch (Exception e) {
            Logger.error("SQLException when executing sizeOfStrings", e, true);
        } finally {
            Util.closeQuietly(rs);
            Util.closeQuietly(pst);
            Util.closeQuietly(conn);
        }

        return size;
    }

    /**
     * Holt eine Liste von Strings aus der Datenbank ab.
     *
     * @param stringId - ID der String-Liste
     * @return Liste der Strings
     */
    public List<String> get(String stringId) {
        final String queryF = String.format("""
                SELECT *
                FROM %s.strings
                WHERE stringid LIKE ?
                ORDER BY orderid ASC""", dataBaseClient.schema);

        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        List<String> strings = new ArrayList<>();

        try {
            conn = DriverManager.getConnection(dataBaseClient.url, dataBaseClient.props);
            pst = conn.prepareStatement(queryF);
            pst.setString(1, stringId);
            rs = pst.executeQuery();
            while (rs.next()) {
                strings.add(rs.getString("string"));
            }
            if (strings.size() == 0) {
                strings = null;
            }
        } catch (Exception e) {
            Logger.error("SQLException when executing getStrings", e, true);
        } finally {
            Util.closeQuietly(rs);
            Util.closeQuietly(pst);
            Util.closeQuietly(conn);
        }

        return strings;
    }

    /**
     * Setzt eine String-Liste in der Datenbank.
     *
     * @param stringId - die ID der Liste
     * @param strings - die Liste selbst
     */
    public void set(String stringId, List<String> strings) {
        final String queryF = String.format("""
                INSERT INTO %s.strings
                (stringid, orderid, string)
                VALUES
                (?, ?, ?);""", dataBaseClient.schema);

        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = DriverManager.getConnection(dataBaseClient.url, dataBaseClient.props);
            pst = conn.prepareStatement(queryF);
            pst.setString(1, stringId);
            for (int i = 0; i < strings.size(); i++) {
                pst.setInt(2, i);
                pst.setString(3, strings.get(i));
                pst.executeUpdate();
            }
        } catch (Exception e) {
            Logger.error("SQLException when executing setDataPoints", e, true);
        } finally {
            Util.closeQuietly(pst);
            Util.closeQuietly(conn);
        }
    }
}
