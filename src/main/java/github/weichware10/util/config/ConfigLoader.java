package github.weichware10.util.config;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import github.weichware10.util.Logger;
import java.io.File;
import java.io.IOException;


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
     * Lädt eine Konfiguration aus einer JSON-Datei.
     *
     * @param location - Speicherort der Datei.
     * @since v0.2
     */
    public static Configuration fromJson(String location) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Configuration configuration;
            // read from file
            configuration = mapper.readValue(new File(location), Configuration.class);
            return configuration;
        } catch (StreamReadException e) {
            Logger.info("An error occured while loading a config", e);
        } catch (DatabindException e) {
            Logger.info("An error occured while loading a config", e);
        } catch (IOException e) {
            Logger.info("An error occured while loading a config", e);
        }
        return null;
    }

    /**
     * Lädt eine Konfiguration in die interne Struktur
     * ({@link Configuration}).
     *
     * @param configId - ID der Konfiguration
     * @return die geladene Konfiguration
     * @implNote noch nicht implementiert, wartet auf Datenbank-Modul
     */
    public static Configuration fromDataBase(String configId) {
        if (configId == "www.weichware10.com/config") {
            return new Configuration();
        } else {
            return null;
        }
    }
}
