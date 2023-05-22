package it.polimi.ingsw.client.view.FXML;

import it.polimi.ingsw.network.ClientImplementation;
import it.polimi.ingsw.network.Server;

import java.rmi.RemoteException;

public class GuiManager extends ClientImplementation {
    ServerSettingsController serverSettingsController;
    NicknameController nicknameController;


    public GuiManager(GUI view, Server server) throws RemoteException {
        super(view, server);
    }



}
