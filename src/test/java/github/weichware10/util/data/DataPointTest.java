package github.weichware10.util.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javafx.geometry.Rectangle2D;
import org.junit.Test;

/**
 * Testet {@link DataPoint}.
 */
public class DataPointTest {
    /**
     * Testet, ob dataId richtig gesetzt wird.
     */
    @Test
    public void dataIdShouldBeSetCorrectly() {
        int id1 = (int) Math.random() * 1000;
        DataPoint dataPoint1 = new DataPoint(id1, 0, new Rectangle2D(1, 2, 3, 4));
        assertEquals("dataPoint1.dataId sollte " + id1 + " sein", id1, dataPoint1.dataId);

        int id2 = (int) Math.random() * 1000;
        DataPoint dataPoint2 = new DataPoint(id1, 0, new Rectangle2D(1, 2, 3, 4));
        assertEquals("dataPoint2.dataId sollte " + id2 + " sein", id2, dataPoint2.dataId);
    }

    /**
     * Testet, ob timeOffset richtig gesetzt wird.
     */
    @Test
    public void timeOffsetShouldBeSetCorrectly() {
        int id1 = (int) Math.random() * 1000;
        DataPoint dataPoint1 = new DataPoint(0, id1, new Rectangle2D(1, 2, 3, 4));
        assertEquals("dataPoint1.timeOffset sollte " + id1 + " sein",
                id1, dataPoint1.timeOffset, 0.0001f);

        int id2 = (int) Math.random() * 1000;
        DataPoint dataPoint2 = new DataPoint(0, id2, new Rectangle2D(1, 2, 3, 4));
        assertEquals("dataPoint2.timeOffset sollte " + id2 + " sein",
                id2, dataPoint2.timeOffset, 0.0001f);
    }

    /**
     * Testet, ob viewport richtig gesetzt wird.
     */
    @Test
    public void viewportShouldBeSetCorrectly() {
        Rectangle2D viewport = new Rectangle2D(1, 2, 3, 4);
        DataPoint dataPoint1 = new DataPoint(0, 0, viewport);
        assertTrue("dataPoint1.viewport sollten {" + viewport.getMinX()
                + ", " + viewport.getMinY() + ", " + viewport.getWidth()
                + ", " + viewport.getHeight() + "} sein",
                dataPoint1.viewport.equals(viewport));
    }

    /**
     * Testet, ob to String richtige Strings aneinander-stringt.
     */
    @Test
    public void toStringShouldStringGood() {
        DataPoint dataPoint1 = new DataPoint(1, 2, new Rectangle2D(1, 2, 3, 4));
        assertEquals("""
                DataPoint: {
                    dataId: 1,
                    timeOffset: 2,
                    viewport: [minX=1.0, minY=2.0, width=3.0, height=4.0],
                    depth: null
                }""", dataPoint1.toString());

        DataPoint dataPoint2 = new DataPoint(5, 6, new Rectangle2D(1, 2, 3, 4), 12);
        assertEquals("""
                DataPoint: {
                    dataId: 5,
                    timeOffset: 6,
                    viewport: [minX=1.0, minY=2.0, width=3.0, height=4.0],
                    depth: 12
                }""", dataPoint2.toString());
    }
}
