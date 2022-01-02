package github.weichware10.util.config;

import java.util.Arrays;
import java.util.List;

/**
 * Beinhaltet Konfiguration für CodeCharts-Versuche.
 */
public class CodeChartsConfiguration extends ToolConfiguration {
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
     * @param imageUrl - Adresse des Bildes
     */
    public CodeChartsConfiguration(List<String> strings, int[] initialSize, long[] timings,
            boolean tutorial, String imageUrl) {
        this.strings = strings;
        this.initialSize = initialSize;
        this.timings = timings;
        this.tutorial = tutorial;
        this.imageUrl = imageUrl;
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
        if (!super.equals(obj)) {
            return false;
        }
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
                        %s, strings: %s, initialSize: %s, timings: %s }""",
                super.toString(),
                strings.toString(),
                Arrays.toString(initialSize),
                Arrays.toString(timings));
    }
}
