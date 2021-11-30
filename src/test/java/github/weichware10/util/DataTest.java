package github.weichware10.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import github.weichware10.util.Enums.ToolType;
import org.junit.Ignore;
import org.junit.Test;


/**
 * Testet {@link Data}.
 */
public class DataTest {
    /**
     * Testet, ob ToolType richtig gesetzt wurde.
     */
    @Test
    public void toolTypeShouldBeSet() {
        Data zoomdata = new Data(ToolType.ZOOMMAPS, 0);
        assertEquals(ToolType.ZOOMMAPS, zoomdata.tooltype);

        Data eyetrackingdata = new Data(ToolType.EYETRACKING, 0);
        assertEquals(ToolType.EYETRACKING, eyetrackingdata.tooltype);

        Data codechartsdata = new Data(ToolType.CODECHARTS, 0);
        assertEquals(ToolType.CODECHARTS, codechartsdata.tooltype);
    }

    /**
     * Testet ob ConfigID richtig gesetzt wurde.
     */
    @Test
    public void configIdShouldBeCorrect() {
        int id1 = (int) Math.random() * 1000;
        Data data1 = new Data(ToolType.ZOOMMAPS, id1);
        assertEquals(id1, data1.configId);

        int id2 = (int) Math.random() * 1000;
        Data data2 = new Data(ToolType.EYETRACKING, id2);
        assertEquals(id2, data2.configId);

        int id3 = (int) Math.random() * 1000;
        Data data3 = new Data(ToolType.CODECHARTS, id3);
        assertEquals(id3, data3.configId);
    }

    /**
     * Testet ob die Daten richtig gesetzt wurden.
     * TODO: replace type with actual datatype!
     */
    @Test
    public void dataShouldBeCorrect() {
        Data falsedata = new Data(ToolType.CODECHARTS, 0);
        falsedata.setData(false);
        assertFalse(falsedata.getData());

        Data truedata = new Data(ToolType.CODECHARTS, 0);
        truedata.setData(true);
        assertTrue(truedata.getData());
    }

    /**
     * Testet, ob Daten richtig hinzugefügt wurden,
     * muss aber noch auf konkrete Implementation der Datenform warten.
     */
    @Test
    @Ignore
    public void dataShouldBeAddedCorrectly() {
        ;
    }

    /**
     * Testet, ob bei gleicher Hinzufügung der Daten das gleiche Datenobjekt erstellt wird.
     * TODO: equals-Operator erstellen
     * Wartet noch auf konkrete Implementation der Datenform.
     */
    @Test
    @Ignore
    public void addingTheSameDataCreatesAnEqualClass() {
        ;
    }
}
