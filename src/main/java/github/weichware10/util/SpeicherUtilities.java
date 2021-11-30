package github.weichware10.util;

import github.weichware10.util.Enums.ToolType;

/**
 * Operations for the Data.
 */
public class SpeicherUtilities {
    private String location;

    /**
     * Creates SpeicherUtilities with Data Location.
     *
     * @param location - the Location where the Data is stored
     */
    public SpeicherUtilities(String location) {
        this.location = location;
    }

    /**
     * Returns the Data Location.
     *
     * @return location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the Data Location.
     *
     * @param location - the Location where the Data should get stored
     * @return returns true if setLocation was sucessfull
     */
    public boolean setLocation(String location) {
        this.location = location;
        if (this.location == null) {
            return false;
        }
        return true;
    }

    /**
     * Function to delete the specific Data.
     * TODO: Richtiges Löschen implementieren
     *
     * @param inputString - Data to delete
     * @return returns true if deletion was sucessfull
     */
    public boolean deleteData(String inpuString) {
        return true;
    }

    /**
     * Function to search for specific Data from a function.
     * TODO: Richtige Suche implementieren
     *
     * @param inputString - Data to search
     * @return returns the requested Data
     */
    public Data searchData(String inpuString) {
        Data test = new Data(ToolType.ZOOMMAPS, 1);
        return test;
    }

    /**
     * Function to save the Information from the functions.
     * TODO: Richtiges Speichern implementieren
     *
     * @param inputData - The Data to save
     * @return return true if the save was sucessfull
     */
    public boolean saveData(Data inputData) {
        return true;
    }
}
