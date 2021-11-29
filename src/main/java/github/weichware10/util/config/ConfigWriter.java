package github.weichware10.util.config;

/**
 * statische Klasse zum Speichern der Konfiguration.
 */
final class ConfigWriter {

    /**
     * Cannot be instantiated.
     */
    private ConfigWriter() {
        throw new IllegalStateException("Cannot be instantiated");
    }

    /**
     * Speicher eine Konfiguration ab.
     *
     * @param location - der gewünschte Speicherort
     * @param configuration - die zu speichernde Konfiguration
     * @return Erfolgsboolean
     */
    public static boolean writeConfiguration(String location, Configuration configuration) {
        if (location == "www.weichware10.com/config") {
            return true;
        } else {
            return false;
        }
    }
}
