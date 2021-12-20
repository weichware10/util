package github.weichware10.util.config;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import github.weichware10.util.db.DataBaseClient;
import io.github.cdimascio.dotenv.Dotenv;
import java.util.List;
import org.junit.Test;

/**
 * Testet {@link ConfigClient}.
 */
public class ConfigClientTest {

    /**
     * Testet ob {@link ConfigClient#getConfig()} {@code null} vorm Laden zurückgibt
     * und eine Instanz von {@link Configuration} nach dem Laden.
     */
    @Test
    public void configShouldBeNullBeforeLoading() {
        ConfigClient client = new ConfigClient(null);
        assertNull("getConfig() does not return null", client.getConfig());
        client.loadFromJson("src/test/resources/testconfig-CODECHARTS.json");
        assertThat("getConfig does not return instance of Configuration class",
                client.getConfig(),
                instanceOf(Configuration.class));
    }

    /**
     * Testet ob {@link ConfigClient#loadConfiguration} {@code false} zurückgibt,
     * wenn ohne {@link DataBaseClient} geladen werden soll,
     * und {@code true} wenn mit {@link DataBaseClient} geladen werden soll.
     */
    @Test
    public void loadingShouldReturnCorrectBooleanDataBase() {
        ConfigClient client1 = new ConfigClient(null);
        assertFalse("Loading should return false", client1.loadFromDataBase("con_test"));

        // prepare dbclient
        Dotenv dotenv = Dotenv.load();
        DataBaseClient dbClient = new DataBaseClient(
                dotenv.get("DB_URL"),
                dotenv.get("DB_USERNAME"),
                dotenv.get("DB_PASSWORD"),
                dotenv.get("DB_SCHEMA"));
        // set config to test with
        final String configId = dbClient.configurations.set(new Configuration("null", "question?",
                new CodeChartsConfiguration()));
        final List<String> trialIds = dbClient.trials.add(configId, 5);
        if (trialIds.size() < 5) {
            fail("dbClient didn't write enough trials");
        }
        // prepare configclient
        ConfigClient client2 = new ConfigClient(dbClient);

        assertTrue("Loading should return true", client2.loadFromDataBase(trialIds.get(1)));
    }

    @Test
    public void loadingShouldReturnCorrectBooleanJson() {
        ConfigClient client = new ConfigClient(null);
        assertFalse("ConfigClient should not JSON load from weird location",
                client.loadFromJson("weirdlocation"));

        assertTrue("ConfigClient should load from valid location",
                client.loadFromJson("src/test/resources/testconfig-CODECHARTS.json"));
    }

    /**
     * Testet ob {@link ConfigClient#loadConfiguration} die Konfiguration nur setzt,
     * wenn eine Location mit einer korrekte Konfiguration angegeben wird.
     */
    @Test
    public void loadingShouldHaveCorrectEffect() {
        // DATABASE
        ConfigClient client1 = new ConfigClient(null);
        client1.loadFromDataBase("https://www.youtube.com/watch?v=dQw4w9WgXcQ");
        assertNull("Configuration should not be set", client1.getConfig());
        // prepare dbclient
        Dotenv dotenv = Dotenv.load();
        DataBaseClient dbClient = new DataBaseClient(
                dotenv.get("DB_URL"),
                dotenv.get("DB_USERNAME"),
                dotenv.get("DB_PASSWORD"),
                dotenv.get("DB_SCHEMA"));
        ConfigClient client2 = new ConfigClient(dbClient);
        client2.loadFromDataBase("https://www.youtube.com/watch?v=dQw4w9WgXcQ");
        assertNull("Configuration should not be set", client2.getConfig());


        // JSON
        client1.loadFromJson("src/test/resources/testconfig-CODECHARTS.json");
        assertThat("Configuration should be set",
                client1.getConfig(),
                instanceOf(Configuration.class));
    }

    /**
     * Stellt sicher, dass das Speichern nicht möglich ist,
     * wenn keine Konfiguration geladen wurde.
     */
    @Test
    public void shouldNotWriteNullishConfig() {
        ConfigClient client = new ConfigClient(null);
        assertFalse("Client should not export before loading", client.writeToJson("config.json"));
    }

    /**
     * Stellt sicher, dass nicht an einen invaliden Pfad geschrieben wird.
     */
    @Test
    public void shouldNotWriteToInvalidPath() {
        ConfigClient client = new ConfigClient(null);
        client.loadFromJson("src/test/resources/testconfig-CODECHARTS.json");
        assertFalse("should not write to invalid path", client.writeToJson("badpath/config.json"));
        assertFalse("should not write to invalid path", client.writeToJson("config"));
    }

    /**
     * Testet, ob der ConfigClient eine geladene Konfiguration abspeichern kann.
     */
    @Test
    public void shouldSaveToValidPath() {
        ConfigClient client = new ConfigClient(null);

        client.loadFromJson("src/test/resources/testconfig-CODECHARTS.json");
        assertTrue("Should be able to write to target/testconfig-CODECHARTS.json",
                client.writeToJson("target/testconfig-CODECHARTS.json"));

        // client.loadFromJson("src/test/resources/testconfig-EYETRACKING.json");
        // assertTrue("Should be able to write to target/testconfig-EYETRACKING.json",
        //         client.writeToJson("target/testconfig-EYETRACKING.json"));

        client.loadFromJson("src/test/resources/testconfig-ZOOMMAPS.json");
        assertTrue("Should be able to write to target/testconfig-ZOOMMAPS.json",
                client.writeToJson("target/testconfig-ZOOMMAPS.json"));
    }

    /**
     * Testet, ob die gleiche Konfiguration geladen wird,
     * wenn zwei Mal die gleiche Datei geladen wird.
     */
    @Test
    public void loadingShouldBeReliable() {
        ConfigClient client1 = new ConfigClient(null);
        ConfigClient client2 = new ConfigClient(null);

        client1.loadFromJson("src/test/resources/testconfig-CODECHARTS.json");
        client2.loadFromJson("src/test/resources/testconfig-CODECHARTS.json");
        assertEquals("Configs should be the same", client1.getConfig(), client2.getConfig());

        client1.loadFromJson("src/test/resources/testconfig-EYETRACKING.json");
        client2.loadFromJson("src/test/resources/testconfig-EYETRACKING.json");
        assertEquals("Configs should be the same", client1.getConfig(), client2.getConfig());

        client1.loadFromJson("src/test/resources/testconfig-ZOOMMAPS.json");
        client2.loadFromJson("src/test/resources/testconfig-ZOOMMAPS.json");
        assertEquals("Configs should be the same", client1.getConfig(), client2.getConfig());
    }

    @Test
    public void shouldWriteToDataBase() {
        ConfigClient client1 = new ConfigClient(null);
        client1.loadFromJson("src/test/resources/testconfig-ZOOMMAPS.json");
        assertNull("Writing should not return an id", client1.writeToDataBase());

        // prepare dbclient
        Dotenv dotenv = Dotenv.load();
        DataBaseClient dbClient = new DataBaseClient(
            dotenv.get("DB_URL"),
            dotenv.get("DB_USERNAME"),
            dotenv.get("DB_PASSWORD"),
            dotenv.get("DB_SCHEMA"));
        // prepare configclient
        ConfigClient client2 = new ConfigClient(dbClient);
        assertNull("Writing should not return an id", client2.writeToDataBase());
        client2.loadFromJson("src/test/resources/testconfig-ZOOMMAPS.json");

        assertThat("Writing should return a string",
                client2.writeToDataBase(),
                instanceOf(String.class));
    }
}
