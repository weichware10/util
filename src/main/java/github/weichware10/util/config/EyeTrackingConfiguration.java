package github.weichware10.util.config;

/**
 * Beinhaltet Konfiguration für EyeTracking-Versuche.
 */
public class EyeTrackingConfiguration extends ToolConfiguration {
    private float pollIntervall;

    // GETTER

    public float getPollIntervall() {
        return pollIntervall;
    }
}
