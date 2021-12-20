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
     * @param trialId - ID der Konfiguration
     * @param dataBaseClient - Client für Datenbankzugriff
     * @return die geladene Konfiguration
     * @since v0.4
     */
    public static Configuration fromDataBase(String trialId, DataBaseClient dataBaseClient) {
        // get configId from database
        final String configId = dataBaseClient.trials.getConfigId(trialId);
        // trial not found or error
        if (configId == null) {
            return null;
        }
        // get config from database
        return dataBaseClient.configurations.get(configId);
    }
}
