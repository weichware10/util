package github.weichware10.util.config;

import java.util.List;

/**
 * Beinhaltet Konfiguration für ZoomMaps-Versuche.
 */
public class ZoomMapsConfiguration extends ToolConfiguration {
    protected float speed = 3f;

    // GETTER

    public ZoomMapsConfiguration() {
        // leer
    }

    /**
     * Konstruktor für die CodeCharts Konfiguration.
     *
     * @param speed - Zoomgeschwindigkeit
     * @param tutorial - Anzeige des Tutorials
     * @param imageUrls - Adressen der Bilder
     */
    public ZoomMapsConfiguration(float speed, boolean tutorial, List<String> imageUrls) {
        this.speed = speed;
        this.tutorial = tutorial;
        this.imageUrls = imageUrls;
    }

    public float getSpeed() {
        return speed;
    }

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
                zoomMapsConfiguration:{
                        %s, speed: %f }""",
                super.toString(),
                speed);
    }
}
