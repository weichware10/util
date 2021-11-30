package github.weichware10.util;

import github.weichware10.util.Enums.ToolType;
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
                return configClient.getConfig().codeChartsConfiguration.getTutorial();
            case EYETRACKING:
                return configClient.getConfig().eyeTrackingConfiguration.getTutorial();
            default: // ZOOMMAPS
                return configClient.getConfig().zoomMapsConfiguration.getTutorial();
        }
    }

    /**
     * Setzt den Konfigurationswert für das aktuelle Tool.
     *
     * @param state - der zu setzende Wert
     */
    private void setConfigState(boolean state) {
        switch (toolType) {
            case CODECHARTS:
                configClient.getConfig().codeChartsConfiguration.setTutorial(state);
                break;
            case EYETRACKING:
                configClient.getConfig().eyeTrackingConfiguration.setTutorial(state);
                break;
            default: // ZOOMMAPS
                configClient.getConfig().zoomMapsConfiguration.setTutorial(state);
                break;
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
