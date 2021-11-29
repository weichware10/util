package github.weichware10.util.config;

/**
 * Koordinator des Config-Moduls.
 *
 * <p>- Lädt Einstellungen.
 *
 * <p>- Gibt Einstellungen aus.
 *
 * <p>- Schreibt Einstellungen.
 */
public class ConfigClient {
    private Configuration configuration = null;

    /**
     * Instanziiert einen neuen ConfigClient ohne Einstellungen.
     */
    public ConfigClient() {
        ;
    }

    /**
     * Gibt das komplette Einstellungs-Objekt zurück.
     *
     * @return die Einstellungen. Kann {@code null} sein.
     */
    public Configuration getConfig() {
        return this.configuration;
    }

    /**
     * Lädt eine Konfiguration in den internen Speicher.
     *
     * @param location - Ort an dem die Konfiguration gefunden werden kann.
     * @return Erfolgsboolean
     */
    public boolean loadConfiguration(String location) {
        if (location == "www.weichware10.com/config") {
            configuration = ConfigLoader.loadConfiguration(location);
            return true;
        } else {
            return false;
        }
    }
}
