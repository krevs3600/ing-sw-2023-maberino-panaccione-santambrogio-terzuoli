package it.polimi.ingsw.network.MessagesToClient.requestMessage;

import it.polimi.ingsw.model.ModelView.GameView;
import it.polimi.ingsw.network.MessagesToClient.MessageToClientType;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.network.Server;
import it.polimi.ingsw.model.Game;

import java.util.Set;
/**
 * <h1>Class ResumeGameResponseMessage</h1>
 * The class ResumeGameResponseMessage extends the abstract class RequestMessage. This message is sent by the {@link Server}
 *  a {@link Client} who correctly tried to resume a {@link Game}
 *
 * @author Francesco Santambrogio
 * @version 1.0
 * @since 6/22/2023
 */
public class ResumeGameResponseMessage extends RequestMessage {
    private final long serialVersionUID = 1L;
    private final GameView gameView;

    /**
     * Class constructor
     * @param nickname of the {@link Client} whom to send the message to
     * @param gameView to be sent to the {@link Client}'s View once the {@link Game} resumes
     */
    public ResumeGameResponseMessage(String nickname, GameView gameView) {
        super(nickname, MessageToClientType.RESUME_GAME_RESPONSE);
        this.gameView = gameView;
    }

    /**
     * Getter method
     * @return the {@link GameView} to be sent to the {@link Client}'s View once the {@link Game} resumes
     */
    public GameView getGameView () {
        return this.gameView;
    }
}