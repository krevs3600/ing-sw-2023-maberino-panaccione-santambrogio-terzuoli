package it.polimi.ingsw.client.view.FXML;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class CreateorJoinGameController {

    @FXML
    public Button CreateNewGame;

    @FXML
    public Button joinGame;

    @FXML
    public Button EXIT;

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
       gui.gameMenu();


    }

    public void ExitGame(MouseEvent mouseEvent) {

    }
}
