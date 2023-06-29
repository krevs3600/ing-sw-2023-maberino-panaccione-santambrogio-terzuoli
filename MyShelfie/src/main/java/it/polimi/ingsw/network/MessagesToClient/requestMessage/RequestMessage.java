package it.polimi.ingsw.network.MessagesToClient.requestMessage;

import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.network.MessagesToClient.MessageToClient;
import it.polimi.ingsw.network.MessagesToClient.MessageToClientType;
import it.polimi.ingsw.network.Server;

/**
 * <h1>Abstract Class RequestMessage</h1>
 * The abstract class RequestMessage extends the abstract class MessageToClient. The main purpose of the class is
 *  is for the {@link Server} to send asynchronous messages to the {@link Client}, to let him/her know that the input he/she
 *  provided was correct
 *
 * @author Francesco Santambrogio
 * @version 1.0
 * @since 5/18/2023
 */
public abstract class RequestMessage extends MessageToClient {

    private final long serialVersionUID = 1L;

    /**
     * Class constructor
     * @param nickname of the {@link Client} whom to send the message to
     * @param messageType type of the {@link MessageToClient}
     */
    public RequestMessage(String nickname, MessageToClientType messageType){

        super (nickname, messageType);
    }

    /**
     * To String method
     * @return a {@code String} containing the content of the type of the {@link MessageToClient}
     */
    @Override
    public String toString(){
        return "Server is sending the following message: " + getType().name();
    }

}