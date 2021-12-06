package github.weichware10.util.config;

import static org.junit.Assert.assertEquals;

import github.weichware10.util.Enums.ToolType;
import org.junit.Test;


/**
 * Testet die ConfigGUI-Klasse.
 * Hier könnte man später noch Tests hinzufügen,
 * die Benutzer-Input simulieren und den configState verändern.
 */
public class ConfigGuiTest {
    @Test
    public void shouldInitializeWithDefaultConfig() {
        Configuration defaultConfig = new Configuration(ToolType.ZOOMMAPS);
        ConfigGui gui = new ConfigGui();
        assertEquals(defaultConfig, gui.configState);
    }
}
