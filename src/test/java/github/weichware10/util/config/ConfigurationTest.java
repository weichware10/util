package github.weichware10.util.config;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Testen Configuration
 */
public class ConfigurationTest {

    @Test
    public void toStringWorks() {
        Configuration ccc = new Configuration("con_id", "question?",
                new CodeChartsConfiguration());
        Configuration zmc = new Configuration("con_id", "question?",
                new ZoomMapsConfiguration());

        assertTrue("toString should match", zmc.toString().matches("Configuration: [\\s\\S]+"));
        assertTrue("toString should match", ccc.toString().matches("Configuration: [\\s\\S]+"));
    }

}
