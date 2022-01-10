package github.weichware10.util.config;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Testet die CodeChartsConfiguration-Klasse.
 */
public class CodeChartsConfigurationTest {

    /**
     * Testet, das toString() ohne Errors läuft.
     */
    @Test
    public void toStringShouldWork() {
        CodeChartsConfiguration config = new CodeChartsConfiguration(
                "OBST",
                new int[] { 1, 2 },
                new long[] { 200, 200 },
                true);
        config.toString();
    }

    /**
     * Testet, ob neu erstelle {@link CodeChartsConfiguration} Objekte gleich sind.
     */
    @Test
    public void newlyCreatedConfigsShouldBeEqual() {
        CodeChartsConfiguration config1 = new CodeChartsConfiguration(
            "OBST",
            new int[] { 1, 2 },
            new long[] { 200, 200 },
            true);
        CodeChartsConfiguration config2 = new CodeChartsConfiguration(
            "OBST",
            new int[] { 1, 2 },
            new long[] { 200, 200 },
            true);
        assertEquals("The two Configurations should be equal", config1, config2);
    }
}
