package github.weichware10.util.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    public final Rectangle2D viewport; // ! double[4]
    public final Integer depth;

    /**
     * Konstruktor für Jackson.
     *
     * @param dataId     - the id of the dataPoint
     * @param timeOffset - the time since the trial started
     * @param viewport   - aktueller Ausschnitt beim ZoomBild
     *
     * @since v1.0
     */
    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public DataPoint(@JsonProperty("dataId") int dataId,
            @JsonProperty("timeOffset") int timeOffset,
            @JsonProperty("viewport") Map<String, Double> viewport,
            @JsonProperty("depth") Integer depth) {
        this.dataId = dataId;
        this.timeOffset = timeOffset;
        this.viewport = (viewport != null) ? new Rectangle2D(
                viewport.get("minX"),
                viewport.get("minY"),
                viewport.get("width"),
                viewport.get("height"))
                : null;
        this.depth = depth;
    }

    /**
     * Stores a single DataPoint without depth. (ZOOMMAPS)
     *
     * @param dataId     - the id of the dataPoint
     * @param timeOffset - the time since the trial started
     * @param viewport   - aktueller Ausschnitt beim ZoomBild
     *
     * @since v0.2
     */
    public DataPoint(int dataId, int timeOffset, Rectangle2D viewport) {
        this.dataId = dataId;
        this.timeOffset = timeOffset;
        this.viewport = viewport;
        this.depth = null;
    }

    /**
     * Stores a single DataPoint with depth (CODECHARTS).
     *
     * @param dataId     - the id of the dataPoint
     * @param timeOffset - the time since the trial started
     * @param viewport   - aktuell angeschautes Raster.
     * @param depth      - Rastertiefe
     *
     * @since v0.3
     */
    public DataPoint(int dataId, int timeOffset, Rectangle2D viewport, int depth) {
        this.dataId = dataId;
        this.timeOffset = timeOffset;
        this.viewport = viewport;
        this.depth = depth;
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
                    viewport: %s,
                    depth: %s
                }""",
                dataId,
                timeOffset,
                viewportStr,
                (depth != null) ? Integer.toString(depth) : "null");
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
        return dataId == that.dataId
                && timeOffset == that.timeOffset
                && (viewport == that.viewport
                        || (viewport.getMinX() == that.viewport.getMinX()
                                && viewport.getMinY() == that.viewport.getMinY()
                                && viewport.getWidth() == that.viewport.getWidth()
                                && viewport.getHeight() == that.viewport.getHeight()))
                && (depth == that.depth);
    }
}
