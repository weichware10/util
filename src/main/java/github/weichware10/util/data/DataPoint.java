package github.weichware10.util.data;

import java.util.Arrays;

/**
 * Stores a single DataPoint.
 *
 * @since v0.2
 */
public class DataPoint {
    public final int dataId;
    public final int timeOffset;
    public final int[] coordinates; // ! int[2]
    public final int[] rasterSize; // ! int[2]
    public final Float zoomLevel;

    /**
     * Stores a single DataPoint with zoomLevel.
     *
     * @param dataId      - the id of the dataPoint
     * @param timeOffset  - the time since the trial started
     * @param coordinates - the coordinates of the viewed picture
     * @param zoomLevel   - how far the user is zoomed in
     *
     * @since v0.2
     */
    public DataPoint(int dataId, int timeOffset, int[] coordinates, float zoomLevel) {
        this.dataId = dataId;
        this.timeOffset = timeOffset;
        this.coordinates = coordinates;
        this.rasterSize = null;
        this.zoomLevel = zoomLevel;
    }

    /**
     * Stores a single DataPoint without zoomLevel.
     *
     * @param dataId      - the id of the dataPoint
     * @param timeOffset  - the time since the trial started
     * @param coordinates - the coordinates on the viewed picture
     * @param rasterSize  - width and height of the raster
     *
     * @since v0.2
     */
    public DataPoint(int dataId, int timeOffset, int[] coordinates, int[] rasterSize) {
        this.dataId = dataId;
        this.timeOffset = timeOffset;
        this.coordinates = coordinates;
        this.rasterSize = rasterSize;
        this.zoomLevel = null;
    }

    /**
     * Stores a single DataPoint with everyting (for database).
     *
     * @param dataId      - the id of the dataPoint
     * @param timeOffset  - the time since the trial started
     * @param coordinates - the coordinates on the viewed picture
     * @param rasterSize  - width and height of the raster
     *
     * @since v0.3
     */
    public DataPoint(int dataId,
            int timeOffset, int[] coordinates, int[] rasterSize,
            float zoomLevel) {
        this.dataId = dataId;
        this.timeOffset = timeOffset;
        this.coordinates = coordinates;
        this.rasterSize = rasterSize;
        this.zoomLevel = zoomLevel;
    }

    @Override
    public String toString() {
        return String.format("""
                DataPoint: {
                    dataId: %d,
                    timeOffset: %d,
                    coordinates: %s,
                    rasterSize: %s,
                    zoomLevel: %s
                }""",
                dataId,
                timeOffset,
                Arrays.toString(coordinates),
                Arrays.toString(rasterSize),
                (zoomLevel == null) ? "null" : zoomLevel.toString());
    }
}
