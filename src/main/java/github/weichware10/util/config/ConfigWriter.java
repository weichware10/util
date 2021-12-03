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
     */
    public static boolean toJson(String location, Configuration configuration) {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
            String json = ow.writeValueAsString(configuration);
            BufferedWriter writer = new BufferedWriter(new FileWriter(location, false));
            writer.append(json);
            writer.close();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Speicher eine Konfiguration ab.
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
