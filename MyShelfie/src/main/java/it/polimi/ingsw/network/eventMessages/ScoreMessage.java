
package it.polimi.ingsw.network.eventMessages;

import it.polimi.ingsw.network.eventMessages.EventMessageType;
import it.polimi.ingsw.network.MessagesToClient.MessageToClient;
import it.polimi.ingsw.network.MessagesToClient.MessageToClientType;
import it.polimi.ingsw.network.MessagesToClient.requestMessage.RequestMessage;

import it.polimi.ingsw.network.MessagesToClient.MessageToClientType;

public class ScoreMessage extends EventMessage {
    private final long serialVersionUID = 1L;
    private int score;
    public ScoreMessage(String nickname, int score) {
        super(nickname, EventMessageType.SCORE);
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
