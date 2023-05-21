package it.polimi.ingsw.network.eventMessages;

public class GameNameChoiceMessage extends EventMessage {

    private final long serialVersionUID = 1L;
    String gameChoice;
    public GameNameChoiceMessage(String nickName, String gameChoice) {
        super(nickName, EventMessageType.GAME_CHOICE);
        this.gameChoice = gameChoice;
    }

    public String getGameChoice () { return  gameChoice;}

    public String toString(){
        return "" + getNickname() + " has chosen the " + getGameChoice() + " game";
    }
}
