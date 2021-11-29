package github.weichware10.util.config;

/**
 * von dieser Klasse erben alle Tool-Konfigurationen,
 * sie enthält wichtige Daten für alle Tools.
 */
abstract class ToolConfiguration {
    private String imageUrl;
    private boolean tutorial;

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
}
