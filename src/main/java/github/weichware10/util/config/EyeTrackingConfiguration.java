package github.weichware10.util.config;

/**
 * Beinhaltet Konfiguration für EyeTracking-Versuche.
 *
 * @deprecated Entwicklung von EyeTracking wird zunächst zurückgestellt.
 */
public class EyeTrackingConfiguration extends ToolConfiguration {
    protected float pollIntervall = 4.20f;

    // GETTER

    public float getPollIntervall() {
        return pollIntervall;
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
        EyeTrackingConfiguration that = (EyeTrackingConfiguration) (obj);
        return pollIntervall == that.pollIntervall;
    }

    @Override
    public String toString() {
        return String.format("""
                eyeTrackingConfiguration: {
                        %s, pollInterval: %f }""",
                super.toString(),
                pollIntervall);
    }
}
