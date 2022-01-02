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
    public final double[] coordinates; // ! double[2]
    public final double[] rasterSize; // ! double[2]
    public final Float zoomLevel;
    public final double[] viewport; //[4]

    /**
     * Konstruktor für Jackson.
     *
     * @param dataId      - the id of the dataPoint
     * @param timeOffset  - the time since the trial started
     * @param coordinates - the coordinates on the viewed picture
     * @param rasterSize  - width and height of the raster
     * @param zoomLevel   - how far the user is zoomed in
     * @param viewport    - aktueller Ausschnitt beim ZoomBild
     *
     * @since v1.0
     */
    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public DataPoint(@JsonProperty("dataId") int dataId,
            @JsonProperty("timeOffset") int timeOffset,
            @JsonProperty("coordinates") double[] coordinates,
            @JsonProperty("rasterSize") double[] rasterSize,
            @JsonProperty("zoomLevel") Float zoomLevel,
            @JsonProperty("viewport") double[] viewport) {
        this.dataId = dataId;
        this.timeOffset = timeOffset;
        this.coordinates = coordinates;
        this.rasterSize = rasterSize;
        this.zoomLevel = zoomLevel;
        this.viewport = viewport;
    }

    /**
     * Stores a single DataPoint with zoomLevel.
     *
     * @param dataId      - the id of the dataPoint
     * @param timeOffset  - the time since the trial started
     * @param coordinates - the coordinates of the viewed picture
     * @param zoomLevel   - how far the user is zoomed in
     * @param viewport    - aktueller Ausschnitt beim ZoomBild
     *
     * @since v0.2
     */
    public DataPoint(int dataId, int timeOffset, double[] coordinates, float zoomLevel,
                     double[] viewport) {
        this.dataId = dataId;
        this.timeOffset = timeOffset;
        this.coordinates = coordinates;
        this.rasterSize = null;
        this.zoomLevel = zoomLevel;
        this.viewport = viewport;
    }

    /**
     * Stores a single DataPoint without zoomLevel and viewport.
     *
     * @param dataId      - the id of the dataPoint
     * @param timeOffset  - the time since the trial started
     * @param coordinates - the coordinates on the viewed picture
     * @param rasterSize  - width and height of the raster
     *
     * @since v0.3
     */
    public DataPoint(int dataId, int timeOffset, double[] coordinates, double[] rasterSize) {
        this.dataId = dataId;
        this.timeOffset = timeOffset;
        this.coordinates = coordinates;
        this.rasterSize = rasterSize;
        this.zoomLevel = null;
        this.viewport = null;
    }

    @Override
    public String toString() {
        return String.format("""
                DataPoint: {
                    dataId: %d,
                    timeOffset: %d,
                    coordinates: %s,
                    rasterSize: %s,
                    zoomLevel: %s,
                    viewport: %s
                }""",
                dataId,
                timeOffset,
                Arrays.toString(coordinates),
                Arrays.toString(rasterSize),
                (zoomLevel == null) ? "null" : zoomLevel.toString(),
                Arrays.toString(viewport));
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
                        || viewport.equals(that.viewport))
                // Überprüfen ob viewport nicht gleichmäßig null / nicht null ist
                && ((viewport == null && that.viewport == null)
                        || (zoomLevel != null && that.zoomLevel != null))
                && ((viewport == null && that.viewport == null)
                        || viewport.equals(that.viewport));
    }
}
