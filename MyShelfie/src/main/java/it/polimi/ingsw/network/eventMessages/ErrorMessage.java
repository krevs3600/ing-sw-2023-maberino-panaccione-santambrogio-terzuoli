package it.polimi.ingsw.network.eventMessages;

import it.polimi.ingsw.network.EventMessage;

public class ErrorMessage extends EventMessage {

    private final String errorMessage;

    public ErrorMessage(String nickName, String errorMessage) {
        super(nickName, MessageType.ERROR);
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public String toString(){
        return getNickName() + " got error: " + errorMessage;
    }

}
