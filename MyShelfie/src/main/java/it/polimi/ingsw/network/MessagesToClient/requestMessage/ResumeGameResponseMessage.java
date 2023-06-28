package it.polimi.ingsw.network.MessagesToClient.requestMessage;

import it.polimi.ingsw.model.ModelView.GameView;
import it.polimi.ingsw.network.MessagesToClient.MessageToClientType;

import java.util.Set;

public class ResumeGameResponseMessage extends RequestMessage {
    private final long serialVersionUID = 1L;
    private final GameView gameView;
    public ResumeGameResponseMessage(String nickname, GameView gameView) {
        super(nickname, MessageToClientType.RESUME_GAME_RESPONSE);
        this.gameView = gameView;
    }

    public GameView getGameView () {
        return this.gameView;
    }
}