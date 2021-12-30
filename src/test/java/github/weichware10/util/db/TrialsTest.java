package github.weichware10.util.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import github.weichware10.util.ToolType;
import github.weichware10.util.config.CodeChartsConfiguration;
import github.weichware10.util.config.Configuration;
import github.weichware10.util.config.ZoomMapsConfiguration;
import github.weichware10.util.data.DataPoint;
import github.weichware10.util.data.TrialData;
import io.github.cdimascio.dotenv.Dotenv;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.joda.time.DateTime;
import org.junit.Test;

/**
 * Testet {@link Trials}.
 */
public class TrialsTest {
    DataBaseClient dbClient;
    Configuration codeConfig;
    Configuration zoomConfig;
    String configIdCc;
    String configIdZm;

    /**
     * Konstruktor.
     */
    public TrialsTest() {
        Dotenv dotenv = Dotenv.load();
        dbClient = new DataBaseClient(
                dotenv.get("DB_URL"),
                dotenv.get("DB_USERNAME"),
                dotenv.get("DB_PASSWORD"),
                dotenv.get("DB_SCHEMA"));

        CodeChartsConfiguration ccConf = new CodeChartsConfiguration(
                Arrays.asList("string1", "string2", "string3"),
                new int[] { 1, 1 }, new long[] { 2, 2 }, true,
                Arrays.asList("imgUrl1", "imgUrl2", "imgUrl3"));
        codeConfig = new Configuration("temp", "Warum ist die Banane krumm?", ccConf);
        configIdCc = dbClient.configurations.set(codeConfig);

        ZoomMapsConfiguration zmConf = new ZoomMapsConfiguration(1f, true,
                Arrays.asList("imgUrl1", "imgUrl2", "imgUrl3"));
        zoomConfig = new Configuration("temp", "Warum ist die Banane krumm?", zmConf);
        configIdZm = dbClient.configurations.set(zoomConfig);
    }

    @Test
    public void addTrialsShouldWork() {
        assertEquals(3, dbClient.trials.add(configIdZm, 3).size());
        assertEquals(3, dbClient.trials.add(configIdCc, 3).size());

        assertNull(dbClient.trials.add("nonExisitingConfigId", 3));
        assertNull(dbClient.trials.add(configIdZm, -1));
        assertNull(dbClient.trials.add("nonExisitingConfigId", -1));
    }

    @Test
    public void setTrialShouldWork() {
        // Jeweils 3 Trial-Einträge pro Tool erstellen
        List<String> trialsZm = dbClient.trials.add(configIdZm, 3);
        List<String> trialsCc = dbClient.trials.add(configIdCc, 3);

        // Trials setzen ohne DataPoints
        TrialData trialData1 = new TrialData(ToolType.ZOOMMAPS, trialsZm.get(0), configIdZm);
        trialData1.setAnswer("Ja!");
        assertTrue(dbClient.trials.set(trialData1));

        TrialData trialData2 = new TrialData(ToolType.CODECHARTS, trialsCc.get(0), configIdCc);
        trialData2.setAnswer("Ja!");
        assertTrue(dbClient.trials.set(trialData2));

        // Trials setzen mit DataPoints
        TrialData trialData3 = new TrialData(ToolType.ZOOMMAPS, trialsZm.get(1), configIdZm);
        trialData3.setAnswer("Ja!");
        trialData3.addDataPoint(new int[] { 1, 1 }, 1.0f);
        trialData3.addDataPoint(new int[] { 2, 2 }, 2.0f);
        assertTrue(dbClient.trials.set(trialData3));

        TrialData trialData4 = new TrialData(ToolType.CODECHARTS, trialsCc.get(1), configIdCc);
        trialData4.setAnswer("Ja!");
        trialData4.addDataPoint(new int[] { 1, 1 }, new int[] { 1, 1 });
        trialData4.addDataPoint(new int[] { 2, 2 }, new int[] { 2, 2 });
        assertTrue(dbClient.trials.set(trialData4));

        // Trials auf bereits gesetzten Trial-Eintrag setzten
        TrialData trialData5 = new TrialData(ToolType.ZOOMMAPS, trialsZm.get(1), configIdZm);
        trialData5.setAnswer("Ja!");
        assertFalse(dbClient.trials.set(trialData5));

        TrialData trialData6 = new TrialData(ToolType.CODECHARTS, trialsCc.get(1), configIdCc);
        trialData6.setAnswer("Ja!");
        assertFalse(dbClient.trials.set(trialData6));

        // Trials mit nicht existierender trialId setzen
        TrialData trialData7 = new TrialData(ToolType.ZOOMMAPS, "nonExistingTrialId", configIdZm);
        trialData7.setAnswer("Ja!");
        assertFalse(dbClient.trials.set(trialData7));

        TrialData trialData8 = new TrialData(ToolType.CODECHARTS, "nonExistingTrialId", configIdCc);
        trialData8.setAnswer("Ja!");
        assertFalse(dbClient.trials.set(trialData8));

        TrialData trialData9 = new TrialData(ToolType.ZOOMMAPS, trialsZm.get(2),
                "nonExistingConfigId");
        trialData9.setAnswer("Ja!");
        assertFalse(dbClient.trials.set(trialData9));

        TrialData trialData10 = new TrialData(ToolType.CODECHARTS, trialsCc.get(2),
                "nonExistingConfigId");
        trialData10.setAnswer("Ja!");
        assertFalse(dbClient.trials.set(trialData10));

        // Trials mit toolType von TrialData != toolType von Konfiguration der configId
        TrialData trialData11 = new TrialData(ToolType.ZOOMMAPS, trialsZm.get(2), configIdCc);
        trialData11.setAnswer("Ja!");
        assertFalse(dbClient.trials.set(trialData10));

        TrialData trialData12 = new TrialData(ToolType.CODECHARTS, trialsCc.get(2), configIdZm);
        trialData12.setAnswer("Ja!");
        assertFalse(dbClient.trials.set(trialData12));

        // Trials mit nicht existierender trialId & configId
        TrialData trialData13 = new TrialData(ToolType.ZOOMMAPS, "nonExistingTrialId",
                "nonExistingConfigId");
        trialData13.setAnswer("Ja!");
        assertFalse(dbClient.trials.set(trialData13));

        TrialData trialData14 = new TrialData(ToolType.CODECHARTS, "nonExistingTrialId",
                "nonExistingConfigId");
        trialData14.setAnswer("Ja!");
        assertFalse(dbClient.trials.set(trialData14));

        // Eintrag Trial in DB hat andere Konfiguration als erstellte TrialData
        TrialData trialData15 = new TrialData(ToolType.ZOOMMAPS, trialsZm.get(2),
                dbClient.configurations.set(zoomConfig));
        trialData15.setAnswer("Ja!");
        assertFalse(dbClient.trials.set(trialData15));

        TrialData trialData16 = new TrialData(ToolType.CODECHARTS, trialsCc.get(2),
                dbClient.configurations.set(codeConfig));
        trialData16.setAnswer("Ja!");
        assertFalse(dbClient.trials.set(trialData16));
    }

    @Test
    public void getTrialShouldWork() {
        List<String> trialsZm = dbClient.trials.add(configIdZm, 1);

        TrialData trialData1 = new TrialData(ToolType.ZOOMMAPS, trialsZm.get(0), configIdZm);
        trialData1.setAnswer("Ja");
        trialData1.addDataPoint(new int[] { 1, 1 }, 1.0f);
        trialData1.addDataPoint(new int[] { 2, 2 }, 1.0f);
        trialData1.addDataPoint(new int[] { 3, 3 }, 1.0f);
        dbClient.trials.set(trialData1);
        TrialData trialData11 = dbClient.trials.getTrial(trialsZm.get(0));
        assertTrue(trialData11.equals(trialData1));

        List<String> trialsCc = dbClient.trials.add(configIdCc, 1);

        TrialData trialData2 = new TrialData(ToolType.CODECHARTS, trialsCc.get(0), configIdCc);
        trialData2.setAnswer("Ja");
        trialData2.addDataPoint(new int[] { 1, 1 }, new int[] { 2, 2 });
        trialData2.addDataPoint(new int[] { 2, 2 }, new int[] { 3, 3 });
        trialData2.addDataPoint(new int[] { 3, 3 }, new int[] { 4, 4 });
        dbClient.trials.set(trialData2);
        TrialData trialData22 = dbClient.trials.getTrial(trialsCc.get(0));
        assertTrue(trialData22.equals(trialData2));
    }

    @Test
    public void getTrialListShouldWork() {
        // ZOOMMAPS
        List<String> trialsZm = dbClient.trials.add(configIdZm, 3);
        TrialData trialData1 = new TrialData(ToolType.ZOOMMAPS, trialsZm.get(0), configIdZm,
                DateTime.now().minusHours(1), "Ja", new ArrayList<DataPoint>());
        trialData1.addDataPoint(new int[] { 1, 1 }, 1.0f);
        dbClient.trials.set(trialData1);

        TrialData trialData2 = new TrialData(ToolType.ZOOMMAPS, trialsZm.get(1), configIdZm,
                DateTime.now().minusHours(2), "Ja", new ArrayList<DataPoint>());
        trialData2.addDataPoint(new int[] { 2, 2 }, 2.0f);
        dbClient.trials.set(trialData2);

        TrialData trialData3 = new TrialData(ToolType.ZOOMMAPS, trialsZm.get(2), configIdZm,
                DateTime.now().minusHours(3), "Ja", new ArrayList<DataPoint>());
        trialData3.addDataPoint(new int[] { 3, 3 }, 3.0f);
        dbClient.trials.set(trialData3);

        String configIdZm2 = dbClient.configurations.set(zoomConfig);
        List<String> trialsZm2 = dbClient.trials.add(configIdZm2, 1);

        TrialData trialData7 = new TrialData(ToolType.ZOOMMAPS, trialsZm2.get(0), configIdZm2,
                DateTime.now().minusHours(4), "Ja", new ArrayList<DataPoint>());
        trialData7.addDataPoint(new int[] { 4, 4 }, 4.0f);
        dbClient.trials.set(trialData7);

        assertTrue(dbClient.trials.getList(null, ToolType.ZOOMMAPS,
                null,
                null, 50).size() >= 4);

        assertEquals(2, dbClient.trials.getList(configIdZm, ToolType.ZOOMMAPS,
                DateTime.now().minusHours(2).minusMinutes(10),
                DateTime.now(), 50).size());

        assertEquals(1, dbClient.trials.getList(configIdZm, ToolType.ZOOMMAPS,
                DateTime.now().minusHours(2).minusMinutes(10),
                DateTime.now().minusHours(1).minusMinutes(10), 50).size());

        assertEquals(0, dbClient.trials.getList(configIdZm, ToolType.ZOOMMAPS,
                DateTime.now().minusHours(4),
                DateTime.now().minusHours(3).minusMinutes(10), 50).size());

        assertEquals(1, dbClient.trials.getList(configIdZm, ToolType.ZOOMMAPS,
                null,
                DateTime.now().minusHours(2).minusMinutes(10), 50).size());

        assertEquals(3, dbClient.trials.getList(configIdZm, ToolType.ZOOMMAPS,
                DateTime.now().minusHours(4),
                null, 50).size());

        assertEquals(2, dbClient.trials.getList(configIdZm, ToolType.ZOOMMAPS,
                DateTime.now().minusHours(2).minusMinutes(10),
                null, 2).size());

        // CODECHARTS
        List<String> trialsCc = dbClient.trials.add(configIdCc, 3);
        TrialData trialData4 = new TrialData(ToolType.CODECHARTS, trialsCc.get(0), configIdCc,
                DateTime.now().minusHours(1), "Ja", new ArrayList<DataPoint>());
        trialData4.addDataPoint(new int[] { 1, 1 }, (new int[] { 1, 1 }));
        dbClient.trials.set(trialData4);

        TrialData trialData5 = new TrialData(ToolType.CODECHARTS, trialsCc.get(1), configIdCc,
                DateTime.now().minusHours(2), "Ja", new ArrayList<DataPoint>());
        trialData5.addDataPoint(new int[] { 2, 2 }, (new int[] { 2, 2 }));
        dbClient.trials.set(trialData5);

        TrialData trialData6 = new TrialData(ToolType.CODECHARTS, trialsCc.get(2), configIdCc,
                DateTime.now().minusHours(3), "Ja", new ArrayList<DataPoint>());
        trialData6.addDataPoint(new int[] { 2, 2 }, (new int[] { 2, 2 }));
        dbClient.trials.set(trialData6);

        String configIdCc2 = dbClient.configurations.set(codeConfig);
        List<String> trialsCc2 = dbClient.trials.add(configIdCc2, 1);

        TrialData trialData8 = new TrialData(ToolType.CODECHARTS, trialsCc2.get(0), configIdCc2,
                DateTime.now().minusHours(4), "Ja", new ArrayList<DataPoint>());
        trialData8.addDataPoint(new int[] { 4, 4 }, new int[] { 4, 4 });
        dbClient.trials.set(trialData8);

        assertTrue(dbClient.trials.getList(null, ToolType.CODECHARTS,
                null,
                null, 50).size() >= 4);

        assertEquals(2, dbClient.trials.getList(configIdCc, ToolType.CODECHARTS,
                DateTime.now().minusHours(2).minusMinutes(10),
                DateTime.now(), 50).size());

        assertEquals(1, dbClient.trials.getList(configIdCc, ToolType.CODECHARTS,
                DateTime.now().minusHours(2).minusMinutes(10),
                DateTime.now().minusHours(1).minusMinutes(10), 50).size());

        assertEquals(0, dbClient.trials.getList(configIdCc, ToolType.CODECHARTS,
                DateTime.now().minusHours(4),
                DateTime.now().minusHours(3).minusMinutes(10), 50).size());

        assertEquals(1, dbClient.trials.getList(configIdCc, ToolType.CODECHARTS,
                null,
                DateTime.now().minusHours(2).minusMinutes(10), 50).size());

        assertEquals(3, dbClient.trials.getList(configIdCc, ToolType.CODECHARTS,
                DateTime.now().minusHours(4),
                null, 50).size());

        assertEquals(2, dbClient.trials.getList(configIdCc, ToolType.CODECHARTS,
                DateTime.now().minusHours(2).minusMinutes(10),
                null, 2).size());

        // GEMISCHT
        assertTrue(dbClient.trials.getList(null, null,
                DateTime.now().minusHours(3).minusMinutes(10),
                DateTime.now().minusHours(1).minusMinutes(10), 11).size() >= 4);

        assertTrue(dbClient.trials.getList(null, null,
                null,
                null, 11).size() >= 8);

        assertTrue(dbClient.trials.getList(null, null,
                null,
                null, -1).size() >= 8);
    }

}
