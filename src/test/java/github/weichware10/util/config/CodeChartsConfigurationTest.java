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
            "OBST", new int[]{ 3, 5 }, new long[]{ 300, 500 },
            false, true, true, 5,
            15, -1, -1);
        config.toString();
    }

    /**
     * Testet, ob neu erstelle {@link CodeChartsConfiguration} Objekte gleich sind.
     */
    @Test
    public void newlyCreatedConfigsShouldBeEqual() {
        CodeChartsConfiguration config1 = new CodeChartsConfiguration(
            "OBST", new int[]{ 3, 5 }, new long[]{ 300, 500 },
            false, true, true, 5,
            15, -1, -1);
        CodeChartsConfiguration config2 = new CodeChartsConfiguration(
            "OBST", new int[]{ 3, 5 }, new long[]{ 300, 500 },
            false, true, true, 5,
            15, -1, -1);
        assertEquals("The two Configurations should be equal", config1, config2);
    }
}
