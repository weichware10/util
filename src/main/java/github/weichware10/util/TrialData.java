package github.weichware10.util;

import java.util.ArrayList;

/**
 * Stores the TrialData for the different tools internally.
 *
 * <p>also used to transfer Data
 */
public class TrialData {
    public final Enums.ToolType tooltype;
    public final String configId;
    private ArrayList<DataPoint> dataPoints;

    /**
     * Stores the TrialData for the different tools internally.
     *
     * @param tooltype - the tooltype of the stored data
     * @param configId - the configuration of the stored data
     */
    public TrialData(Enums.ToolType tooltype, String configId) {
        this.tooltype = tooltype;
        this.configId = configId;
        this.dataPoints = new ArrayList<DataPoint>();
    }

    /**
     * get the stored dataPoints.
     *
     * @return the stored dataPoints
     */
    public ArrayList<DataPoint> getData() {
        return this.dataPoints;
    }

    /**
     * set new dataPoints.
     *
     * @param dataPoints - dataPoints to be set
     */
    public void setData(ArrayList<DataPoint> dataPoints) {
        this.dataPoints = dataPoints;
    }
}
