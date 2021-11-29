package github.weichware10.util.config;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * Testet {@link ConfigLoader}.
 */
public class ConfigLoaderTest {

    /**
     * Testet, ob {@link ConfigLoader#loadConfiguration}
     * nur bei korrekten URLs ein Objekt zurückgibt.
     */
    @Test
    public void shouldOnlyLoadFromValidLocation() {
        Configuration config = ConfigLoader.loadConfiguration(
            "www.weichware10.com/funny-owl-pics-haha");
        assertNull("loadConfiguration should return null,", config);

        assertThat("loadConfiguration should return instance of Configuration class",
            ConfigLoader.loadConfiguration("www.weichware10.com/config"),
            instanceOf(Configuration.class));
    }
}
