package it.polimi.ingsw.client.view.FXML;

import it.polimi.ingsw.observer_observable.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * <h1>Class StartController</h1>
 *this class represents the controller of the initial fxml scene where
 * the player chooses whether to close the game window or start playing
 *
 * @author Francesca Pia Panaccione, Carlo Terzuoli
 * @version 1.0
 * @since 5/23/2023
 */
public class StartController extends Observable{
    @FXML
    public AnchorPane rootPane;

    @FXML
    public Button playBtn;


    @FXML
    public Button quitBtn1;


    private GUI gui;
    /**
     * Setter to have a reference to the view
     * @param gui reference to the view
     */
    public void setGui(GUI gui){
        this.gui=gui;
    }

    /**
     *This method is used to close the main window
     */


    public void closeJavaFxMain(){
       GuiApp.getWindow().close();
    }

//TODO: refactor del metodo (createGame)

    /**
     *This method is used to switch from the initial fxml scene to the connection scene
     */
    public void switchToConnection(ActionEvent event) throws IOException {
        gui.askTypeofConnection(gui.getStage());
    }


}


