package github.weichware10.util;

/**
 * Stores the Data for the different tools internally.
 *
 * <p>also used to transfer Data
 */
public class Data {
    public final Enums.ToolType tooltype;
    public final int configId;
    private boolean data; // TODO: replace type with actual datatype!

    /**
     * Stores the Data for the different tools internally.
     *
     * @param tooltype the tooltype of the stored data
     * @param configId the configuration of the stored data
     */
    public Data(Enums.ToolType tooltype, int configId) {
        this.tooltype = tooltype;
        this.configId = configId;
        this.data = false;
    }

    // TODO: replace type with actual datatype!
    /**
     * get the stored data.
     *
     * @return the stored data
     */
    public boolean getData() {
        System.out.println("hi");
        return this.data;
    }

    // TODO: replace type with actual datatype!
    /**
     * set new data.
     */
    public void setData(boolean data) {
        this.data = data;
    }
}
