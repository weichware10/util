package github.weichware10.util;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import github.weichware10.util.Enums.ToolType;
import github.weichware10.util.data.TrialData;
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
        assertThat("Datentyp sollte Data sein", instance.searchData("Versuch1 ZoomMaps"),
            instanceOf(TrialData.class));
    }

    /**
     * Test if the save was successfull.
     */
    @Test
    public void saveWasSuccessfull() {
        SpeicherUtilities instance = new SpeicherUtilities("C/Documents");
        TrialData versuch2ZoomMaps = new TrialData(ToolType.ZOOMMAPS, "2", "1");
        assertEquals(true, instance.saveData(versuch2ZoomMaps));
    }
}
