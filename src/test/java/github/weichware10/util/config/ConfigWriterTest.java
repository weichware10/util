package github.weichware10.util.config;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import github.weichware10.util.Enums.ToolType;
import org.junit.Test;



/**
 * Testet {@link ConfigWriter}.
 */
public class ConfigWriterTest {
    @Test
    public void onlyWriteToValidLocation() {
        assertFalse("ConfigWriter should not write to shady locations.",
                ConfigWriter.toDataBase(
                        "www.downloadfreeramNOW.com", new Configuration(ToolType.ZOOMMAPS)));
        assertTrue("ConfigWriter should write to valid locations.",
                ConfigWriter.toDataBase(
                        "www.weichware10.com/config", new Configuration(ToolType.ZOOMMAPS)));
    }

    /**
     * Testet, ob das Schreiben der Konfiguration in eine JSON-Datei erfolgreich ist.
     */
    @Test
    public void canWriteToValidPath() {
        assertTrue("ConfigWriter should be able to write to 'target/CFG-zoommaps-path.json'",
                ConfigWriter.toJson("target/CFG-zoommaps-path.json",
                        new Configuration(ToolType.ZOOMMAPS)));

        assertTrue("ConfigWriter should be able to write to 'target/CFG-codecharts-path.json'",
                ConfigWriter.toJson("target/CFG-codecharts-path.json",
                        new Configuration(ToolType.CODECHARTS)));

        assertTrue("ConfigWriter should be able to write to 'target/CFG-eyetracking-path.json'",
                ConfigWriter.toJson("target/CFG-eyetracking-path.json",
                        new Configuration(ToolType.EYETRACKING)));
    }
}
