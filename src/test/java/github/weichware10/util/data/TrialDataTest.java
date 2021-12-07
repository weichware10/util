package github.weichware10.util.data;

import static org.junit.Assert.assertEquals;

import github.weichware10.util.Enums.ToolType;
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
        assertEquals("zommdata sollte vom Typ ZOOMMAPS sein", ToolType.ZOOMMAPS, zoomdata.toolType);

        TrialData eyetrackingdata = new TrialData(ToolType.EYETRACKING, "1", "1");
        assertEquals("zommdata sollte vom Typ EYETRACKING sein",
                ToolType.EYETRACKING, eyetrackingdata.toolType);

        TrialData codechartsdata = new TrialData(ToolType.CODECHARTS, "1", "1");
        assertEquals("zommdata sollte vom Typ CODECHARTS sein",
                ToolType.CODECHARTS, codechartsdata.toolType);
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
     * Testet toString stringy Strings zurückgibt.
     */
    @Test
    public void stringShouldStringStringy() {
        TrialData data1 = new TrialData(ToolType.CODECHARTS, "1", "2");
        assertEquals(String.format("""
                TrialData: {
                    toolType: CODECHARTS
                    trialId: 1
                    configId: 2
                    startTime: %s
                    dataPoints: dataPoints[0]
                }""", data1.startTime.toString()),
                data1.toString());
        data1.addDataPoint(new int[] { 1, 2 }, new int[] { 3, 4 });
        assertEquals(String.format("""
                TrialData: {
                    toolType: CODECHARTS
                    trialId: 1
                    configId: 2
                    startTime: %s
                    dataPoints: dataPoints[1]
                }""", data1.startTime.toString()),
                data1.toString());

    }

    /**
     * Testet, ob bei gleicher Hinzufügung der Daten das gleiche Datenobjekt
     * erstellt wird.
     */
    @Test
    public void dataShouldBeSetCorrectly() {
        ;
    }
}
