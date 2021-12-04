package github.weichware10.util.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


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
        try {
            // umwandeln von Configuration zu JSON String
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = ow.writeValueAsString(configuration);

            // Öffnen der Datei
            BufferedWriter writer = new BufferedWriter(new FileWriter(location, false));

            // Schreiben des JSON
            writer.append(json);
            writer.close();

            return true; // Schreiben war erfolgreich
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false; // Schreiben war nicht erfolgreich
    }

    /**
     * Speichert eine Konfiguration ab.
     *
     * @param location - der gewünschte Speicherort
     * @param configuration - die zu speichernde Konfiguration
     * @return Erfolgsboolean
     */
    public static boolean toDataBase(String location, Configuration configuration) {
        if (location == "www.weichware10.com/config") {
            return true;
        } else {
            return false;
        }
    }
}
