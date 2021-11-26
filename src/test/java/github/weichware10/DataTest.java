package github.weichware10;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import github.weichware10.util.Data;
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
        assertEquals(zoomdata.tooltype, ToolType.ZOOM);
        Data eyetrackingdata = new Data(ToolType.EYETRACKING, 0);
        assertEquals(eyetrackingdata.tooltype, ToolType.EYETRACKING);
        Data codechartsdata = new Data(ToolType.CODECHARTS, 0);
        assertEquals(codechartsdata.tooltype, ToolType.CODECHARTS);
    }

    /**
     * Tests if the config id was set correctly.
     */
    @Test
    public void configIdShouldBeCorrect() {
        int id1 = (int) Math.random() * 1000;
        Data data1 = new Data(ToolType.ZOOM, id1);
        assertEquals(data1.configId, id1);

        int id2 = (int) Math.random() * 1000;
        Data data2 = new Data(ToolType.EYETRACKING, id2);
        assertEquals(data2.configId, id2);

        int id3 = (int) Math.random() * 1000;
        Data data3 = new Data(ToolType.CODECHARTS, id3);
        assertEquals(data3.configId, id3);
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
