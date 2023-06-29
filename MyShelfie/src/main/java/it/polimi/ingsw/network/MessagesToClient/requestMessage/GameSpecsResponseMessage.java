package it.polimi.ingsw.network.MessagesToClient.requestMessage;

import it.polimi.ingsw.network.MessagesToClient.MessageToClientType;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.network.Server;
import it.polimi.ingsw.model.Game;
/**
 * <h1>Class GameSpecsResponseMessage</h1>
 * The class GameSpecsResponseMessage extends the abstract class RequestMessage. The purpose of this message is for the {@link Server}
 * respond to a {@link Client} who has tried to create a new {@link Game}, telling him/her if the attempt at creating
 * a new {@link Game} was successful
 *
 * @author Francesco Maberino
 * @version 1.0
 * @since 5/24/2023
 */
public class GameSpecsResponseMessage extends RequestMessage{

    private final long serialVersionUID = 1L;
    private final boolean validGameName;
    private final boolean validGameCreation;

    /**
     * Class constructor
     * @param nickname of the {@link Client} whom to send the message to
     * @param validGameName  {@code boolean} value telling if {@code GameName} is valid
     * @param validGameCreation {@code boolean} value telling if the attempt at creating a new {@link Game} was succesful
     */
    public GameSpecsResponseMessage(String nickname, boolean validGameName, boolean validGameCreation) {
        super(nickname, MessageToClientType.GAME_SPECS);
        this.validGameName = validGameName;
        this.validGameCreation = validGameCreation;
    }

    /**
     * Method that tells if the attempt at creating a new {@link Game} by the {@link Client} was successful
     */
    public boolean isValidGameCreation(){
        return validGameCreation;
    }

    /**
     * method that tells if the provided {@code GameName} is valid
     */
    public boolean isValidGameName(){
        return this.validGameName;
    }
}
