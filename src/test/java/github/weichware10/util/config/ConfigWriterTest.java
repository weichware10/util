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
            ConfigWriter.writeConfiguration("www.downloadfreeramNOW.com", new Configuration()));
        assertTrue("ConfigWriter should write to valid locations.",
            ConfigWriter.writeConfiguration("www.weichware10.com/config", new Configuration()));
    }
}
