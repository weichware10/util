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
     * indem Testkonfigurationen doppelt geladen werden und dann miteinander verglichen werden.
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
        //  Vergleichen
        assertEquals("Configs should be the same", configC1, configC2);
        assertEquals("Configs should be the same", configE1, configE2);
        assertEquals("Configs should be the same", configZ1, configZ2);
    }
}
