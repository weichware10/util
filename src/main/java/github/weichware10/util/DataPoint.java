package github.weichware10.util;

/**
 * Stores a single DataPoint.
 */
public class DataPoint {
    public final int dataId;
    public final float timeOffset; // TODO: unit!
    public final int[] coordinates; // ! int[2]
    public final int[] rasterSize; // ! int[2]
    private float zoomLevel;

    /**
     * Stores a single DataPoint with zoomLevel.
     *
     * @param dataId - the id of the dataPoint
     * @param timeOffset - the time since the trial started
     * @param coordinates - the coordinates of the viewed picture
     * @param rasterSize - width and height of the raster
     * @param zoomLevel - the zoom strength // TODO: besser Beschreibung finden
     */
    public DataPoint(int dataId, float timeOffset, int[] coordinates, int[] rasterSize,
            float zoomLevel) {
        this.dataId = dataId;
        this.timeOffset = timeOffset;
        this.coordinates = coordinates;
        this.rasterSize = rasterSize;
        this.zoomLevel = zoomLevel;
    }

    /**
     * Stores a single DataPoint without zoomLevel.
     *
     * @param dataId - the id of the dataPoint
     * @param timeOffset - the time since the trial started
     * @param coordinates - the coordinates of the viewed picture
     * @param rasterSize - width and height of the raster
     */
    public DataPoint(int dataId, float timeOffset, int[] coordinates, int[] rasterSize) {
        this.dataId = dataId;
        this.timeOffset = timeOffset;
        this.coordinates = coordinates;
        this.rasterSize = rasterSize;
    }

    /**
     * get the saved zoomLevel.
     *
     * @return the saved zoomLevel
     */
    public float getZoomLevel() {
        return zoomLevel;
    }

}
