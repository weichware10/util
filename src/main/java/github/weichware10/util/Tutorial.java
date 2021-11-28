package github.weichware10.util;

import github.weichware10.util.Enums.ToolType;

// TODO: Klären, ob das hier eher ein Interface ist!
// TODO: Benötigt Config-Klasse zur Fertigstellung

/**
 * Grundlegende Tutorial-Klasse.
 *
 * <p>Erbende Tutorial-Klassen müssen nur die Tutorial-Funktion implementieren.
 *
 * <p>Aufgerufen wird die start()-Funktion.
 */
public abstract class Tutorial {
    private ToolType toolType = null;

    public Tutorial(ToolType toolType) {
        this.toolType = toolType;
    }

    /**
     * Startet die Ausführung des Tutorials.
     */
    public void start() {
        ;
    }

    /**
     * findet heraus, ob für das Tutorial für das Tool angezeigt werden soll.
     *
     * @return configState -
     *     true, wenn angezeigt werden soll;
     *     false, wenn nicht angezeigt werden soll
     */
    private boolean getConfigState() {
        return false;
    }

    /**
     * Setzt den Konfigurationswert für das aktuelle Tool.
     *
     * @param state der zu setzende Wert
     */
    private void setConfigState(boolean state) {
        ;
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
