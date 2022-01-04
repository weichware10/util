package github.weichware10.util.config;

import static org.junit.Assert.assertTrue;

import github.weichware10.util.db.DataBaseClient;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.Test;



/**
 * Testet {@link ConfigWriter}.
 */
public class ConfigWriterTest {
    @Test
    public void onlyWriteToDataBase() {

        // prepare dbclient
        Dotenv dotenv = Dotenv.load();
        DataBaseClient dbClient = new DataBaseClient(
                dotenv.get("DB_URL"),
                "test_admin",
                dotenv.get("DB_PASSWORD"),
                dotenv.get("DB_SCHEMA"));

        String configId = ConfigWriter.toDataBase(new Configuration("null", "question?", "url",
                        "intro", "outro",
                        new CodeChartsConfiguration()), dbClient);
        assertTrue("ConfigWriter should write to valid database", configId != null);
    }

    /**
     * Testet, ob das Schreiben der Konfiguration in eine JSON-Datei erfolgreich ist.
     */
    @Test
    public void canWriteToValidPath() {
        assertTrue("ConfigWriter should be able to write to 'target/CFG-zoommaps-path.json'",
                ConfigWriter.toJson("target/CFG-zoommaps-path.json",
                        new Configuration()));

        assertTrue("ConfigWriter should be able to write to 'target/CFG-codecharts-path.json'",
                ConfigWriter.toJson("target/CFG-codecharts-path.json",
                        new Configuration()));

        assertTrue("ConfigWriter should be able to write to 'target/CFG-eyetracking-path.json'",
                ConfigWriter.toJson("target/CFG-eyetracking-path.json",
                        new Configuration()));
    }
}
