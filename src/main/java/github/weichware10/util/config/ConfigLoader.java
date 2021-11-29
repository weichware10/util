package github.weichware10.util.config;

import github.weichware10.util.Enums.ToolType;

/**
 * statische Klasse zum Laden der Konfiguration.
 */
class ConfigLoader {
    /**
     * Lädt eine Konfiguration in die interne Struktur
     * ({@link github.weichware10.util.config.Configuration}).
     *
     * @param location - Speicherort der Konfiguration
     * @return die geladene Konfiguration
     */
    public static Configuration loadConfiguration(String location) {
        Configuration config = new Configuration(new ToolType[] {
            ToolType.CODECHARTS, ToolType.EYETRACKING, ToolType.ZOOMMAPS
        });

        return config;
    }
}
