package it.polimi.ingsw.network.eventMessages;

public class GameNameMessage extends EventMessage {
    private final String GameName;
    private final long serialVersionUID = 1L;
    public GameNameMessage(String nickName,String GameName) {
        super(nickName, EventMessageType.GAME_NAME);
        this.GameName=GameName;


    }

    public String getGameName() {
        return GameName;
    }

    @Override
    public String toString(){
        return getNickname() + " asks to choose a specific Game Name";
    }
}
