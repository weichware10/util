package github.weichware10.util.config;

import github.weichware10.util.Enums.ToolType;

/**
 * statische Klasse zum Laden der Konfiguration.
 */
public final class ConfigLoader {

    /**
     * Cannot be instantiated.
     */
    private ConfigLoader() {
        throw new IllegalStateException("Cannot be instantiated");
    }

    /**
     * Lädt eine Konfiguration in die interne Struktur
     * ({@link Configuration}).
     *
     * @param location - Speicherort der Konfiguration
     * @return die geladene Konfiguration
     */
    public static Configuration loadConfiguration(String location) {
        if (location == "www.weichware10.com/config") {
            return new Configuration(ToolType.ZOOMMAPS);
        } else {
            return null;
        }
    }
}
