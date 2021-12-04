package github.weichware10.util.config;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import github.weichware10.util.Enums.ToolType;
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
        Configuration config = ConfigLoader.fromDataBase(
                "www.weichware10.com/funny-owl-pics-haha");
        assertNull("loadConfiguration should return null,", config);

        assertThat("loadConfiguration should return instance of Configuration class",
                ConfigLoader.fromDataBase("www.weichware10.com/config"),
                instanceOf(Configuration.class));
    }

    /**
     * Testet, ob das Laden aus einer Datei erfolgreich ist,
     * indem erst eine Datei abgespeichert wird, diese dann neu geladen wird
     * und die zwei Konfigurationen miteinander verglichen werden.
     */
    @Test
    public void loadingIsSuccesfull() {
        Configuration configC1 = new Configuration(ToolType.CODECHARTS);
        Configuration configE1 = new Configuration(ToolType.EYETRACKING);
        Configuration configZ1 = new Configuration(ToolType.ZOOMMAPS);
        ConfigWriter.toJson("target/configC1.json", configC1);
        ConfigWriter.toJson("target/configE1.json", configE1);
        ConfigWriter.toJson("target/configZ1.json", configZ1);
        Configuration configC2 = ConfigLoader.fromJson("target/configC1.json");
        Configuration configE2 = ConfigLoader.fromJson("target/configE1.json");
        Configuration configZ2 = ConfigLoader.fromJson("target/configZ1.json");
        assertEquals(configC1, configC2);
        assertEquals(configE1, configE2);
        assertEquals(configZ1, configZ2);
    }
}
