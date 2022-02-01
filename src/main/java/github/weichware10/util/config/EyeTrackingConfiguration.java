package github.weichware10.util.config;

/**
 * Beinhaltet Konfiguration für EyeTracking-Versuche.
 *
 * @deprecated Entwicklung von EyeTracking wird zunächst zurückgestellt.
 */
public class EyeTrackingConfiguration {
    protected float pollIntervall = 4.20f;

    public float getPollIntervall() {
        return pollIntervall;
    }

    @Override
    public boolean equals(Object obj) {
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
                        pollInterval: %f }""",
                pollIntervall);
    }
}
