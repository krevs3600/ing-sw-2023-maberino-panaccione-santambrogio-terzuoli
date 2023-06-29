package it.polimi.ingsw.network.MessagesToClient;

import it.polimi.ingsw.network.Server;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.network.MessagesToClient.errorMessages.ErrorMessage;
import it.polimi.ingsw.network.MessagesToClient.requestMessage.RequestMessage;

import java.io.Serializable;


/**
 * <h1>Abstract Class MessageToClient</h1>
 * The abstract class MessageToClient main purpose is to let the {@link Server} communicate directly to the {@link Client}
 * via an asynchronous message. MessageToClient can is extended by two classes: {@link ErrorMessage} and {@link RequestMessage}
 *
 * @author Francesco Santambrogio
 * @version 1.0
 * @since 5/28/2023
 */
public abstract class MessageToClient implements Serializable {

    private final MessageToClientType type;
    private final String clientNickname;
    public MessageToClient (String nickname, MessageToClientType type) {
        this.clientNickname = nickname;
        this.type = type;
    }

    public String getNickname () {
        return clientNickname;
    }

    public MessageToClientType getType () {
        return type;
    }

}
