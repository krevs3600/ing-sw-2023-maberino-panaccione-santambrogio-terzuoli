package it.polimi.ingsw.client.view.FXML;

import it.polimi.ingsw.observer_observable.Observable;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;


/**
 * <h1>Class RMIorSocketController</h1>
 *this class represents the controller of the fxml scene where
 * the player chooses type of connection
 *
 * @author Francesca Pia Panaccione, Carlo Terzuoli
 * @version 1.0
 * @since 5/23/2023
 */
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


    /**
     * Getter method
     * @return the {@link RMIorSocketController#RMI} present in the {@link RMIorSocketController}
     * which is  a boolean that is "true" if the player has chosen the RMI connection type, false otherwise
     *
     */

    public boolean isRMI() {
        return RMI;
    }

    /**
     * Getter method
     * @return the {@link RMIorSocketController#socket} present in the {@link RMIorSocketController}
     * which is  a boolean that is "true" if the player has chosen the socket connection type, false otherwise
     *
     */

    public boolean isSocket(){
        return socket;
    }

    /**
     *
     This method is used to create the connection  as soon as the players confirms his choice by pressing the {@link RMIorSocketController RMIbutton}
     or the {@link RMIorSocketController TCPbutton} in the
     {@code RMIorSocket_scene}
     * @param event the click on the {@link RMIorSocketController RMIbutton} or on the {@link RMIorSocketController TCPbutton}
     */

    public void createConnection(MouseEvent event) throws IOException{
        if (RMIbutton.isSelected()) {
            RMI = true;
            socket = false;
            gui.createConnection();
        } else if (TCPbutton.isSelected()){
            RMI = false;
            socket = true;
            gui.createConnection();
        } else {
            gui.showPopup("Please select an option");
        }
    }

    /**
     * Setter to have a reference to the view
     * @param gui reference to the view
     */



    public void setGui(GUI gui) {
        this.gui=gui;
    }

}
