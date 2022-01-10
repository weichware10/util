package github.weichware10.util.config;

import java.util.Arrays;

/**
 * Beinhaltet Konfiguration für CodeCharts-Versuche.
 */
public class CodeChartsConfiguration {
    protected String stringId = "OBST";
    protected int[] initialSize = { 10, 20 };
    protected long[] timings = { 200, 200 };

    /**
     * leerer Konstruktor. Wird für Jackson gebraucht (json writer)
     * smooth criminal.
     */
    protected CodeChartsConfiguration() {
        // leer
    }

    /**
     * Konstruktor für die CodeCharts Konfiguration.
     *
     * @param stringid - String-ID für die CodeCharts Konfiguration
     * @param initialSize - Rastergröße der CodeCharts Konfiguration
     * @param timings - Zeit zum Wechsel von Bild zu Raster & Raster zu Eingabefeld
     *                  der CodeCharts Konfiguration
     * @param tutorial - Anzeige des Tutorials
     */
    public CodeChartsConfiguration(String stringid, int[] initialSize, long[] timings,
            boolean tutorial) {
        this.stringId = stringid;
        this.initialSize = initialSize;
        this.timings = timings;
    }

    // --- GETTERS ---

    public String getStringId() {
        return stringId;
    }

    public int[] getInitialSize() {
        return initialSize;
    }

    public long[] getTimings() {
        return timings;
    }

    // --- OVERRIDES ---

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        CodeChartsConfiguration that = (CodeChartsConfiguration) (obj);
        return stringId.equals(that.stringId)
                && Arrays.equals(initialSize, that.initialSize)
                && Arrays.equals(timings, that.timings);
    }

    @Override
    public String toString() {
        return String.format("""
                codeChartsConfiguration: {
                        stringId: %s, initialSize: %s, timings: %s }""",
                stringId,
                Arrays.toString(initialSize),
                Arrays.toString(timings));
    }
}
