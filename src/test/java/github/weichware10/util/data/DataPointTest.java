package github.weichware10.util.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
        DataPoint dataPoint1 = new DataPoint(id1, 0, new double[] { 0, 0 }, new double[] { 0, 0 });
        assertEquals("dataPoint1.dataId sollte " + id1 + " sein", id1, dataPoint1.dataId);

        int id2 = (int) Math.random() * 1000;
        DataPoint dataPoint2 = new DataPoint(id1, 0, new double[] { 0, 0 }, 0.0f,
            new double[] { 1, 2, 3, 4 });
        assertEquals("dataPoint2.dataId sollte " + id2 + " sein", id2, dataPoint2.dataId);
    }

    /**
     * Testet, ob timeOffset richtig gesetzt wird.
     */
    @Test
    public void timeOffsetShouldBeSetCorrectly() {
        int id1 = (int) Math.random() * 1000;
        DataPoint dataPoint1 = new DataPoint(0, id1, new double[] { 0, 0 }, new double[] { 0, 0 });
        assertEquals("dataPoint1.timeOffset sollte " + id1 + " sein",
                id1, dataPoint1.timeOffset, 0.0001f);

        int id2 = (int) Math.random() * 1000;
        DataPoint dataPoint2 = new DataPoint(0, id2, new double[] { 0, 0 }, 0.0f,
            new double[] { 1, 2, 3, 4 });
        assertEquals("dataPoint2.timeOffset sollte " + id2 + " sein",
                id2, dataPoint2.timeOffset, 0.0001f);
    }

    /**
     * Testet, ob coordinates richtig gesetzt werden.
     */
    @Test
    public void coordinatesShouldBeSetCorrectly() {
        double[] coordinates1 = new double[] { (double) Math.random() * 1000,
                                               (double) Math.random() * 1000 };
        DataPoint dataPoint1 = new DataPoint(0, 0, coordinates1, new double[] { 0, 0 });
        assertTrue("dataPoint1.coordinates sollten {" + coordinates1[0]
                + ", " + coordinates1[1] + "} sein", dataPoint1.coordinates.equals(coordinates1));

        double[] coordinates2 = new double[] { (double) Math.random() * 1000,
                                               (double) Math.random() * 1000 };
        DataPoint dataPoint2 = new DataPoint(0, 0, coordinates2, 0.0f,
            new double[] { 1, 2, 3, 4 });
        assertTrue("dataPoint2.coordinates sollten {" + coordinates2[0]
                + ", " + coordinates2[1] + "} sein", dataPoint2.coordinates.equals(coordinates2));
    }

    /**
     * Testet, ob rasterSize richtig gesetzt wird.
     */
    @Test
    public void rasterSizeShouldBeSetCorrectly() {
        double[] rasterSize1 = new double[] { (double) Math.random() * 1000,
                                              (double) Math.random() * 1000 };
        DataPoint dataPoint1 = new DataPoint(0, 0, rasterSize1, new double[] { 0, 0 });
        assertTrue("dataPoint1.rasterSize sollten {" + rasterSize1[0]
                + ", " + rasterSize1[1] + "} sein", dataPoint1.coordinates.equals(rasterSize1));

        double[] rasterSize2 = new double[] { (double) Math.random() * 1000,
                                              (double) Math.random() * 1000 };
        DataPoint dataPoint2 = new DataPoint(0, 0, rasterSize2, 0.0f,
            new double[] { 1, 2, 3, 4 });
        assertTrue("dataPoint2.rasterSize sollten {" + rasterSize2[0]
                + ", " + rasterSize2[1] + "} sein", dataPoint2.coordinates.equals(rasterSize2));
    }

    /**
     * Testet, ob zoomLevel richtig gesetzt wird.
     */
    @Test
    public void zoomLevelShouldBeSetCorrectly() {
        float zoomLevel1 = (float) Math.random() * 1000;
        DataPoint dataPoint1 = new DataPoint(0, 0, new double[] { 0, 0 }, zoomLevel1,
            new double[] { 1, 2, 3, 4 });
        assertEquals("dataPoint1.getZoomLevel() sollte " + zoomLevel1 + " sein",
                zoomLevel1, dataPoint1.zoomLevel, 0.0001f);
    }

    /**
     * Testet, ob viewport richtig gesetzt wird.
     */
    @Test
    public void viewportShouldBeSetCorrectly() {
        double[] viewport = new double[] { 1, 2, 3, 4 };
        DataPoint dataPoint1 = new DataPoint(0, 0, new double[] { 0, 0 }, 0.0f,
            viewport);
        assertTrue("dataPoint1.viewport sollten {" + viewport[0]
            + ", " + viewport[1] + ", " + viewport[2] + ", " + viewport[3] + "} sein",
            dataPoint1.viewport.equals(viewport));
    }

    /**
     * Testet, ob to String richtige Strings aneinander-stringt.
     */
    @Test
    public void toStringShouldStringGood() {
        DataPoint dataPoint1 = new DataPoint(1, 2, new double[] { 3, 4 }, 42,
            new double[] { 1, 2, 3, 4 });
        assertEquals("""
                DataPoint: {
                    dataId: 1,
                    timeOffset: 2,
                    coordinates: [3, 4],
                    rasterSize: null,
                    zoomLevel: 42.0,
                    viewport: [1, 2, 3, 4]
                }""", dataPoint1.toString());

        DataPoint dataPoint2 = new DataPoint(5, 6, new double[] { 7, 8 }, new double[] { 9, 10 });
        assertEquals("""
                DataPoint: {
                    dataId: 5,
                    timeOffset: 6,
                    coordinates: [7, 8],
                    rasterSize: [9, 10],
                    zoomLevel: null,
                    viewport: null
                }""", dataPoint2.toString());
    }
}
