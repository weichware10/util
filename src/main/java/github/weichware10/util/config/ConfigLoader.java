package github.weichware10.util.config;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import github.weichware10.util.Logger;
import github.weichware10.util.db.DataBaseClient;
import java.io.File;
import java.io.IOException;


/**
 * statische Klasse zum Laden der Konfiguration.
 */
public final class ConfigLoader {

    /**
     * Kann nicht instanziiert werden.
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
            // aus Datei lesen
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
     * @param trialId - ID der Konfiguration
     * @param dataBaseClient - Client für Datenbankzugriff
     * @return die geladene Konfiguration
     * @since v1.0
     */
    public static Configuration fromDataBase(String trialId, DataBaseClient dataBaseClient) {
        // configId von Datenbank abrufen
        final String configId = dataBaseClient.trials.getConfigId(trialId);
        // trial nicht gefunden oder Fehler
        if (configId == null) {
            return null;
        }

        // Konfiguration von Datenbank abrufen
        Configuration configuration = dataBaseClient.configurations.get(configId);
        configuration.trialId = trialId;
        return configuration;
    }
}
