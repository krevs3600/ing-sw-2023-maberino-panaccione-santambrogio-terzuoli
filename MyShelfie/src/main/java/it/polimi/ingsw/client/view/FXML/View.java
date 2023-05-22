package it.polimi.ingsw.client.view.FXML;


/*import it.polimi.ingsw.model.ReducedGod;
import it.polimi.ingsw.model.board.Position;
import it.polimi.ingsw.model.board.ReducedSpace;
import it.polimi.ingsw.model.enumerations.Color;
*/

import it.polimi.ingsw.model.Bookshelf;
import it.polimi.ingsw.model.CommonGoalCard.CommonGoalCard;
import it.polimi.ingsw.model.ModelView.GameView;
import it.polimi.ingsw.model.ModelView.SpaceView;
import it.polimi.ingsw.model.Space;
import it.polimi.ingsw.network.MessagesToClient.MessageToClient;
import it.polimi.ingsw.network.eventMessages.EventMessage;
import javafx.beans.Observable;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Set;

/**
 * Defines a generic view to be implemented by each view type (e.g. CLI, GUI in JavaFX, ...).
 */
public interface View {

   String askConnectionType();


    void createConnection()  throws RemoteException, NotBoundException;
    /**
     * Asks the user to choose a Nickname.
     */
    void askNickname();

    String askServerAddress();

    int askServerPort();

     void askGameName();

    void showGameNamesList(Set<String> availableGameNames);


    /**
     * Shows a generic message.
     *
     * @param
     */

   void showMessage(MessageToClient message);




    /**
     * Asks the user how many players he wants to play with.
     */
    void askNumberOfPlayers(String gameName);


    boolean isValidPort(String serverPort);

 boolean isValidIPAddress(String address);

 void gameMenu();


 void update(GameView gameView, EventMessage eventMessage);



}

