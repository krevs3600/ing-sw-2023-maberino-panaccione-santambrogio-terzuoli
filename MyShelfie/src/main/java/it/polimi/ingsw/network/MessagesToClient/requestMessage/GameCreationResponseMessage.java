package it.polimi.ingsw.network.MessagesToClient.requestMessage;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.network.MessagesToClient.MessageToClientType;
import it.polimi.ingsw.network.Server;

/**
 * <h1>Class GameCreationResponseMessage</h1>
 * The class GameCreationResponseMessage extends the abstract class RequestMessage. The purpose of this message is for the {@link Server}
 * respond to a {@link Client} who has tried to create a new {@link Game}, telling it whether its attempt at creating
 * a new {@link Game} has been successful.
 *
 * @author Francesco Santambrogio, Carlo Terzuoli
 * @version 1.0
 * @since 5/18/2023
 */
public class GameCreationResponseMessage extends RequestMessage {
    private final long serialVersionUID = 1L;
    private final boolean validGameCreation;

    /**
     * Class constructor
     * @param nickname of the {@link Client} whom to send the message to
     * @param validGameCreation {@code  boolean} value that tells the {@link Client} if its attempt at creating a new
     * {@link Game} was successful
     */
    public GameCreationResponseMessage(String nickname, boolean validGameCreation) {
        super(nickname, MessageToClientType.GAME_CREATION);
        this.validGameCreation = validGameCreation;

    }

    /**
     * This method tells if the {@link Client}'s attempt at creating a new {@link Game} was successful
     */
    public boolean isValidGameCreation(){
        return validGameCreation;
    }
}
