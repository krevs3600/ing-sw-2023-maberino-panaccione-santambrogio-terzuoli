package it.polimi.ingsw.network.MessagesToClient.requestMessage;

import it.polimi.ingsw.network.MessagesToClient.MessageToClientType;

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
