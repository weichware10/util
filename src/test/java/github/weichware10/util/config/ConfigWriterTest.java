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

    @Test
    public void testWriteToJson() {
        ConfigWriter.toJson("location", new Configuration(ToolType.ZOOMMAPS));
        assertTrue(true);
    }
}
