package it.polimi.ingsw.client.view.FXML;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * <h1>Class LobbyController</h1>
 *this class represents the controller of the fxml scene of the lobby where
 *  the player is waiting for the other players to join the game
 *
 * @author Francesca Pia Panaccione, Carlo Terzuoli
 * @version 1.0
 * @since 5/14/2023
 */

public class LobbyController {
    @FXML
    public Label numPinLobby;

    @FXML
    public Label NumberOfMissingPlayers;

    @FXML
    public Label PlayerJoinedGame;

    private GUI gui;

    private int PinLobby;

// todo eliminare se non ci serve
    public void setPinLobby(int num){
        this.PinLobby=num;
    }

    // todo eliminare se non ci serve
    public int getPinLobby() {
        return PinLobby;
    }


    /**
     * Setter to have a reference to the view
     * @param gui reference to the view
     */
    public void setGui(GUI gui) {
        this.gui=gui;
    }

}
