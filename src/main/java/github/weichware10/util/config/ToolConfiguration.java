package github.weichware10.util.config;

import java.util.Arrays;

/**
 * von dieser Klasse erben alle Tool-Konfigurationen,
 * sie enthält wichtige Daten für alle Tools.
 */
abstract class ToolConfiguration {
    protected String[] imageUrls = {
        "www.weichware10.com/owlimage", "www.weichware10.com/running-owl" };
    protected boolean tutorial = true;

    // GETTER

    public String[] getImageUrls() {
        return imageUrls;
    }

    public boolean getTutorial() {
        return tutorial;
    }

    @Override
    public boolean equals(Object obj) {
        System.out.println("ToolConfiguration");

        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ToolConfiguration that = (ToolConfiguration) (obj);
        return Arrays.equals(imageUrls, that.imageUrls)
                && tutorial == that.tutorial;
    }

    @Override
    public String toString() {
        return String.format("imageUrls: %s, tutorial: %b",
                Arrays.toString(imageUrls),
                tutorial);
    }
}
