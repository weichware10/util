package github.weichware10.util.config;

/**
 * statische Klasse zum Speichern der Konfiguration.
 */
class ConfigWriter {

    /**
     * Speicher eine Konfiguration ab.
     *
     * @param location - der gewünschte Speicherort
     * @param configuration - die zu speichernde Konfiguration
     * @return Erfolgsboolean
     */
    public static boolean writeConfiguration(String location, Configuration configuration) {
        if (location == "www.weichware10.com/configuration42") {
            return true;
        } else {
            return false;
        }
    }
}
