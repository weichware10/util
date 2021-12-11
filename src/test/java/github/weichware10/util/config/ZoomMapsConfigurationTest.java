package github.weichware10.util.config;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import org.junit.Test;

/**
 * Testet die ZoomMapsConfiguration-Klasse.
 */
public class ZoomMapsConfigurationTest {

    /**
     * Testet, das toString() ohne Errors läuft.
     */
    @Test
    public void toStringShouldWork() {
        ZoomMapsConfiguration config = new ZoomMapsConfiguration(0, false, Arrays.asList("a"));
        config.toString();
    }

    /**
     * Testet, ob neu erstelle {@link ZoomMapsConfiguration} Objekte gleich sind.
     */
    @Test
    public void newlyCreatedConfigsShouldBeEqual() {
        ZoomMapsConfiguration config1 = new ZoomMapsConfiguration(0, false, Arrays.asList("a"));
        ZoomMapsConfiguration config2 = new ZoomMapsConfiguration(0, false, Arrays.asList("a"));
        assertEquals("The two Configurations should be equal", config1, config2);
    }
}
