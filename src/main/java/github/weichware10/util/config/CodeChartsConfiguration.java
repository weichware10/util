package github.weichware10.util.config;

import java.util.Arrays;
import java.util.List;

/**
 * Beinhaltet Konfiguration für CodeCharts-Versuche.
 */
public class CodeChartsConfiguration {
    protected List<String> strings = Arrays.asList("foo", "bar");
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
     * @param strings - Strings für die CodeCharts Konfiguration
     * @param initialSize - Rastergröße der CodeCharts Konfiguration
     * @param timings - Zeit zum Wechsel von Bild zu Raster & Raster zu Eingabefeld
     *                  der CodeCharts Konfiguration
     * @param tutorial - Anzeige des Tutorials
     */
    public CodeChartsConfiguration(List<String> strings, int[] initialSize, long[] timings,
            boolean tutorial) {
        this.strings = strings;
        this.initialSize = initialSize;
        this.timings = timings;
    }

    // --- GETTERS ---

    public List<String> getStrings() {
        return strings;
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
        return strings.equals(that.strings)
                && Arrays.equals(initialSize, that.initialSize)
                && Arrays.equals(timings, that.timings);
    }

    @Override
    public String toString() {
        return String.format("""
                codeChartsConfiguration: {
                        strings: %s, initialSize: %s, timings: %s }""",
                strings.toString(),
                Arrays.toString(initialSize),
                Arrays.toString(timings));
    }
}
