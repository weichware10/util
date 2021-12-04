package github.weichware10.util.config;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.ObjectInputFilter.Config;

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
        ConfigClient client = new ConfigClient();
        assertNull("getConfig() does not return null", client.getConfig());
        client.loadConfiguration("www.weichware10.com/config");
        assertThat("getConfig does not return instance of Configuration class",
                client.getConfig(),
                instanceOf(Configuration.class));
    }

    /**
     * Testet ob {@link ConfigClient#loadConfiguration} {@code false} zurückgibt,
     * wenn eine falsche {@code location} angegeben wurde,
     * und {@code true} wenn eine korrekte {@code location} angegeben wurde.
     */
    @Test
    public void loadingShouldReturnCorrectBoolean() {
        ConfigClient client = new ConfigClient();
        assertFalse("Loading should return false", client.loadConfiguration(
                "https://preview.redd.it/epoet6lk5au71.jpg?auto=webp&s=145f91aa106ea927791f87af7bbb2b7a0f2e3e94"));

        assertTrue("Loading should return true", client.loadConfiguration(
                "www.weichware10.com/config"));
    }

    /**
     * Testet ob {@link ConfigClient#loadConfiguration} die Konfiguration nur setzt,
     * wenn eine Location mit einer korrekte Konfiguration angegeben wird.
     */
    @Test
    public void loadingShouldHaveCorrectEffect() {
        ConfigClient client = new ConfigClient();
        client.loadConfiguration("https://www.youtube.com/watch?v=dQw4w9WgXcQ");
        assertNull("Configuration should not be set", client.getConfig());
        client.loadConfiguration("www.weichware10.com/config");
        assertThat("Configuration should be set",
                client.getConfig(),
                instanceOf(Configuration.class));
    }

    /**
     * Stellt sicher, dass das Speichern nicht möglich ist,
     * wenn keine Konfiguration geladen wurde.
     */
    @Test
    public void shouldNotWriteEmptyConfig() {
        ConfigClient client = new ConfigClient();
        assertFalse("Client should not export before loading", client.writeToJson("config.json"));
    }

    /**
     * Stellt sicher, dass nicht an einen invaliden Pfad geschrieben wird.
     */
    @Test
    public void shouldNotWriteToWrongPath() {
        ConfigClient client = new ConfigClient();
        client.loadFromJson("src/test/resources/testconfig-CODECHARTS.json");
        assertFalse("should not write to invalid path", client.writeToJson("badpath/config.json"));
        assertFalse("should not write to invalid path", client.writeToJson("config"));
    }

    /**
     * Testet, ob der ConfigClient eine geladene Konfiguration abspeichern kann.
     */
    @Test
    public void shouldSaveToValidPath() {
        ConfigClient client = new ConfigClient();

        client.loadFromJson("src/test/resources/testconfig-CODECHARTS.json");
        assertTrue("Should be able to write to target/testconfig-CODECHARTS.json",
                client.writeToJson("target/testconfig-CODECHARTS.json"));

        client.loadFromJson("src/test/resources/testconfig-EYETRACKING.json");
        assertTrue("Should be able to write to target/testconfig-EYETRACKING.json",
                client.writeToJson("target/testconfig-EYETRACKING.json"));

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
        ConfigClient client1 = new ConfigClient();
        ConfigClient client2 = new ConfigClient();

        client1.loadFromJson("src/test/resources/testconfig-CODECHARTS.json");
        client2.loadFromJson("src/test/resources/testconfig-CODECHARTS.json");
        assertEquals("Configs should be the same", client1.getConfig(), client2.getConfig());

        client1.loadFromJson("src/test/resources/testconfig-EYETRACKING.json");
        client2.loadFromJson("src/test/resources/testconfig-EYETRACKING.json");
        assertEquals("Configs should be the same", client1.getConfig(), client2.getConfig());

        client1.loadFromJson("src/test/resources/testconfig-ZOOMMAPS.json");
        client2.loadFromJson("src/test/resources/testconfig-ZOOMMAPS.json");
        assertEquals("Configs should be the same" ,client1.getConfig(), client2.getConfig());
    }
}
