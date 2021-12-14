package github.weichware10.util.config;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Testet die EyeTrackingConfiguration-Klasse.
 */
@SuppressWarnings("deprecation")
public class EyeTrackingConfigurationTest {

    /**
     * Testet, das toString() ohne Errors läuft.
     */
    @Test
    public void toStringShouldWork() {
        EyeTrackingConfiguration config = new EyeTrackingConfiguration();
        config.toString();
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
