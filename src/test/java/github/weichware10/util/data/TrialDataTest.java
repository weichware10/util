package github.weichware10.util.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

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
        TrialData zoomdata = new TrialData(ToolType.ZOOMMAPS, "1", "1", "nichts");
        assertEquals("zommdata sollte vom Typ ZOOMMAPS sein", ToolType.ZOOMMAPS, zoomdata.toolType);

        TrialData eyetrackingdata = new TrialData(ToolType.EYETRACKING, "1", "1", "nichts");
        assertEquals("zommdata sollte vom Typ EYETRACKING sein",
                ToolType.EYETRACKING, eyetrackingdata.toolType);

        TrialData codechartsdata = new TrialData(ToolType.CODECHARTS, "1", "1", "nichts");
        assertEquals("zommdata sollte vom Typ CODECHARTS sein",
                ToolType.CODECHARTS, codechartsdata.toolType);
    }

    /**
     * Testet ob ConfigID richtig gesetzt wurde.
     */
    @Test
    public void configIdShouldBeCorrect() {
        String id1 = String.valueOf((int) Math.random() * 1000);
        TrialData data1 = new TrialData(ToolType.ZOOMMAPS, "1", id1, "nichts");
        assertEquals(("configId von data1 sollte " + id1 + " sein"), id1, data1.configId);

        String id2 = String.valueOf((int) Math.random() * 1000);
        TrialData data2 = new TrialData(ToolType.EYETRACKING, "1", id2, "nichts");
        assertEquals(("configId von data2 sollte " + id2 + " sein"), id2, data2.configId);

        String id3 = String.valueOf((int) Math.random() * 1000);
        TrialData data3 = new TrialData(ToolType.CODECHARTS, "1", id3, "nichts");
        assertEquals(("configId von data3 sollte " + id3 + " sein"), id3, data3.configId);
    }

    /**
     * Testet ob TrialID richtig gesetzt wurde.
     */
    @Test
    public void trialIdShouldBeCorrect() {
        String id1 = String.valueOf((int) Math.random() * 1000);
        TrialData data1 = new TrialData(ToolType.ZOOMMAPS, id1, "1", "nichts");
        assertEquals(("trialId von data1 sollte " + id1 + " sein"), id1, data1.trialId);

        String id2 = String.valueOf((int) Math.random() * 1000);
        TrialData data2 = new TrialData(ToolType.EYETRACKING, id2, "1", "nichts");
        assertEquals(("trialId von data2 sollte " + id2 + " sein"), id2, data2.trialId);

        String id3 = String.valueOf((int) Math.random() * 1000);
        TrialData data3 = new TrialData(ToolType.CODECHARTS, id2, "1", "nichts");
        assertEquals(("trialId von data3 sollte " + id3 + " sein"), id3, data3.trialId);
    }

    /**
     * Testet ob answer richtig gesetzt wurde.
     */
    @Test
    public void answerShouldBeCorrect() {
        TrialData data1 = new TrialData(ToolType.ZOOMMAPS, "1", "1", "nichts");
        assertEquals(("answer von data1 sollte 'nichts' sein"), "nichts", data1.answer);
    }

    /**
     * Testet ob toString stringy Strings zurückgibt.
     */
    @Test
    public void stringShouldStringStringy() {
        TrialData data1 = new TrialData(ToolType.CODECHARTS, "1", "2", "nichts");
        assertEquals(String.format("""
                TrialData: {
                    toolType: CODECHARTS
                    trialId: 1
                    configId: 2
                    startTime: %s
                    answer: nichts
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
                    answer: nichts
                    dataPoints: dataPoints[1]
                }""", data1.startTime.toString()),
                data1.toString());

        TrialData data2 = new TrialData(ToolType.ZOOMMAPS, "1", "2", "nichts");
        assertEquals(String.format("""
                TrialData: {
                    toolType: ZOOMMAPS
                    trialId: 1
                    configId: 2
                    startTime: %s
                    answer: nichts
                    dataPoints: dataPoints[0]
                }""", data2.startTime.toString()),
                data2.toString());
        data2.addDataPoint(new int[] { 1, 2 }, 42.0f);
        assertEquals(String.format("""
                TrialData: {
                    toolType: ZOOMMAPS
                    trialId: 1
                    configId: 2
                    startTime: %s
                    answer: nichts
                    dataPoints: dataPoints[1]
                }""", data2.startTime.toString()),
                data2.toString());
    }

    /**
     * Testet, ob Länge der übegebenen Arrays ungleich 2 ist.
     */
    @Test
    public void wrongListSizeShouldThrow() {
        TrialData data1 = new TrialData(ToolType.ZOOMMAPS, "1", "2", "nichts");
        assertThrows(IllegalArgumentException.class, () -> {
            data1.addDataPoint(new int[] { 1, 2, 3 }, 42);
        });

        TrialData data2 = new TrialData(ToolType.CODECHARTS, "1", "2", "nichts");
        assertThrows(IllegalArgumentException.class, () -> {
            data2.addDataPoint(new int[] { 1, 2, 3 }, new int[] { 1, 2 });
        });
        assertThrows(IllegalArgumentException.class, () -> {
            data2.addDataPoint(new int[] { 1, 2 }, new int[] { 1, 2, 3 });
        });
        assertThrows(IllegalArgumentException.class, () -> {
            data2.addDataPoint(new int[] { 1, 2, 3 }, new int[] { 1, 2, 3 });
        });
    }

    /**
     * Testet, ob bei dem Versuch die Daten für den falschen ToolType
     * hinzuzufügen ein Error geworfen wird.
     */
    @Test
    public void wrongToolTypeShouldThrow() {
        TrialData data1 = new TrialData(ToolType.ZOOMMAPS, "1", "2", "nichts");
        assertThrows(IllegalArgumentException.class, () -> {
            data1.addDataPoint(new int[] { 1, 2 }, new int[] { 1, 2 });
        });

        TrialData data2 = new TrialData(ToolType.CODECHARTS, "1", "2", "nichts");
        assertThrows(IllegalArgumentException.class, () -> {
            data2.addDataPoint(new int[] { 1, 2 }, 42);
        });
    }

    @Test
    public void shouldGetDataCorrectly() {
        TrialData data1 = new TrialData(ToolType.ZOOMMAPS, "1", "2", "nichts");
        data1.addDataPoint(new int[] { 1, 2 }, 42.0f);
        DataPoint dataPoint1 = data1.getData().get(0);
        assertTrue(data1.getData().get(0).equals(dataPoint1));
    }
}
