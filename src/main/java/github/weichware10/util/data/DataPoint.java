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
    public final double[] viewport; // ! double[4]

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
            @JsonProperty("viewport") double[] viewport) {
        this.dataId = dataId;
        this.timeOffset = timeOffset;
        this.coordinates = coordinates;
        this.rasterSize = rasterSize;
        this.viewport = viewport;
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
    public DataPoint(int dataId, int timeOffset, double[] viewport) {
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
                && Arrays.equals(rasterSize, that.rasterSize)
                && Arrays.equals(viewport, that.viewport);
    }
}
