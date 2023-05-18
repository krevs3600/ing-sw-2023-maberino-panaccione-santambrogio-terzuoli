package it.polimi.ingsw.network;

import it.polimi.ingsw.model.ModelView.GameView;
import it.polimi.ingsw.network.requestMessage.RequestMessage;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Client extends Remote {
    void update(GameView gameView, EventMessage eventMessage) throws RemoteException;
    void onMessage (RequestMessage message) throws RemoteException;
}
