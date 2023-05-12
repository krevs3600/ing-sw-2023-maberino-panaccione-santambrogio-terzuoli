package it.polimi.ingsw.network.eventMessages;

import it.polimi.ingsw.network.EventMessage;

public class NumOfPlayerMessage extends EventMessage {
    private final long serialVersionUID = 1L;
    private final int numOfPlayers;
    public NumOfPlayerMessage(String nickName, int numOfPlayers) {
        super(nickName, MessageType.NUM_OF_PLAYERS);
        this.numOfPlayers = numOfPlayers;
    }

    public int getNumOfPlayers() {
        return numOfPlayers;
    }

    @Override
    public String toString(){
        return getNickName() + " wants to create a game with " + String.valueOf(getNumOfPlayers()) + " players";
    }
}
