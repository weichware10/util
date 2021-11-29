package github.weichware10.util.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Testet die CodeChartsConfiguration-Klasse.
 */
public class CodeChartsConfigurationTest {

    /**
     * Testet, ob der Tutorial-Boolean korrekt geändert wird.
     */
    @Test
    public void tutorialBooleanShouldBeChangeable() {
        CodeChartsConfiguration config = new CodeChartsConfiguration();
        config.setTutorial(false);
        assertFalse("The Tutorial Boolean should be false", config.getTutorial());
        config.setTutorial(true);
        assertTrue("The Tutorial Boolean should be true", config.getTutorial());
    }

    /**
     * Testet, ob neu erstelle {@link CodeChartsConfiguration} Objekte gleich sind.
     */
    @Test
    public void newlyCreatedConfigsShouldBeEqual() {
        CodeChartsConfiguration config1 = new CodeChartsConfiguration();
        CodeChartsConfiguration config2 = new CodeChartsConfiguration();
        assertEquals("The two Configurations should be equal", config1, config2);
    }
}
