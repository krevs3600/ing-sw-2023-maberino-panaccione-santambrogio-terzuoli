package it.polimi.ingsw.client.view.FXML;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class CreateorJoinGameController {

    @FXML
    public Button CreateNewGame;

    @FXML
    public Button joinGame;

    @FXML
    public Button EXIT;

    @FXML
    public Group NoGamesInLobby;

    @FXML
    public Button OkBtn;

    @FXML
    public Label NoLobbyGamesText;
    @FXML
    public DialogPane NolobbygamesPane;


    private boolean joinGameb=false;

    private boolean createGame=false;


    public boolean getCreateGame(){
        return createGame;
    }


    public boolean getJoinGameb(){
        return  this.joinGameb;
    }
    private GUI gui;
   public void setGui(GUI gui){
       this.gui=gui;
   }

    public void CreateGame(MouseEvent mouseEvent) {
       createGame=true;
       gui.gameMenu();


    }

    public void JoinGame(MouseEvent mouseEvent) {
       joinGameb=true;
       //gui.gameMenu();
        gui.joinGame();


    }

    public void ExitGame(MouseEvent mouseEvent) {

    }

    public void PanelDisappearing(MouseEvent mouseEvent) {
        this.NolobbygamesPane.setVisible(false);
        this.OkBtn.setVisible(false);
        this.NoLobbyGamesText.setVisible(false);
    }
}
