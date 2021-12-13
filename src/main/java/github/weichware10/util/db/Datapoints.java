package github.weichware10.util.db;

import github.weichware10.util.Logger;
import github.weichware10.util.data.DataPoint;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

class Datapoints {

    private final DataBaseClient dataBaseClient;

    protected Datapoints(DataBaseClient dataBaseClient) {
        this.dataBaseClient = dataBaseClient;
    }

    public List<DataPoint> getDataPoints(String trialId) {
        final String query = """
                SELECT *
                FROM %s.datapoints
                WHERE trialId LIKE '%s'
                ORDER BY dataid ASC""";

        List<DataPoint> dataPoints = new ArrayList<DataPoint>();

        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(dataBaseClient.url, dataBaseClient.props);
            st = conn.createStatement();
            rs = st.executeQuery(String.format(query, dataBaseClient.schema, trialId));
            while (rs.next()) {
                dataPoints.add(new DataPoint(
                        rs.getInt("dataid"),
                        rs.getInt("timeoffset"),
                        new int[] { rs.getInt("coordinates_x"), rs.getInt("coordinates_y") },
                        new int[] { rs.getInt("rastersize_x"), rs.getInt("rastersize_y") },
                        rs.getFloat("zoomlevel")));
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

    public void setDataPoints(List<DataPoint> dataPoints, String trialId) {
        final String ccQuery = """
                INSERT INTO %s.datapoints
                (trialid, dataid, timeoffset,
                coordinates_x, coordinates_y, rastersize_x, rastersize_y,
                zoomlevel)
                VALUES
                ('%s', %d, %d,
                %d, %d, %d, %d,
                null);""";

        final String zmQuery = """
                INSERT INTO %s.datapoints
                (trialid, dataid, timeoffset,
                coordinates_x, coordinates_y, rastersize_x, rastersize_y,
                zoomlevel)
                VALUES
                ('%s', %d, %d,
                null, null, null, null,
                %s);""";

        Connection conn = null;
        Statement st = null;

        try {
            conn = DriverManager.getConnection(dataBaseClient.url, dataBaseClient.props);
            st = conn.createStatement();
            for (int i = 0; i < dataPoints.size(); i++) {
                DataPoint dp = dataPoints.get(i);
                // CODECHARTS
                if (dp.zoomLevel == null) {
                    Logger.info("CODE");
                    st.executeUpdate(String.format(ccQuery,
                            dataBaseClient.schema,
                            trialId,
                            dp.dataId,
                            dp.timeOffset,
                            dp.coordinates[0],
                            dp.coordinates[1],
                            dp.rasterSize[0],
                            dp.rasterSize[1]));
                } else { // ZOOMMAPS
                    Logger.info("ZOOM");
                    st.executeUpdate(String.format(zmQuery,
                            dataBaseClient.schema,
                            trialId,
                            dp.dataId,
                            dp.timeOffset,
                            String.format(Locale.US, "%f", dp.zoomLevel)));
                }
            }
        } catch (Exception e) {
            Logger.error("SQLException when executing setDataPoints", e);
        } finally {
            Util.closeQuietly(st);
            Util.closeQuietly(conn);
        }
    }
}
