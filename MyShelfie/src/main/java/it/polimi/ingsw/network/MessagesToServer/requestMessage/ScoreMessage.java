package it.polimi.ingsw.network.MessagesToServer.requestMessage;

import it.polimi.ingsw.network.MessageType;
import it.polimi.ingsw.network.MessagesToServer.MessageToClient;
import it.polimi.ingsw.network.MessagesToServer.MessageToClientType;

public class ScoreMessage extends RequestMessage {
    private final long serialVersionUID = 1L;
    private int score;
    private String nickname;
    public ScoreMessage(String nickname, int score) {
        super(MessageToClientType.SCORE);
        this.score = score;
        this.nickname = nickname;
    }

    public int getScore(){
        return this.score;
    }

    public String getNickname() {
        return nickname;
    }

    @Override
    public String toString(){
        return "" + getNickname() + "'s score is : " + getScore();
    }

}
