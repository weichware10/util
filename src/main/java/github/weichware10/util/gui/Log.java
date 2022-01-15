package github.weichware10.util.gui;

import github.weichware10.util.Logger;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableBooleanValue;
import javafx.scene.Parent;
import javafx.stage.Stage;

/**
 * Dialog zum Darstellen der Log Infos.
 */
public class Log extends AbsScene {

    private static Stage logStage;
    private static Parent root;

    private static SimpleBooleanProperty visibleProperty = new SimpleBooleanProperty(false);
    public static ObservableBooleanValue visible = visibleProperty;

    /**
     * Startet das Log.
     */
    public static void start(String icon) {
        root = start(logStage,
                Log.class.getResource("Log.fxml"),
                root,
                null,
                "Toolbox - Log",
                null,
                500,
                500,
                icon).root;
    }

    public static boolean isVisible() {
        return visible.get();
    }

    public static void show() {
        logStage.show();
        visibleProperty.set(true);
    }

    public static void hide() {
        logStage.hide();
        visibleProperty.set(false);
    }

    public static void close() {
        logStage.close();
    }

    /**
     * Loggt den Inhalt (falls vorhanden) mit dem gegebenen Typ.
     *
     * @param content - zu loggender Inhalt
     * @param type - "DEBUG", "INFO", "WARN" oder "ERROR"
     */
    public static void log(String content, String type) {
        if (content.length() == 0) {
            return;
        }
        switch (type) {
            case "DEBUG":
                Logger.debug(content);
                break;
            case "INFO":
                Logger.info(content);
                break;
            case "WARN":
                Logger.warn(content);
                break;
            case "ERROR":
                Logger.error(content);
                break;
            default:
                break;
        }
    }
}
