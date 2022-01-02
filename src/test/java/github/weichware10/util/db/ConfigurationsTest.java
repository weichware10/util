package github.weichware10.util.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import github.weichware10.util.config.CodeChartsConfiguration;
import github.weichware10.util.config.Configuration;
import github.weichware10.util.config.ZoomMapsConfiguration;
import io.github.cdimascio.dotenv.Dotenv;
import java.util.Arrays;
import org.junit.Test;

/**
 * Testet Configurations.java
 */
public class ConfigurationsTest {

    final DataBaseClient dbClient;
    Configuration ccConfig;
    Configuration zmConfig;
    Configuration zmConfigWoTu;

    /**
     * Erstellen des DataBaseClients.
     */
    public ConfigurationsTest() {
        Dotenv dotenv = Dotenv.load();
        dbClient = new DataBaseClient(
                dotenv.get("DB_URL"),
                "test_admin",
                dotenv.get("DB_PASSWORD"),
                dotenv.get("DB_SCHEMA"));

        CodeChartsConfiguration codeChartsConfiguration = new CodeChartsConfiguration(
                Arrays.asList("test1", "test2"),
                new int[] { 20, 40 },
                new long[] { 200, 400 },
                true,
                "url");

        ccConfig = new Configuration("dunno yet", "Test Question?", codeChartsConfiguration);
        String ccConfigId = dbClient.configurations.set(ccConfig);
        ccConfig = new Configuration(ccConfigId, "Test Question?", codeChartsConfiguration);

        ZoomMapsConfiguration zoomMapsConfiguration1 = new ZoomMapsConfiguration(4.2f, true,
                "url");

        zmConfig = new Configuration("dunno yet", "Test Question?", zoomMapsConfiguration1);
        String zmConfigId = dbClient.configurations.set(zmConfig);
        zmConfig = new Configuration(zmConfigId, "Test Question?", zoomMapsConfiguration1);

        ZoomMapsConfiguration zoomMapsConfiguratiom2 = new ZoomMapsConfiguration(4.2f, false,
                "url");

        zmConfigWoTu = new Configuration("dunno yet", "Test Question?", zoomMapsConfiguratiom2);
        String zmConfigWoTuId = dbClient.configurations.set(zmConfigWoTu);
        zmConfigWoTu = new Configuration(zmConfigWoTuId, "Test Question?", zoomMapsConfiguratiom2);
    }

    @Test
    public void setConfigurationsShouldWork() {
        CodeChartsConfiguration codeChartsConfiguration = new CodeChartsConfiguration(
                Arrays.asList("test1", "test2"),
                new int[] { 20, 40 },
                new long[] { 200, 400 },
                true,
                "url");

        Configuration config;
        String configId;

        config = new Configuration("", "Test Question?", codeChartsConfiguration);
        configId = dbClient.configurations.set(config);
        assertTrue("configId sollte mit con_ anfangen.", configId.startsWith("con_"));

        ZoomMapsConfiguration zoomMapsConfiguration = new ZoomMapsConfiguration(4.2f, true,
                "url");
        config = new Configuration("", "Test Question?", zoomMapsConfiguration);
        configId = dbClient.configurations.set(config);
        assertTrue("configId sollte mit con_ anfangen.", configId.startsWith("con_"));
    }

    @Test
    public void getConfigurationsShouldWork() {
        assertEquals(ccConfig,
                dbClient.configurations.get(ccConfig.getConfigId()));
        assertEquals(zmConfig,
                dbClient.configurations.get(zmConfig.getConfigId()));
        assertEquals(zmConfigWoTu,
                dbClient.configurations.get(zmConfigWoTu.getConfigId()));
    }
}
