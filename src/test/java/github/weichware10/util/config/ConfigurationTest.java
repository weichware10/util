package github.weichware10.util.config;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Testet Configuration.
 */
public class ConfigurationTest {

    @Test
    public void toStringWorks() {
        Configuration ccc = new Configuration("con_id", "url", "intro", "outro", true,
                new CodeChartsConfiguration());
        Configuration zmc = new Configuration("con_id", "question?", "url", "intro", "outro", true,
                new ZoomMapsConfiguration());

        assertTrue("toString should match", zmc.toString().matches("Configuration: [\\s\\S]+"));
        assertTrue("toString should match", ccc.toString().matches("Configuration: [\\s\\S]+"));
    }

}
