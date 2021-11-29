package github.weichware10.util.config;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

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
}
