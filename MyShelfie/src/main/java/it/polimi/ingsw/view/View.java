package it.polimi.ingsw.view;


/*import it.polimi.ingsw.model.ReducedGod;
import it.polimi.ingsw.model.board.Position;
import it.polimi.ingsw.model.board.ReducedSpace;
import it.polimi.ingsw.model.enumerations.Color;
*/

import it.polimi.ingsw.model.ModelView.GameView;
import it.polimi.ingsw.network.MessagesToClient.MessageToClient;
import it.polimi.ingsw.network.eventMessages.EventMessage;
import it.polimi.ingsw.network.Server;
import it.polimi.ingsw.view.gui.MenuController;
import it.polimi.ingsw.view.gui.RMIorSocketController;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
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
     * This method asks the user to answer with the type of connection he/she wants to establish: "r" for RMI
     * or "s" for Socket
     * @return the connection type chosen by the user if the input is valid
     */

    String askConnectionType();

    /**
     * This method is used to establish a connection with the {@link Server}, either via Socket or RMI, according to
     * what is specified by the user input. In CLI, the connection will be established with RMI if the user input
     * is "r", and Socket if it is "s". In GUI , depends on the button clicked by the user: the {@link RMIorSocketController RMI_button}
     * or the the {@link RMIorSocketController TCP_button}
     * @throws RemoteException when the remote call fails
     * @throws NotBoundException if there is no available {@link Server} with the binding specified by the method
     */

    void createConnection()  throws RemoteException, NotBoundException;

    /**
     * This method is used to ask a player the nickname with which he/she will be playing games
     */
    void askNickname();

    /**
     * This method is used to ask the user the address of the {@link Server} he wants to register to. If the user
     * just presses ENTER the local host will be selected
     * @return the address chosen by the user
     */

    String askServerAddress();


    /**
     * This method is used to ask the user the port he/she wants to use for the connection. If the user
     * just presses ENTER the default port will be selected
     * @return the address chosen by the user
     */
    int askServerPort();

    /**
     * This method is used to ask the player the number of players and game name which will characterize the
     * game he/she is creating
     */

    void askGameSpecs();


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
     * This method is used to determine whether the port provided by the user is valid or not
     * @param serverPort the port chosen by the user to be checked
     * @return a {@code boolean} value specifying if the port provided is valid
     */
    boolean isValidPort(String serverPort);

    /**
     * This method is used to determine whether the address provided by the user is valid or not
     * @param address the address chosen by the user to be checked
     * @return a {@code boolean} value specifying if the address provided is valid
     */
     boolean isValidIPAddress(String address);

    /**
     *This method is used to shows the different possible options to the user.
     * A user can decide to create or
     * join game, to resume or to reload one, or finally the user can just decide to exit the menu
     *
     */

    void gameMenu();

    /**
     * Notifies the {@link Server} that a user wants to resume an already existing game
     */
    void resumeGame();

    /**
     * Notifies the {@link Server} that a user wants to reload an already existing game
     */

    void reloadGame();



    /**
     * This method overrides the {@code Observer update}: it is called by the {@link Server} when an {@link EventMessage} is generated
     *  from the Model.
     *  According to the {@EventMessage #type}, this method interacts differently with the User
     * @param gameView the Observable class
     * @param eventMessage the specific event of which the class is notified
     */
     void update(GameView gameView, EventMessage eventMessage);

    /**
     *This method delete all the observers
     */

    void deleteAllObservers();


    /**
     * This method is used to notify the server
     * about the client's choice to enter a game after pressing the {@link MenuController#joinGame} button
     * in the fxml {@code CreateOrJoinGame_scene} or after typing "2" in the command line interface.
     */
    void joinGame();
}

