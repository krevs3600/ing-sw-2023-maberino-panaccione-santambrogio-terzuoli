package it.polimi.ingsw.network.eventMessages;

public class GameSpecsMessage extends EventMessage {
    private final long serialVersionUID = 1L;
    private final int numOfPlayers;
    private final String gameName;
    public GameSpecsMessage(String nickName, String gameName, int numOfPlayers) {
        super(nickName, EventMessageType.GAME_SPECS);
        this.numOfPlayers = numOfPlayers;
        this.gameName = gameName;
    }

    public int getNumOfPlayers() {
        return numOfPlayers;
    }
    public String getGameName () { return gameName;}

    public String toString(){
        return getNickname() + " wants to create a game with " + String.valueOf(getNumOfPlayers()) + " players";
    }
}
