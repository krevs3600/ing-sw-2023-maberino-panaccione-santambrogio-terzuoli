package it.polimi.ingsw.network.MessagesToClient.requestMessage;

import it.polimi.ingsw.network.MessagesToClient.MessageToClient;
import it.polimi.ingsw.network.MessagesToClient.MessageToClientType;

public class FirstCommonGoalMessage extends MessageToClient {
    private final long serialVersionUID = 1L;

    public FirstCommonGoalMessage(String nickName) {
        super(nickName, MessageToClientType.FIRSTCOMMONGOAL);

    }

    @Override
    public String toString(){
        return getNickname() + " has reached the first CommonGoal" ;
    }
}

