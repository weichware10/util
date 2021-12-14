package github.weichware10.util.config;

import java.util.Arrays;
import java.util.List;

/**
 * von dieser Klasse erben alle Tool-Konfigurationen,
 * sie enthält wichtige Daten für alle Tools.
 */
abstract class ToolConfiguration {
    protected List<String> imageUrls = Arrays.asList(
            "www.weichware10.com/owlimage", "www.weichware10.com/running-owl");
    protected boolean tutorial = true;

    // --- GETTER ---

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public boolean getTutorial() {
        return tutorial;
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
        ToolConfiguration that = (ToolConfiguration) (obj);
        return imageUrls.equals(that.imageUrls)
                && tutorial == that.tutorial;
    }

    @Override
    public String toString() {
        return String.format("imageUrls: %s, tutorial: %b",
                imageUrls.toString(),
                tutorial);
    }
}
