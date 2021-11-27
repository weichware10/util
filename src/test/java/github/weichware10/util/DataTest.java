package github.weichware10.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import github.weichware10.util.Enums.ToolType;
import org.junit.Test;


/**
 * Unit test for the Data class.
 */
public class DataTest {
    /**
     * Tests if the tooltype is set correctly.
     */
    @Test
    public void toolTypeShouldBeSet() {
        Data zoomdata = new Data(ToolType.ZOOM, 0);
        assertEquals(ToolType.ZOOM, zoomdata.tooltype);

        Data eyetrackingdata = new Data(ToolType.EYETRACKING, 0);
        assertEquals(ToolType.EYETRACKING, eyetrackingdata.tooltype);

        Data codechartsdata = new Data(ToolType.CODECHARTS, 0);
        assertEquals(ToolType.CODECHARTS, codechartsdata.tooltype);
    }

    /**
     * Tests if the config id was set correctly.
     */
    @Test
    public void configIdShouldBeCorrect() {
        int id1 = (int) Math.random() * 1000;
        Data data1 = new Data(ToolType.ZOOM, id1);
        assertEquals(id1, data1.configId);

        int id2 = (int) Math.random() * 1000;
        Data data2 = new Data(ToolType.EYETRACKING, id2);
        assertEquals(id2, data2.configId);

        int id3 = (int) Math.random() * 1000;
        Data data3 = new Data(ToolType.CODECHARTS, id3);
        assertEquals(id3, data3.configId);
    }

    // TODO: replace type with actual datatype!
    /**
     * Tests if the data was set correctly.
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
}
