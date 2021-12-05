package github.weichware10.util.data;

import github.weichware10.util.Enums;
import java.util.ArrayList;
import org.joda.time.DateTime;

/**
 * Stores the TrialData for the different tools internally.
 *
 * <p>also used to transfer Data
 */
public class TrialData {
    public final Enums.ToolType tooltype;
    public final String trialId;
    public final String configId;
    public final DateTime startTime;
    private ArrayList<DataPoint> dataPoints;

    /**
     * Stores the TrialData for the different tools internally.
     *
     * @param tooltype - the tooltype of the stored data
     * @param trialId - the id of the trial
     * @param configId - the configuration of the stored data
     */
    public TrialData(Enums.ToolType tooltype, String trialId, String configId) {
        this.tooltype = tooltype;
        this.trialId = trialId;
        this.configId = configId;
        this.startTime = DateTime.now(); // TODO: Wann wird Instanz im Versuch erzugt?
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
