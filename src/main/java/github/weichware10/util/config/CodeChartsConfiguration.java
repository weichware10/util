package github.weichware10.util.config;

/**
 * Beinhaltet Konfiguration für CodeCharts-Versuche.
 */
public class CodeChartsConfiguration extends ToolConfiguration {
    private String[] strings;
    private float initialSize;
    private float[] timings;

    // GETTERS

    public String[] getStrings() {
        return strings;
    }

    public float getInitialSize() {
        return initialSize;
    }

    public float[] getTimings() {
        return timings;
    }
}
