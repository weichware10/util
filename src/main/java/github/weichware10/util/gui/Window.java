package github.weichware10.util.gui;

import github.weichware10.util.Logger;
import javafx.application.Platform;
import javafx.stage.Stage;

/**
 * Beinhaltet Refresh Methode.
 */
public class Window {

    /**
     * Erfrischt das Fenster.
     * Verändert Breite um +1 und dann um -1, was zu einem Refresh führt.
     *
     * @param primaryStage - Stage die erfrischt werden soll.
     */
    public static void refresh(Stage primaryStage) {

        if (java.awt.Toolkit.getDefaultToolkit().getScreenResolution() == 96) {
            Logger.debug("skipping refresh");
            return;
        }

        if (primaryStage.isMaximized()) {

            primaryStage.setWidth(primaryStage.getWidth() - 16);

            new Thread(new Runnable() {

                @Override
                public void run() {
                    Platform.runLater(() -> primaryStage.setWidth(primaryStage.getWidth() + 16));
                }

            }).start();

        } else {

            primaryStage.setWidth(primaryStage.getWidth() - 1);

            new Thread(new Runnable() {

                @Override
                public void run() {
                    Platform.runLater(() -> primaryStage.setWidth(primaryStage.getWidth() + 1));
                }

            }).start();

        }

    }

}
