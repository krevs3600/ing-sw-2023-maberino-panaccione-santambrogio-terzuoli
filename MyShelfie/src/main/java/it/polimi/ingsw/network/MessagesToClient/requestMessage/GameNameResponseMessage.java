package it.polimi.ingsw.network.MessagesToClient.requestMessage;

import it.polimi.ingsw.network.MessagesToClient.MessageToClientType;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.network.Server;
import it.polimi.ingsw.model.Game;
/**
 * <h1>Class GameNameResponseMessage</h1>
 * The class GameNameResponseMessage extends the abstract class RequestMessage. The purpose of this message is for the {@link Server}
 * respond to a {@link Client} who has tried to create a new {@link Game}, telling him/her if the name of the {@link Game}
 * he/she chose is valid.
 *
 * @author Francesca Pia Panaccione
 * @version 1.0
 * @since 5/18/2023
 */
public class GameNameResponseMessage extends RequestMessage {
    private final long serialVersionUID = 1L;
    private final boolean validGameName;

   private final String GameName;

    /**
     * Class constructor
     * @param nickname of the {@link Client} whom to send the message to
     * @param GameName chose by the {@link Client}
     * @param validGameName {@code boolean} value telling if {@code GameName} is valid
     */
    public GameNameResponseMessage(String nickname, String GameName,boolean validGameName) {
        super(nickname, MessageToClientType.GAME_NAME_RESPONSE);
        this.validGameName = validGameName;
        this.GameName=GameName;
    }

    /**
     * method that tells if the provided {@code GameName} is valid
     */
    public boolean isValidGameName(){
        return validGameName;
    }

    /**
     * Getter method
     * @return GameName provided by the {@link Client}
     */
    public String getGameName(){
        return this.GameName;
    }
}
