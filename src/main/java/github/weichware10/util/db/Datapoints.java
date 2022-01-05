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
                // bei codecharts versuchen
                double[] coordinates = new double[] { rs.getDouble("coordinates_x"),
                        rs.getDouble("coordinates_y") };
                coordinates = rs.wasNull() ? null : coordinates;
                double[] rasterSize = new double[] { rs.getDouble("rastersize_x"),
                        rs.getDouble("rastersize_y") };
                rasterSize = rs.wasNull() ? null : rasterSize;

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
                        coordinates,
                        rasterSize,
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
        // final String ccQuery = """
        //         INSERT INTO %s.datapoints
        //         (trialid, dataid, timeoffset,
        //         coordinates_x, coordinates_y, rastersize_x, rastersize_y,
        //         viewportmin_x, viewportmin_y, viewport_width, viewport_height)
        //         VALUES
        //         ('%s', %d, %d,
        //         %s, %s, %s, %s,
        //         null, null, null, null);""";

        // final String zmQuery = """
        //         INSERT INTO %s.datapoints
        //         (trialid, dataid, timeoffset,
        //         coordinates_x, coordinates_y, rastersize_x, rastersize_y,
        //         viewportmin_x, viewportmin_y, viewport_width, viewport_height)
        //         VALUES
        //         ('%s', %d, %d,
        //         null, null, null, null,
        //         %s, %s, %s, %s);""";

        final String queryF = String.format("""
                INSERT INTO %s.datapoints
                (trialid, dataid, timeoffset,
                coordinates_x, coordinates_y, rastersize_x, rastersize_y,
                viewportmin_x, viewportmin_y, viewport_width, viewport_height)
                VALUES
                (?, ?, ?,
                ?, ?, ?, ?,
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
                    // Felder für CodeCharts
                    pst.setDouble(4, dp.coordinates[0]);
                    pst.setDouble(5, dp.coordinates[1]);
                    pst.setDouble(6, dp.rasterSize[0]);
                    pst.setDouble(7, dp.rasterSize[1]);
                    // Felder für ZoomMaps
                    pst.setNull(8, java.sql.Types.DOUBLE);
                    pst.setNull(9, java.sql.Types.DOUBLE);
                    pst.setNull(10, java.sql.Types.DOUBLE);
                    pst.setNull(11, java.sql.Types.DOUBLE);
                } else { // ZOOMMAPS
                    // Felder für CodeCharts
                    pst.setNull(4, java.sql.Types.DOUBLE);
                    pst.setNull(5, java.sql.Types.DOUBLE);
                    pst.setNull(6, java.sql.Types.DOUBLE);
                    pst.setNull(7, java.sql.Types.DOUBLE);
                    // Felder für ZoomMaps
                    pst.setDouble(8, dp.viewport.getMinX());
                    pst.setDouble(9, dp.viewport.getMinY());
                    pst.setDouble(10, dp.viewport.getWidth());
                    pst.setDouble(11, dp.viewport.getHeight());
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
