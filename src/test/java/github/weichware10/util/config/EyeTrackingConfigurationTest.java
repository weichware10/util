package github.weichware10.util.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Testet die EyeTrackingConfiguration-Klasse.
 */
public class EyeTrackingConfigurationTest {

    /**
     * Testet, ob der Tutorial-Boolean korrekt geändert wird.
     */
    @Test
    public void tutorialBooleanShouldBeChangeable() {
        EyeTrackingConfiguration config = new EyeTrackingConfiguration();
        config.setTutorial(false);
        assertFalse("The Tutorial Boolean should be false", config.getTutorial());
        config.setTutorial(true);
        assertTrue("The Tutorial Boolean should be true", config.getTutorial());
    }

    /**
     * Testet, ob neu erstelle {@link EyeTrackingConfiguration} Objekte gleich sind.
     */
    @Test
    public void newlyCreatedConfigsShouldBeEqual() {
        EyeTrackingConfiguration config1 = new EyeTrackingConfiguration();
        EyeTrackingConfiguration config2 = new EyeTrackingConfiguration();
        assertEquals("The two Configurations should be equal", config1, config2);
    }
}
