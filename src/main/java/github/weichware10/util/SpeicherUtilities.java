package github.weichware10.util;

/**
 * TODO: JUSTIN.
 */
public class SpeicherUtilities {
    private String location;

    /**
     * TODO: JUSTIN.
     *
     * @param location todo
     */
    public SpeicherUtilities(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public boolean setLocation(String location) {
        this.location = location;
        return false;
    }

    public boolean deleteData(String inpuString) {
        return false;
    }
}
