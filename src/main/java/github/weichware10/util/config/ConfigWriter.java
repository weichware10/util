package github.weichware10.util.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import github.weichware10.util.Logger;
import github.weichware10.util.db.DataBaseClient;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;


/**
 * statische Klasse zum Speichern der Konfiguration.
 */
public final class ConfigWriter {

    /**
     * Cannot be instantiated.
     */
    private ConfigWriter() {
        throw new IllegalStateException("Cannot be instantiated");
    }

    /**
     * Speichert eine {@link Configuration} in einer JSON-Datei.
     *
     * @param location - Speicherort der JSON-Datei
     * @param configuration - die abzuspeichernde Configuration
     * @return Erfolgsboolean
     * @since v0.2
     */
    public static boolean toJson(String location, Configuration configuration) {
        // only write to JSON files
        if (!location.endsWith(".json")) {
            return false;
        }
        try {
            // umwandeln von Configuration zu JSON String
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = ow.writeValueAsString(configuration);

            // Öffnen der Datei
            File file = new File(location);
            FileOutputStream fos = new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
            BufferedWriter writer = new BufferedWriter(osw);

            // Schreiben des JSON
            writer.append(json);
            writer.close();

            return true; // Schreiben war erfolgreich
        } catch (JsonProcessingException e) {
            Logger.info("An error occured while writing a config", e);
        } catch (IOException e) {
            Logger.info("An error occured while writing a config", e);
        }
        return false; // Schreiben war nicht erfolgreich
    }

    /**
     * Speichert eine Konfiguration ab.
     *
     * @param configuration - die zu speichernde Konfiguration
     * @param dataBaseClient - Client für Datenbankzugriff
     * @return Erfolgsboolean
     * @since v1.0
     */
    public static String toDataBase(Configuration configuration, DataBaseClient dataBaseClient) {
        return dataBaseClient.configurations.set(configuration);
    }
}
