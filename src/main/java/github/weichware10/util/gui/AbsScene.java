package github.weichware10.util.gui;

import github.weichware10.util.Logger;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Abstrakte Klasse, von der alle Szenen erben. Diese Klasse lädt vorallem FXML-Dateien.
 */
public abstract class AbsScene {

    /**
     * lädt die Szene intern und gibt die root-Instanz zurück.
     *
     * @param fxml - URL der FXML-Datei
     * @return das geladene Parent Objekt und die Controller-Instanz.
     */
    public static InitResult initialize(URL fxml) {

        FXMLLoader loader = new FXMLLoader(fxml);

        Parent root = null;
        try {
            root = loader.load();
        } catch (Exception e) {
            Logger.error("Error when loading " + fxml, e, true);
            System.exit(-1);
        }

        AbsSceneController controller = loader.getController();

        return new InitResult(root, controller);
    }

    /**
     * Setzt die MenuBar, falls das in initialize geladene root-Objekt BorderPane ist.
     *
     * @param menuBar - die zu setzende Menüleiste
     */
    public static void setMenuBar(MenuBar menuBar, Parent root) {
        if (root != null && root instanceof BorderPane) {
            BorderPane borderPane = (BorderPane) root;
            borderPane.setTop(menuBar);
        }
    }

    /**
     * Zeigt die Szene an.
     *
     * @param primaryStage - das Hauptfenster
     * @return das Initialisierungsergebniss
     */
    public static InitResult start(Stage primaryStage, URL fxml, Parent root,
            AbsSceneController controller, String title, MenuBar menuBar,
            Integer width, Integer height, String icon) {
        if (primaryStage == null || title == null || fxml == null) {
            throw new NullPointerException(
                    String.format("Stage primaryStage (%s), ",
                            (primaryStage != null) ? primaryStage.toString() : "null")
                            + String.format("String title (%s), ",
                                    (title != null) ? title : "null")
                            + String.format("and URL fxml (%s) are required",
                                    (fxml != null) ? fxml.toString() : "null"));
        }
        if (root == null) {
            InitResult ir = initialize(fxml);
            root = ir.root;
            controller = ir.controller;
        }
        // Menüleiste setzen
        if (menuBar != null) {
            setMenuBar(menuBar, root);
        }
        // Größe einstellen
        if (width != null && height != null) {
            primaryStage.setWidth(width);
            primaryStage.setHeight(height);
        }
        // auf primaryStage setzen
        Scene existingScene = root.getScene();
        if (existingScene != null) {
            primaryStage.setScene(existingScene);
        } else {
            primaryStage.setScene(new Scene(root));
        }
        if (icon != null) {
            primaryStage.getIcons().add(new Image(icon));
        }
        primaryStage.setTitle(title);
        Window.refresh(primaryStage);
        return new InitResult(root, controller);
    }

    /**
     * root und controller des Lade-Vorgangs.
     */
    public static class InitResult {
        public final Parent root;
        public final AbsSceneController controller;

        protected InitResult(Parent root, AbsSceneController controller) {
            this.root = root;
            this.controller = controller;
        }
    }
}
