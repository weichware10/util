package github.weichware10.util.data;

import github.weichware10.util.Enums;
import github.weichware10.util.Enums.ToolType;
import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;

/**
 * Stores the TrialData for the different tools internally.
 *
 * <p>also used to transfer Data
 *
 * @since v0.2
 */
public class TrialData {
    public final Enums.ToolType toolType;
    public final String trialId;
    public final String configId;
    public final DateTime startTime;
    private List<DataPoint> dataPoints;

    /**
     * Stores the TrialData for the different tools internally.
     *
     * @param toolType - the tooltype of the stored data
     * @param trialId  - the id of the trial
     * @param configId - the configuration of the stored data
     *
     * @since v0.2
     */
    public TrialData(Enums.ToolType toolType, String trialId, String configId) {
        this.toolType = toolType;
        this.trialId = trialId;
        this.configId = configId;
        this.startTime = DateTime.now();
        this.dataPoints = new ArrayList<DataPoint>();
    }

    /**
     * get the stored dataPoints.
     *
     * @return the stored dataPoints
     *
     * @since v0.2
     */
    public List<DataPoint> getData() {
        return dataPoints;
    }

    /**
     * Add a DataPoint for CodeCharts.
     *
     * @param coordinates - the coordinates on the viewed picture
     * @param rasterSize  - width and height of the raster
     *
     * @since v0.2
     */
    public void addDataPoint(int[] coordinates, int[] rasterSize) {

        if (toolType != ToolType.CODECHARTS) {
            throw new IllegalArgumentException("Can only add CODECHARTS DataPoints.");
        }

        if (coordinates.length != 2) {
            throw new IllegalArgumentException("coordinates[] needs to have a length of 2");
        }
        if (rasterSize.length != 2) {
            throw new IllegalArgumentException("rasterSize[] needs to have a length of 2");
        }

        // kann "ohne" Bedenken gecastet werden,
        // damit Overflow auftritt, müsste zwischen Anfang und jetzt ca 25 Tage liegen.
        int timeOffset = (int) (DateTime.now().getMillis() - startTime.getMillis());

        dataPoints.add(new DataPoint(dataPoints.size(), timeOffset, coordinates, rasterSize));
    }

    /**
     * Add a DataPoint for ZoomMaps.
     *
     * @param coordinates - the coordinates on the viewed picture
     * @param zoomLevel   - how far the user is zoomed in
     *
     * @since v0.2
     */
    public void addDataPoint(int[] coordinates, float zoomLevel) {

        if (toolType != ToolType.ZOOMMAPS) {
            throw new IllegalArgumentException("Can only add ZOOMMAPS DataPoints.");
        }

        if (coordinates.length != 2) {
            throw new IllegalArgumentException("coordinates[] needs to have a length of 2");
        }

        // kann "ohne" Bedenken gecastet werden,
        // damit Overflow auftritt, müsste zwischen Anfang und jetzt ca 25 Tage liegen.
        int timeOffset = (int) (DateTime.now().getMillis() - startTime.getMillis());

        dataPoints.add(new DataPoint(dataPoints.size(), timeOffset, coordinates, zoomLevel));
    }

    @Override
    public String toString() {
        return String.format("""
                TrialData: {
                    toolType: %s
                    trialId: %s
                    configId: %s
                    startTime: %s
                    dataPoints: dataPoints[%d]
                }""",
                toolType.toString(),
                trialId,
                configId,
                startTime.toString(),
                dataPoints.size());
    }
}
