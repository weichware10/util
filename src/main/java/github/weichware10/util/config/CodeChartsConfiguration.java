package github.weichware10.util.config;

import java.util.Arrays;

/**
 * Beinhaltet Konfiguration für CodeCharts-Versuche.
 */
public class CodeChartsConfiguration {
    /**
     * String-ID für die CodeCharts Konfiguration.
     */
    protected String stringId = "OBST";
    /**
     * Rastergröße der CodeCharts Konfiguration.
     */
    protected int[] initialSize = { 10, 20 };
    /**
     * Zeit zum Wechsel von Bild zu Raster & Raster zu Eingabefeld
     * der CodeCharts Konfiguration.
     */
    protected long[] timings = { 200, 200 };
    /**
     * ob das Raster bei der String-Anzeige angezeigt werden soll.
     */
    protected boolean showGrid = false;
    /**
     * ob sich die Rastergröße nach Eingaben ändert.
     */
    protected boolean relativeSize = true;
    /**
     * ob die Strings zufällig eingesetzt werden.
     */
    protected boolean randomized = true;
    /**
     * wie viele Durchläufe der Versuch haben soll.
     */
    protected Integer interations = 15;
    /**
     * wie oft sich ein Feld maximal teilen soll.
     * (nur bei {@code relativeSize = true})
     */
    protected Integer maxDepth = 5;
    /**
     * wie oft Felder horizontal geteilt werden sollen.
     * Einstellen eines negativen Wertes, um dynamisch längere Seite zu halbieren.
     * (nur bei {@code relativeSize = true})
     */
    protected Integer defaultHorizontal = 2;
    /**
     * wie oft Felder vertikal geteilt werden sollen.
     * Einstellen eines negativen Wertes, um dynamisch längere Seite zu halbieren.
     * (nur bei {@code relativeSize = true})
     */
    protected Integer defaultVertical = 2;

    /**
     * leerer Konstruktor. Wird für Jackson gebraucht (json writer)
     * smooth criminal.
     */
    protected CodeChartsConfiguration() {
        // leer
    }

    /**
     * Konstruktor für CodeCharts-Konfigurationen.
     *
     * @param stringId - String-ID für die CodeCharts Konfiguration
     * @param initialSize - Rastergröße der CodeCharts Konfiguration
     * @param timings - Zeit zum Wechsel von Bild zu Raster & Raster zu Eingabefeld
     *                  der CodeCharts Konfiguration
     * @param showGrid - ob das Raster bei der String-Anzeige angezeigt werden soll
     * @param relativeSize - ob sich die Rastergröße nach Eingaben ändert
     * @param randomized - ob die Strings zufällig eingesetzt werden
     * @param iterations - wie viele Durchläufe der Versuch haben soll
     * @param maxDepth - wie oft sich ein Feld maximal teilen soll
     *                  (nur bei {@code relativeSize = true})
     * @param defaultHorizontal - wie oft Felder horizontal geteilt werden sollen
     *                  Einstellen eines negativen Wertes, um dynamisch längere Seite zu halbieren
     *                  (nur bei {@code relativeSize = true})
     * @param defaultVertical - wie oft Felder vertikal geteilt werden sollen
     *                  Einstellen eines negativen Wertes, um dynamisch längere Seite zu halbieren
     *                  (nur bei {@code relativeSize = true})
     */
    public CodeChartsConfiguration(String stringId, int[] initialSize, long[] timings,
            boolean showGrid, boolean relativeSize, boolean randomized, int maxDepth,
            int iterations, int defaultHorizontal, int defaultVertical) {
        this.stringId = stringId;
        this.initialSize = initialSize;
        this.timings = timings;
        this.showGrid = showGrid;
        this.relativeSize = relativeSize;
        this.randomized = randomized;
        this.maxDepth = maxDepth;
        this.interations = iterations;
        this.defaultHorizontal = defaultHorizontal;
        this.defaultVertical = defaultVertical;
    }

    // --- GETTERS ---

    public String getStringId() {
        return stringId;
    }

    public long[] getTimings() {
        return timings;
    }

    public int[] getInitialSize() {
        return initialSize;
    }

    public boolean getShowGrid() {
        return showGrid;
    }

    public boolean getRelativeSize() {
        return relativeSize;
    }

    public boolean getRandomized() {
        return randomized;
    }

    public int getInterations() {
        return interations;
    }

    public int getMaxDepth() {
        return maxDepth;
    }

    public int getDefaultHorizontal() {
        return defaultHorizontal;
    }

    public int getDefaultVertical() {
        return defaultVertical;
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
                && Arrays.equals(timings, that.timings)
                && Arrays.equals(initialSize, that.initialSize)
                && (showGrid == that.showGrid)
                && (relativeSize == that.relativeSize)
                && (randomized == that.randomized)
                && (interations == that.interations)
                && (maxDepth == that.maxDepth)
                && (defaultHorizontal == that.defaultHorizontal)
                && (defaultVertical == that.defaultVertical);
    }

    @Override
    public String toString() {
        // CHECKSTYLE:OFF
        return String.format("""
                codeChartsConfiguration: {
                        stringId: %s, initialSize: %s, timings: %s, showGrid: %b, relativeSize: %b, randomized: %b, iterations %d, maxDepth: %s, defaultHorizontal: %s, defaultVertical: %s }""",
                stringId,
                Arrays.toString(initialSize),
                Arrays.toString(timings),
                showGrid,
                relativeSize,
                randomized,
                interations,
                (maxDepth != null) ? Integer.toString(maxDepth) : "null",
                (defaultHorizontal != null) ? Integer.toString(defaultHorizontal) : "null",
                (defaultVertical != null) ? Integer.toString(defaultVertical) : "null");
        // CHECKSTYLE:ON
    }
}
