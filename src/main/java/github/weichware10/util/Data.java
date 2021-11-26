package github.weichware10.util;

/**
 * Stores the Data for the different tools internally <p>
 * also used to transfer Data
 */
public class Data {
    public final Enums.ToolType tooltype;
    public final int config_id;
    private boolean data; // TODO: replace type with actual datatype!

    public Data(Enums.ToolType tooltype, int config_id) {
        this.tooltype = tooltype;
        this.config_id = config_id;
        this.data = false;
    }

    // TODO: replace type with actual datatype!
    /**
     * get the stored data
     * @return the stored data
     */
    public boolean getData() {
        return this.data;
    }

    // TODO: replace type with actual datatype!
    /**
     * set new data
     */
    public void setData(boolean data) {
        this.data = data;
    }
}
