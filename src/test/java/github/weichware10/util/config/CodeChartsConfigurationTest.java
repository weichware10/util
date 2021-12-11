package github.weichware10.util.config;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

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
                Arrays.asList("a"), new int[] { 1, 2 },
                new long[]{ 200, 200 }, true, Arrays.asList("a"));
        config.toString();
    }

    /**
     * Testet, ob neu erstelle {@link CodeChartsConfiguration} Objekte gleich sind.
     */
    @Test
    public void newlyCreatedConfigsShouldBeEqual() {
        CodeChartsConfiguration config1 = new CodeChartsConfiguration(
            Arrays.asList("a"), new int[] { 1, 2 },
            new long[]{ 200, 200 }, true, Arrays.asList("a"));
        CodeChartsConfiguration config2 = new CodeChartsConfiguration(
            Arrays.asList("a"), new int[] { 1, 2 },
            new long[]{ 200, 200 }, true, Arrays.asList("a"));
        assertEquals("The two Configurations should be equal", config1, config2);
    }
}
