package github.weichware10.util.db;

import github.weichware10.util.Logger;
import github.weichware10.util.data.DataPoint;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Die Datapoints-Tabelle beinhaltet die gespeicherten Datapoints.
 */
class Datapoints {

    private final DataBaseClient dataBaseClient;

    protected Datapoints(DataBaseClient dataBaseClient) {
        this.dataBaseClient = dataBaseClient;
    }

    /**
     * Gibt alle DataPoints zum Versuch mit trialId zurück.
     *
     * @param trialId - trialId des Versuchs
     * @return Liste von trialId
     */
    public List<DataPoint> get(String trialId) {
        final String queryF = """
                SELECT *
                FROM %s.datapoints
                WHERE trialId LIKE '%s'
                ORDER BY dataid ASC""";
        final String query = String.format(queryF, dataBaseClient.schema, trialId);

        List<DataPoint> dataPoints = new ArrayList<DataPoint>();

        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(dataBaseClient.url, dataBaseClient.props);
            st = conn.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                // bei zoommaps versuchen
                Map<String, Double> viewport = new HashMap<>();
                viewport.put("minX", rs.getDouble("viewportmin_x"));
                viewport.put("minY", rs.getDouble("viewportmin_y"));
                viewport.put("width", rs.getDouble("viewport_width"));
                viewport.put("height", rs.getDouble("viewport_height"));
                viewport = rs.wasNull() ? null : viewport;

                // neuen Punkt zur Liste hinzufügen
                dataPoints.add(new DataPoint(
                        rs.getInt("dataid"),
                        rs.getInt("timeoffset"),
                        viewport));
            }

        } catch (SQLException e) {
            Logger.error("SQLException when executing getDataPoints", e);
        } finally {
            Util.closeQuietly(rs);
            Util.closeQuietly(st);
            Util.closeQuietly(conn);
        }

        return dataPoints;
    }

    /**
     * Speichert DataPoints für das Trial mit trialId.
     *
     * @param dataPoints - die zu speichernden DataPoints
     * @param trialId    - trialId des Versuchs
     */
    public void set(List<DataPoint> dataPoints, String trialId) {

        final String queryF = String.format("""
                INSERT INTO %s.datapoints
                (trialid, dataid, timeoffset,
                viewportmin_x, viewportmin_y, viewport_width, viewport_height)
                VALUES
                (?, ?, ?,
                ?, ?, ?, ?);""", dataBaseClient.schema);

        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = DriverManager.getConnection(dataBaseClient.url, dataBaseClient.props);
            pst = conn.prepareStatement(queryF);
            for (int i = 0; i < dataPoints.size(); i++) {
                DataPoint dp = dataPoints.get(i);
                // Felder für CodeCharts und ZoomMaps
                pst.setString(1, trialId);
                pst.setInt(2, dp.dataId);
                pst.setInt(3, dp.timeOffset);

                // CODECHARTS
                if (dp.viewport == null) {
                    // Felder für ZoomMaps
                    pst.setNull(4, java.sql.Types.DOUBLE);
                    pst.setNull(5, java.sql.Types.DOUBLE);
                    pst.setNull(6, java.sql.Types.DOUBLE);
                    pst.setNull(7, java.sql.Types.DOUBLE);
                } else { // ZOOMMAPS
                    // Felder für ZoomMaps
                    pst.setDouble(4, dp.viewport.getMinX());
                    pst.setDouble(5, dp.viewport.getMinY());
                    pst.setDouble(6, dp.viewport.getWidth());
                    pst.setDouble(7, dp.viewport.getHeight());
                }
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
