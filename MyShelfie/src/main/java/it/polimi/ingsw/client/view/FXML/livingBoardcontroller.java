package it.polimi.ingsw.client.view.FXML;
import it.polimi.ingsw.model.LivingRoomBoard;
import it.polimi.ingsw.model.utils.NumberOfPlayers;
import it.polimi.ingsw.network.message.NumOfPlayerMessage;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

public class livingBoardcontroller {
    @FXML
    GridPane board;

    LivingRoomBoard livingBoard=new LivingRoomBoard(NumberOfPlayers.TWO_PLAYERS);



}
