package it.polimi.ingsw.network.eventMessages;

import it.polimi.ingsw.network.EventMessage;

public class ScoreMessage extends EventMessage {
    private int score;
    public ScoreMessage(String nickName, int score) {
        super(nickName, MessageType.SCORE);
        this.score = score;
    }

    public int getScore(){
        return this.score;
    }

    public String toString(){
        return "" + getNickName() + "'s score is : " + getScore();
    }
}
