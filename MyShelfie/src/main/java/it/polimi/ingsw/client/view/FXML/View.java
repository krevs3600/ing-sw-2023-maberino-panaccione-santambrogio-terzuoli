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

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Set;




/**
 * <h1>Interface View</h1>
 * This interface defines a generic view to be implemented by
 * each view type (e.g. CLI, GUI in JavaFX, ...).
 *
 * @author Francesco Maberino, Francesca Pia Panaccione,Francesco Santambrogio, Carlo Terzuoli
 * @version 1.0
 * @since 5/21/2023
 */

public interface View {

    /**
     * Asks the user for the type of connection he wants  (RMI or Socket)
     */

    String askConnectionType();

    /**
     * Creates a connection either of type RMI or Socket depending on the user's choice.
     */

    void createConnection()  throws RemoteException, NotBoundException;

    /**
     * Asks the user to choose a Nickname.
     */
    void askNickname();

    /**
     * Asks the user to choose a Server address.
     */

    String askServerAddress();


    /**
     * Asks the user to choose a Server port.
     */
    int askServerPort();

    /**
     * Asks the creator of the game for the specifications of the game: the name and the number of players.
     */

    void askGameSpecs();


    /**
     * Asks the creator of the game to choose a name for it.
     */
    void askGameName();

    /**
     * Shows the list of the available games to the users who want to join a game.
     * @param availableGameNames the list of the names of the games in the lobby with missing participants to start.
     */

    void showGameNamesList(Set<String> availableGameNames);


    /**
     * Shows a generic message.
     * @param message the message to be shown to the client.
     */

   void showMessage(MessageToClient message) throws IOException;

    /**
     * Asks the user how many players he wants to play with.
     * @param gameName the name of the game in which to choose the number of players.
     */
    void askNumberOfPlayers(String gameName);

    /**
     * Checks the validity of the port inserted by the user
     * @param serverPort the port chosen by the user to be checked
     */

    boolean isValidPort(String serverPort);

    /**
     * Checks the validity of the IPaddress inserted by the user
     * @param address the address chosen by the user to be checked
     */
     boolean isValidIPAddress(String address);

    /**
     * Shows the user the menu of the game
     * with the available options: create a new game, join a game or exit the game.
     */

    void gameMenu();



    /**
     * This method overrides the {@code Observer update}: it is called by an {@link Observable} class,
     * in this case a {@link GameView},
     * when it is notified about an event passed as an {@link EventMessage}.
     * According to the {@EventMessage #type}, this method interacts differently with the User
     * @param gameView the Observable class
     * @param eventMessage the specific event of which the class is notified
     */
     void update(GameView gameView, EventMessage eventMessage);

    void deleteAllObservers();
}

