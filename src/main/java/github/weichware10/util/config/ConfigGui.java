package github.weichware10.util.config;

import github.weichware10.util.Enums.ToolType;

/**
 * Setzt das Bearbeiten von Einstellungen via GUI um.
 */
public class ConfigGui {
    protected Configuration configState;

    /**
     * Initialisiert die ConfigGui-Klasse mit der default-Konfiguration.
     */
    public ConfigGui() {
        configState = new Configuration(ToolType.ZOOMMAPS);
    }

    /**
     * Ein Editor zum Bearbeiten einer Konfiguration.
     */
    public void showEditor() {
        ;
    }
}
