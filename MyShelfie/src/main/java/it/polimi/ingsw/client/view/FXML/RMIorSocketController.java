package it.polimi.ingsw.client.view.FXML;

import it.polimi.ingsw.observer_observable.Observable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
    public RadioButton RMIbutton;
    @FXML
    public RadioButton TCPbutton;
    @FXML
    public Button okButton;

    private Stage stage;
    private Scene scene;
    private Parent root;
    private GUI gui;

    private boolean RMI=false;

    private boolean socket=false;


    public boolean isRMI() {
        return RMI;
    }

    public boolean isSocket(){
        return socket;
    }

    public void createConnection(MouseEvent event) throws IOException{
        if (RMIbutton.isSelected()) {
            RMI = true;
            socket = false;
            gui.createConnection();
        } else if (TCPbutton.isSelected()){
            RMI = false;
            socket = true;
            gui.createConnection();
        }
    }

    public void setGui(GUI gui) {
        this.gui=gui;
    }

}
