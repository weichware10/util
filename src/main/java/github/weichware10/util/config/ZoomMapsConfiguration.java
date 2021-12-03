package github.weichware10.util.config;

/**
 * Beinhaltet Konfiguration für ZoomMaps-Versuche.
 */
public class ZoomMapsConfiguration extends ToolConfiguration {
    protected float speed = 3f;

    // GETTER

    public float getSpeed() {
        return speed;
    }

    @Override
    public boolean equals(Object obj) {
        System.out.println("ZoomMapsConfiguration");
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
