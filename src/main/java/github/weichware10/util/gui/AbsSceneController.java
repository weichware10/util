package github.weichware10.util.gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;


/**
 * abstrakte Klasse für Controller-Klassen.
 */
public abstract class AbsSceneController {

    @FXML
    protected ResourceBundle resources;
    @FXML
    protected URL location;

    @FXML
    protected abstract void initialize();

}
