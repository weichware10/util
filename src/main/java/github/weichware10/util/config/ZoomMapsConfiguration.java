package github.weichware10.util.config;

/**
 * Beinhaltet Konfiguration für ZoomMaps-Versuche.
 */
public class ZoomMapsConfiguration extends ToolConfiguration {
    protected float speed = 3f;

    /**
     * leerer Konstruktor. Wird für Jackson gebraucht (json writer)
     * smooth criminal.
     */
    public ZoomMapsConfiguration() {
        // leer
    }

    /**
     * Konstruktor für die CodeCharts Konfiguration.
     *
     * @param speed - Zoomgeschwindigkeit
     * @param tutorial - Anzeige des Tutorials
     * @param imageUrl - Adressen des Bildes
     */
    public ZoomMapsConfiguration(float speed, boolean tutorial, String imageUrl) {
        this.speed = speed;
        this.tutorial = tutorial;
        this.imageUrl = imageUrl;
    }

    // --- GETTER ---

    public float getSpeed() {
        return speed;
    }

    // --- OVERRIDES ---

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
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
                        %s, speed: %f }""",
                super.toString(),
                speed);
    }
}
