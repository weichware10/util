package github.weichware10.util.config;

/**
 * Beinhaltet Konfiguration für ZoomMaps-Versuche.
 */
public class ZoomMapsConfiguration {
    protected double speed = 3;
    protected double imageViewWidth = 400;
    protected double imageViewHeight = 400;

    /**
     * leerer Konstruktor der für Jackson benötigt wird (json writer).
     */
    public ZoomMapsConfiguration() {
        // leer
    }

    /**
     * Konstruktor für die ZoomMaps Konfiguration.
     *
     * @param speed - Zoomgeschwindigkeit
     * @param imageViewWidth - Anzeige des Tutorials
     * @param imageViewHeight - Adressen des Bildes
     */
    public ZoomMapsConfiguration(double speed, double imageViewWidth, double imageViewHeight) {
        this.speed = speed;
        this.imageViewWidth = imageViewWidth;
        this.imageViewHeight = imageViewHeight;
    }

    public double getSpeed() {
        return speed;
    }

    public double getImageViewWidth() {
        return imageViewWidth;
    }

    public double getImageViewHeight() {
        return imageViewHeight;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ZoomMapsConfiguration that = (ZoomMapsConfiguration) (obj);
        return speed == that.speed;
    }

    @Override
    public String toString() {
        return String.format("""
                zoomMapsConfiguration: {
                        speed: %f, imageViewWidth: %f, imageViewHeight: %f }""",
                speed, imageViewWidth, imageViewHeight);
    }
}
