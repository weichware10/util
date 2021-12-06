package github.weichware10.util.config;

import java.util.Arrays;
import java.util.List;

/**
 * Beinhaltet Konfiguration für CodeCharts-Versuche.
 */
public class CodeChartsConfiguration extends ToolConfiguration {
    protected List<String> strings = Arrays.asList("foo", "bar");
    protected int[] initialSize = { 10, 20 };
    protected float[] timings = { 2.0f, 2.0f };

    // GETTERS

    public List<String> getStrings() {
        return strings;
    }

    public int[] getInitialSize() {
        return initialSize;
    }

    public float[] getTimings() {
        return timings;
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
