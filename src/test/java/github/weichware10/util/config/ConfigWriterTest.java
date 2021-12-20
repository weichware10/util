package github.weichware10.util.config;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;



/**
 * Testet {@link ConfigWriter}.
 */
public class ConfigWriterTest {
    @Test
    public void onlyWriteToValidLocation() {
        assertFalse("ConfigWriter should not write to shady locations.",
                ConfigWriter.toDataBase(new Configuration(), null) != null);
        assertTrue("ConfigWriter should write to valid locations.",
                ConfigWriter.toDataBase(new Configuration(), null) != null);
    }

    /**
     * Testet, ob das Schreiben der Konfiguration in eine JSON-Datei erfolgreich ist.
     */
    @Test
    public void canWriteToValidPath() {
        assertTrue("ConfigWriter should be able to write to 'target/CFG-zoommaps-path.json'",
                ConfigWriter.toJson("target/CFG-zoommaps-path.json",
                        new Configuration()));

        assertTrue("ConfigWriter should be able to write to 'target/CFG-codecharts-path.json'",
                ConfigWriter.toJson("target/CFG-codecharts-path.json",
                        new Configuration()));

        assertTrue("ConfigWriter should be able to write to 'target/CFG-eyetracking-path.json'",
                ConfigWriter.toJson("target/CFG-eyetracking-path.json",
                        new Configuration()));
    }
}
