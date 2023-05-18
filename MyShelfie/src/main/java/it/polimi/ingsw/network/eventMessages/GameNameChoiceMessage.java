package it.polimi.ingsw.network.eventMessages;

import it.polimi.ingsw.model.ModelView.BookshelfView;
import it.polimi.ingsw.network.MessageType;

public class GameNameChoiceMessage extends EventMessage {

    private final long serialVersionUID = 1L;
    String gameChoice;
    public GameNameChoiceMessage(String nickName, String gameChoice) {
        super(nickName, MessageType.GAME_CHOICE);
        this.gameChoice = gameChoice;
    }

    public String getGameChoice () { return  gameChoice;}

    public String toString(){
        return "" + getNickName() + " has chosen the " + getGameChoice() + " game";
    }
}
