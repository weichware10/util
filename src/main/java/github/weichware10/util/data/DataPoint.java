package github.weichware10.util.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
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
     * Konstruktor für Jackson.
     *
     * @param dataId      - the id of the dataPoint
     * @param timeOffset  - the time since the trial started
     * @param coordinates - the coordinates on the viewed picture
     * @param rasterSize  - width and height of the raster
     * @param zoomLevel   - how far the user is zoomed in
     *
     * @since v1.0
     */
    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public DataPoint(@JsonProperty("dataId") int dataId,
            @JsonProperty("timeOffset") int timeOffset,
            @JsonProperty("coordinates") int[] coordinates,
            @JsonProperty("rasterSize") int[] rasterSize,
            @JsonProperty("zoomLevel") Float zoomLevel) {
        this.dataId = dataId;
        this.timeOffset = timeOffset;
        this.coordinates = coordinates;
        this.rasterSize = rasterSize;
        this.zoomLevel = zoomLevel;
    }

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
     * @since v0.3
     */
    public DataPoint(int dataId, int timeOffset, int[] coordinates, int[] rasterSize) {
        this.dataId = dataId;
        this.timeOffset = timeOffset;
        this.coordinates = coordinates;
        this.rasterSize = rasterSize;
        this.zoomLevel = null;
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

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        DataPoint that = (DataPoint) (other);
        return dataId == that.dataId && timeOffset == that.timeOffset
                && Arrays.equals(coordinates, that.coordinates)
                // Überprüfen ob rasterSize nicht gleichmäßig null / nicht null ist
                && ((rasterSize == null && that.rasterSize == null)
                        || (rasterSize != null && that.rasterSize != null))
                && ((rasterSize == null && that.rasterSize == null)
                        || Arrays.equals(rasterSize, that.rasterSize))
                // Überprüfen ob zoomLevel nicht gleichmäßig null / nicht null ist
                && ((zoomLevel == null && that.zoomLevel == null)
                        || (zoomLevel != null && that.zoomLevel != null))
                && ((zoomLevel == null && that.zoomLevel == null)
                        || zoomLevel.equals(that.zoomLevel));
    }
}
