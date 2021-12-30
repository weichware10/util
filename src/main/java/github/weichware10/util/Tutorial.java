package github.weichware10.util;

import github.weichware10.util.config.ConfigClient;


/**
 * Grundlegende Tutorial-Klasse.
 *
 * <p>Erbende Tutorial-Klassen müssen nur die Tutorial-Funktion implementieren.
 *
 * <p>Erbende Tutorial-Klassen müssen in ihrem Constructor super() callen.
 *
 * <p>Aufgerufen wird die start()-Funktion.
 */
@SuppressWarnings("unused")
public abstract class Tutorial {
    private final ToolType toolType;
    private final ConfigClient configClient;

    /**
     * Instaniziiert ein neues Tutorial-Objekt.
     *
     * @param configClient - der ConfigClient für die Einstellungen
     * @param toolType - der ToolType des Tutorials
     */
    public Tutorial(ConfigClient configClient, ToolType toolType) {
        this.configClient = configClient;
        this.toolType = toolType;
    }

    /**
     * Startet die Ausführung des Tutorials.
     */
    public void start() {
        ;
    }

    /**
     * findet heraus, ob für das Tool, dass Tutorial angezeigt werden soll.
     *
     * @return configState -
     *     true, wenn angezeigt werden soll;
     *     false, wenn nicht angezeigt werden soll
     */
    private boolean getConfigState() {
        switch (toolType) {
            case CODECHARTS:
                return configClient.getConfig().getCodeChartsConfiguration().getTutorial();
            // case EYETRACKING:
            //     return configClient.getConfig().getEyeTrackingConfiguration().getTutorial();
            default: // ZOOMMAPS
                return configClient.getConfig().getZoomMapsConfiguration().getTutorial();
        }
    }

    /**
     * Zeigt das Tutorial an, mit OK-Button und Checkbox.
     * Ruft {@link #tutorial()} auf.
     *
     * @return showAgain - Auswahl der User,
     *     ob das Tutorial nochmal angezeigt werden soll.
     */
    private boolean show() {
        return false;
    }

    /**
     * Das Tutorial, welches angezeigt werden soll.
     */
    protected abstract void tutorial();
}
