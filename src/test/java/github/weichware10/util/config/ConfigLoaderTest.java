package github.weichware10.util.config;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import github.weichware10.util.db.DataBaseClient;
import io.github.cdimascio.dotenv.Dotenv;
import java.util.List;
import org.junit.Test;

/**
 * Testet {@link ConfigLoader}.
 */
public class ConfigLoaderTest {

    /**
     * Testet, ob {@link ConfigLoader#loadConfiguration}
     * nur bei korrekten trialIds ein Objekt zurückgibt.
     */
    @Test
    public void shouldOnlyLoadFromValidTrialId() {

        // prepare dbclient
        Dotenv dotenv = Dotenv.load();
        DataBaseClient dbClient = new DataBaseClient(
                dotenv.get("DB_URL"),
                "test_admin",
                dotenv.get("DB_PASSWORD"),
                dotenv.get("DB_SCHEMA"));
        // set config to test with
        final String configId = dbClient.configurations.set(new Configuration(
                "null",
                "question?",
                "url",
                "intro",
                "outro",
                true,
                new CodeChartsConfiguration()));
        final List<String> trialIds = dbClient.trials.add(configId, 5);
        if (trialIds.size() < 5) {
            fail("dbClient didn't write enough trials");
        }

        Configuration config = ConfigLoader.fromDataBase(
                "wrongId", dbClient);
        assertNull("loadConfiguration should return null,", config);

        assertThat("loadConfiguration should return instance of Configuration class",
                ConfigLoader.fromDataBase(trialIds.get(1), dbClient),
                instanceOf(Configuration.class));
    }

    /**
     * Testet, ob das Laden aus einer Datei erfolgreich ist,
     * indem Testkonfigurationen doppelt geladen werden und dann miteinander
     * verglichen werden.
     */
    @Test
    public void loadingIsSuccesfull() {
        // Version 1 laden
        Configuration configC1 = ConfigLoader.fromJson(
                "src/test/resources/testconfig-CODECHARTS.json");
        Configuration configE1 = ConfigLoader.fromJson(
                "src/test/resources/testconfig-EYETRACKING.json");
        Configuration configZ1 = ConfigLoader.fromJson(
                "src/test/resources/testconfig-ZOOMMAPS.json");
        // Version 2 laden
        Configuration configC2 = ConfigLoader.fromJson(
                "src/test/resources/testconfig-CODECHARTS.json");
        Configuration configE2 = ConfigLoader.fromJson(
                "src/test/resources/testconfig-EYETRACKING.json");
        Configuration configZ2 = ConfigLoader.fromJson(
                "src/test/resources/testconfig-ZOOMMAPS.json");
        // Vergleichen
        assertEquals("Configs should be the same", configC1, configC2);
        assertEquals("Configs should be the same", configE1, configE2);
        assertEquals("Configs should be the same", configZ1, configZ2);
    }
}
