package github.weichware10.util.config;

/**
 * von dieser Klasse erben alle Tool-Konfigurationen,
 * sie enthält wichtige Daten für alle Tools.
 */
abstract class ToolConfiguration {
    protected String imageUrl = "www.weichware10.com/owlimage";
    protected boolean tutorial = true;

    // --- GETTER ---

    public String getImageUrl() {
        return imageUrl;
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
        return imageUrl.equals(that.imageUrl)
                && tutorial == that.tutorial;
    }

    @Override
    public String toString() {
        return String.format("imageUrls: %s, tutorial: %b",
                imageUrl.toString(),
                tutorial);
    }
}
