package github.weichware10.util.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Testet die ZoomMapsConfiguration-Klasse.
 */
public class ZoomMapsConfigurationTest {

    /**
     * Testet, ob der Tutorial-Boolean korrekt geändert wird.
     */
    @Test
    public void tutorialBooleanShouldBeChangeable() {
        ZoomMapsConfiguration config = new ZoomMapsConfiguration();
        config.setTutorial(false);
        assertFalse("The Tutorial Boolean should be false", config.getTutorial());
        config.setTutorial(true);
        assertTrue("The Tutorial Boolean should be true", config.getTutorial());
    }

    /**
     * Testet, ob neu erstelle {@link ZoomMapsConfiguration} Objekte gleich sind.
     */
    @Test
    public void newlyCreatedConfigsShouldBeEqual() {
        ZoomMapsConfiguration config1 = new ZoomMapsConfiguration();
        ZoomMapsConfiguration config2 = new ZoomMapsConfiguration();
        assertEquals("The two Configurations should be equal", config1, config2);
    }
}
