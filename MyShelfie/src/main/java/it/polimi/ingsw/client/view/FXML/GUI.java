package it.polimi.ingsw.client.view.FXML;


import it.polimi.ingsw.network.MessagesToClient.MessageToClient;
import it.polimi.ingsw.observer_observable.Observable;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Set;

public class GUI extends Observable implements View {


    @Override
    public String askConnectionType() {
        return null;
    }

    @Override
    public void createConnection() throws RemoteException, NotBoundException {

    }

    @Override
    public void askNickname() {

    }

    @Override
    public String askServerAddress() {
        return null;
    }

    @Override
    public int askServerPort() {
        return 0;
    }

    @Override
    public void askGameName() {

    }

    @Override
    public void showGameNamesList(Set<String> availableGameNames) {

    }

    @Override
    public void showMessage(MessageToClient message) {

    }

    @Override
    public void askNumberOfPlayers(String gameName) {

    }

    @Override
    public boolean isValidPort(String serverPort) {
        return false;
    }

    @Override
    public boolean isValidIPAddress(String address) {
        return false;
    }

    @Override
    public void gameMenu() {

    }
}
