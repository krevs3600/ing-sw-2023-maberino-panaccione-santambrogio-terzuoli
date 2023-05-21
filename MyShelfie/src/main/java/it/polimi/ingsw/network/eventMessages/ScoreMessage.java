package it.polimi.ingsw.network.eventMessages;

import it.polimi.ingsw.network.MessageType;
import it.polimi.ingsw.network.MessagesToServer.MessageToClient;
import it.polimi.ingsw.network.MessagesToServer.MessageToClientType;
import it.polimi.ingsw.network.MessagesToServer.requestMessage.RequestMessage;

public class ScoreMessage extends EventMessage {
    private final long serialVersionUID = 1L;
    private int score;
    public ScoreMessage(String nickname, int score) {
        super(nickname, MessageType.SCORE);
        this.score = score;
    }

    public int getScore(){
        return this.score;
    }

    @Override
    public String toString(){
        return "" + getNickname() + "'s score is : " + getScore();
    }

}
