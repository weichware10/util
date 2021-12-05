package github.weichware10.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import github.weichware10.util.Enums.ToolType;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Testet {@link TrailData}.
 */
public class TrialDataTest {
    /**
     * Testet, ob ToolType richtig gesetzt wurde.
     */
    @Test
    public void toolTypeShouldBeSet() {
        TrialData zoomdata = new TrialData(ToolType.ZOOMMAPS, "1", "1");
        assertEquals("zommdata sollte vom Typ ZOOMMAPS sein", ToolType.ZOOMMAPS, zoomdata.tooltype);

        TrialData eyetrackingdata = new TrialData(ToolType.EYETRACKING, "1", "1");
        assertEquals("zommdata sollte vom Typ EYETRACKING sein",
            ToolType.EYETRACKING, eyetrackingdata.tooltype);

        TrialData codechartsdata = new TrialData(ToolType.CODECHARTS, "1", "1");
        assertEquals("zommdata sollte vom Typ CODECHARTS sein",
            ToolType.CODECHARTS, codechartsdata.tooltype);
    }

    /**
     * Testet ob ConfigID richtig gesetzt wurde.
     */
    @Test
    public void configIdShouldBeCorrect() {
        String id1 = String.valueOf((int) Math.random() * 1000);
        TrialData data1 = new TrialData(ToolType.ZOOMMAPS, "1", id1);
        assertEquals(("configId von data1 sollte " + id1 + " sein"), id1, data1.configId);

        String id2 = String.valueOf((int) Math.random() * 1000);
        TrialData data2 = new TrialData(ToolType.EYETRACKING, "1", id2);
        assertEquals(("configId von data2 sollte " + id2 + " sein"), id2, data2.configId);

        String id3 = String.valueOf((int) Math.random() * 1000);
        TrialData data3 = new TrialData(ToolType.CODECHARTS, "1", id3);
        assertEquals(("configId von data3 sollte " + id3 + " sein"), id3, data3.configId);
    }

    /**
     * Testet ob TrialID richtig gesetzt wurde.
     */
    @Test
    public void trialIdShouldBeCorrect() {
        String id1 = String.valueOf((int) Math.random() * 1000);
        TrialData data1 = new TrialData(ToolType.ZOOMMAPS, id1, "1");
        assertEquals(("trialId von data1 sollte " + id1 + " sein"), id1, data1.trialId);

        String id2 = String.valueOf((int) Math.random() * 1000);
        TrialData data2 = new TrialData(ToolType.EYETRACKING, id2, "1");
        assertEquals(("trialId von data2 sollte " + id2 + " sein"), id2, data2.trialId);

        String id3 = String.valueOf((int) Math.random() * 1000);
        TrialData data3 = new TrialData(ToolType.CODECHARTS, id2, "1");
        assertEquals(("trialId von data3 sollte " + id3 + " sein"), id3, data3.trialId);
    }

    /**
     * Testet ob die Daten richtig gesetzt wurden.
     */
    @Test
    public void dataShouldBeCorrect() {
        TrialData data1 = new TrialData(ToolType.CODECHARTS, "1", "1");
        DataPoint dataPoint1 = new DataPoint(1, 2, new int[]{3, 4}, new int[]{5, 6});
        DataPoint dataPoint2 = new DataPoint(2, 3, new int[]{4, 5}, new int[]{6, 7});
        ArrayList<DataPoint> dataPoints1 = new ArrayList<DataPoint>(
            Arrays.asList(dataPoint1, dataPoint2));

        data1.setData(dataPoints1);
        assertTrue("dataPoints1 und data1.getData() sollten gleich sein",
            dataPoints1.equals(data1.getData()));

        TrialData data2 = new TrialData(ToolType.CODECHARTS, "1", "1");
        DataPoint dataPoint3 = new DataPoint(5, 6, new int[]{7, 8}, new int[]{9, 10});
        DataPoint dataPoint4 = new DataPoint(6, 7, new int[]{8, 9}, new int[]{10, 11});
        ArrayList<DataPoint> dataPoints2 = new ArrayList<DataPoint>(
            Arrays.asList(dataPoint3, dataPoint4));

        data2.setData(dataPoints2);

        assertTrue("dataPoints2 und data2.getData() sollten gleich sein",
            dataPoints2.equals(data2.getData()));
        assertFalse("data1.getData() und data2.getData() sollten nicht gleich sein",
            data1.getData().equals(data2.getData()));
    }

    /**
     * Testet, ob bei gleicher Hinzufügung der Daten das gleiche Datenobjekt erstellt wird.
     */
    @Test
    @Ignore
    public void addingTheSameDataCreatesAnEqualClass() {
        TrialData data1 = new TrialData(ToolType.CODECHARTS, "1", "1");
        TrialData data2 = new TrialData(ToolType.CODECHARTS, "1", "1");
        DataPoint dataPoint1 = new DataPoint(1, 2, new int[]{3, 4}, new int[]{5, 6});
        DataPoint dataPoint2 = new DataPoint(2, 3, new int[]{4, 5}, new int[]{6, 7});
        ArrayList<DataPoint> dataPoints1 = new ArrayList<DataPoint>(
            Arrays.asList(dataPoint1, dataPoint2));

        data1.setData(dataPoints1);
        data2.setData(dataPoints1);
        assertTrue("data1.getData() und data2.getData() sollten gleich sein",
            data1.getData().equals(data2.getData()));
    }
}
