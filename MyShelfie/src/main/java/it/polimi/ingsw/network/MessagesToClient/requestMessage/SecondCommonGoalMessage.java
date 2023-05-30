package it.polimi.ingsw.network.MessagesToClient.requestMessage;

import it.polimi.ingsw.network.MessagesToClient.MessageToClient;
import it.polimi.ingsw.network.MessagesToClient.MessageToClientType;
import it.polimi.ingsw.network.eventMessages.EventMessage;
import it.polimi.ingsw.network.eventMessages.EventMessageType;

public class SecondCommonGoalMessage extends MessageToClient {
    private final long serialVersionUID = 1L;

    public SecondCommonGoalMessage(String nickName) {
        super(nickName, MessageToClientType.SECONDCOMMONGOAL);

    }

    @Override
    public String toString(){
        return getNickname() + " has reached the second CommonGoal" ;
    }
}
