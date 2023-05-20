package it.polimi.ingsw.network.eventMessages;

import it.polimi.ingsw.network.MessageType;

public class GameCreationMessage extends EventMessage {
    private final long serialVersionUID = 1L;
    private final int numOfPlayers;
    private final String gameName;
    public GameCreationMessage(String nickName, int numOfPlayers, String gameName) {
        super(nickName, MessageType.GAME_CREATION);
        this.numOfPlayers = numOfPlayers;
        this.gameName = gameName;
    }

    public int getNumOfPlayers() {
        return numOfPlayers;
    }
    public String getGameName () { return gameName;}

    @Override
    public String toString(){
        return getNickname() + " wants to create a game with " + String.valueOf(getNumOfPlayers()) + " players";
    }
}
