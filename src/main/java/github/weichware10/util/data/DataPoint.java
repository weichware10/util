package github.weichware10.util.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Arrays;
import java.util.Map;
import javafx.geometry.Rectangle2D;

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
    public final Rectangle2D viewport; // ! double[4]

    /**
     * Konstruktor für Jackson.
     *
     * @param dataId      - the id of the dataPoint
     * @param timeOffset  - the time since the trial started
     * @param coordinates - the coordinates on the viewed picture
     * @param rasterSize  - width and height of the raster
     * @param viewport    - aktueller Ausschnitt beim ZoomBild
     *
     * @since v1.0
     */
    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public DataPoint(@JsonProperty("dataId") int dataId,
            @JsonProperty("timeOffset") int timeOffset,
            @JsonProperty("coordinates") double[] coordinates,
            @JsonProperty("rasterSize") double[] rasterSize,
            @JsonProperty("viewport") Map<String, Double> viewport) {
        this.dataId = dataId;
        this.timeOffset = timeOffset;
        this.coordinates = coordinates;
        this.rasterSize = rasterSize;
        this.viewport = (viewport != null) ? new Rectangle2D(
                viewport.get("minX"),
                viewport.get("minY"),
                viewport.get("width"),
                viewport.get("height"))
                : null;
    }

    /**
     * Stores a single DataPoint with viewport.
     *
     * @param dataId      - the id of the dataPoint
     * @param timeOffset  - the time since the trial started
     * @param viewport    - aktueller Ausschnitt beim ZoomBild
     *
     * @since v0.2
     */
    public DataPoint(int dataId, int timeOffset, Rectangle2D viewport) {
        this.dataId = dataId;
        this.timeOffset = timeOffset;
        this.coordinates = null;
        this.rasterSize = null;
        this.viewport = viewport;
    }

    /**
     * Stores a single DataPoint without viewport.
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
        this.viewport = null;
    }

    @Override
    public String toString() {
        String viewportStr = "null";
        if (viewport != null) {
            viewportStr = "[minX=";
            viewportStr += viewport.getMinX() + ", minY=";
            viewportStr += viewport.getMinY() + ", width=";
            viewportStr += viewport.getWidth() + ", height=";
            viewportStr += viewport.getHeight() + "]";
        }
        return String.format("""
                DataPoint: {
                    dataId: %d,
                    timeOffset: %d,
                    coordinates: %s,
                    rasterSize: %s,
                    viewport: %s
                }""",
                dataId,
                timeOffset,
                Arrays.toString(coordinates),
                Arrays.toString(rasterSize),
                viewportStr);
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
                && Arrays.equals(rasterSize, that.rasterSize)
                && viewport == that.viewport || (
                    viewport.getMinX() == that.viewport.getMinX()
                    && viewport.getMinY() == that.viewport.getMinY()
                    && viewport.getWidth() == that.viewport.getWidth()
                    && viewport.getHeight() == that.viewport.getHeight()
                    );
    }
}
