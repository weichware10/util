package github.weichware10.util.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import github.weichware10.util.Logger;
import github.weichware10.util.config.CodeChartsConfiguration;
import github.weichware10.util.config.Configuration;
import github.weichware10.util.config.ZoomMapsConfiguration;
import io.github.cdimascio.dotenv.Dotenv;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

/**
 * Testet {@link Trials}.
 */
public class TrialsTest {
    Dotenv dotenv = Dotenv.load();
    DataBaseClient dbClient = new DataBaseClient(
            dotenv.get("DB_URL"),
            dotenv.get("DB_USERNAME"),
            dotenv.get("DB_PASSWORD"),
            dotenv.get("DB_SCHEMA"));

    @Test
    public void addTrialsShouldWork() {
        CodeChartsConfiguration ccConf = new CodeChartsConfiguration(
                    Arrays.asList("string1", "string2", "string3"),
                    new int[] {1, 1}, new long[] {2, 2}, true,
                    Arrays.asList("imgUrl1", "imgUrl2", "imgUrl3"));
        Configuration codeConfig = new Configuration("temp", "Warum ist die Banane krumm?", ccConf);
        String configIdCc = dbClient.configurations.setConfiguration(codeConfig);

        ZoomMapsConfiguration zmConf = new ZoomMapsConfiguration(1f, true,
                Arrays.asList("imgUrl1", "imgUrl2", "imgUrl3"));
        Configuration zoomConfig = new Configuration("temp", "Warum ist die Banane krumm?", zmConf);
        String configIdZm = dbClient.configurations.setConfiguration(zoomConfig);


        assertEquals(3, dbClient.trials.addTrials(configIdZm, 3).size());
        assertEquals(3, dbClient.trials.addTrials(configIdCc, 3).size());

        // TODO: Wenn Überprüfung auf amount und korrekte configId in addTrials implementiert ist
        // assertThrows(expectedThrowable, dbClient.trials.addTrials("nonExisitingConfigId", 3));
        // assertThrows(expectedThrowable, dbClient.trials.addTrials(configIdZm, -1));
        // assertThrows(expectedThrowable, dbClient.trials.addTrials("nonExisitingConfigId", -1));
    }

    @Test
    public void setTrialShouldWork() {
        
    }
}
