package github.weichware10.util;

import static org.junit.Assert.assertEquals;

import github.weichware10.util.Enums.ToolType;
import org.junit.Test;


/**
 * Unit Test für Speicherutilities.
 */
public class SpeicherUtilitiesTest {
    /**
     * Test if the Location is set correctly.
     */
    @Test
    public void locationShouldBeSetCorrectly() {
        SpeicherUtilities instance = new SpeicherUtilities("C/Documents");
        assertEquals("C/Documents", instance.getLocation());
    }
    /**
     * Test if the Location is returned correctly.
     */

    @Test
    public void gotLocationCorrect() {
        SpeicherUtilities instance = new SpeicherUtilities("C/Documents");
        assertEquals("C/Documents", instance.getLocation());
    }
    /**
     * Test if the Location is changed correctly.
     */

    @Test
    public void locationShouldBeChangedCorrectly() {
        SpeicherUtilities instance = new SpeicherUtilities("C/Documents");
        instance.setLocation("C/Music");
        assertEquals("C/Music", instance.getLocation());
    }
    /**
     * Test if the deletion was successfull.
     */

    @Test
    public void deletionWasSuccessfull() {
        SpeicherUtilities instance = new SpeicherUtilities("C/Documents");
        assertEquals(true, instance.deleteData("Versuch1 Eyetracking"));
    }
    /**
     * Test if the search was successfull.
     */

    @Test
    public void searchWasSuccessfull() {
        SpeicherUtilities instance = new SpeicherUtilities("C/Documents");
        boolean test = instance.searchData("Versuch1 ZoomMaps") instanceof Data;
        assertEquals(true, test);
    }
    /**
     * Test if the save was successfull.
     */

    @Test
    public void saveWasSuccessfull() {
        SpeicherUtilities instance = new SpeicherUtilities("C/Documents");
        Data versuch2ZoomMaps = new Data(ToolType.ZOOM, 2);
        assertEquals(true, instance.saveData(versuch2ZoomMaps));
    }
}
