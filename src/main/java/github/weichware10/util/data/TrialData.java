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
    private String answer;
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
     * Stores the TrialData for the different tools internally.
     *
     * @param toolType   - the tooltype of the stored data
     * @param trialId    - the id of the trial
     * @param configId   - the configuration of the stored data
     * @param startTime  - Startzeitpunkt des Versuchs
     * @param answer     - Anwort des Versuchs
     * @param dataPoints - Daten des Versuchs
     *
     * @since v0.3
     */
    public TrialData(Enums.ToolType toolType, String trialId, String configId,
            DateTime startTime, String answer, List<DataPoint> dataPoints) {
        this.toolType = toolType;
        this.trialId = trialId;
        this.configId = configId;
        this.startTime = startTime;
        this.answer = answer;
        this.dataPoints = dataPoints;
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
     * get the answer.
     *
     * @return the answer
     *
     * @since v0.3
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * set the answer.
     *
     * @param answer - the answer
     */
    public void setAnswer(String answer) {
        this.answer = answer;
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
                    answer: %s
                    dataPoints: dataPoints[%d]
                }""",
                toolType.toString(),
                trialId,
                configId,
                startTime.toString(),
                answer,
                dataPoints.size());
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        TrialData that = (TrialData) (other);
        return toolType.equals(that.toolType) && trialId.equals(that.trialId)
                && configId.equals(that.configId) && startTime.equals(that.startTime)
                && answer.equals(that.answer) && dataPoints.equals(that.dataPoints);
    }
}
