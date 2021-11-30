package github.weichware10.util.config;

/**
 * von dieser Klasse erben alle Tool-Konfigurationen,
 * sie enthält wichtige Daten für alle Tools.
 */
abstract class ToolConfiguration {
    private String imageUrl = "www.weichware10.com/owlimage";
    private boolean tutorial = true;

    // GETTER

    public String getImageUrl() {
        return imageUrl;
    }

    public boolean getTutorial() {
        return tutorial;
    }

    public void setTutorial(boolean tutorial) {
        this.tutorial = tutorial;
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
        return imageUrl.equals(that.imageUrl)
                && tutorial == that.tutorial;
    }

    @Override
    public String toString() {
        return String.format("imageUrl: %s, tutorial: %b",
                imageUrl,
                tutorial);
    }
}
