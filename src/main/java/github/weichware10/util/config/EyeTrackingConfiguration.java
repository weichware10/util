package github.weichware10.util.config;

/**
 * Beinhaltet Konfiguration für EyeTracking-Versuche.
 */
public class EyeTrackingConfiguration extends ToolConfiguration {
    private float pollIntervall = 4.20f;

    // GETTER

    public float getPollIntervall() {
        return pollIntervall;
    }

    @Override
    public boolean equals(Object obj) {
        System.out.println("EyeTrackingConfiguration");

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
}
