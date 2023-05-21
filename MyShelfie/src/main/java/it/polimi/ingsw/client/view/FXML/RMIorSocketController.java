package it.polimi.ingsw.client.view.FXML;

import it.polimi.ingsw.observer_observable.Observable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;


public class RMIorSocketController extends Observable {
    @FXML
    public AnchorPane PaneforParquet;
    @FXML
    public RadioButton rmibutton;
    @FXML
    public RadioButton socketbutton;

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void createRMIconnection(MouseEvent mouseEvent) throws IOException {
            URL url = new File("src/main/resources/it/polimi/ingsw/client/view/FXML/AddressIp_scene.fxml/").toURI().toURL();

            root = FXMLLoader.load(url);
            stage = JavaFxMain.getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
    }

    public void createsocketconnection(MouseEvent mouseEvent) {

    }
}
