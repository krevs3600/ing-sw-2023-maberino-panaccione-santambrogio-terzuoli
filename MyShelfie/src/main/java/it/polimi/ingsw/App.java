package it.polimi.ingsw;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.network.CLientImplementation;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.network.Server;
import it.polimi.ingsw.network.ServerImplementation;
import it.polimi.ingsw.view.cli.TextualUI;

public class App {
    public static void main(String[] args) {
        Server server = new ServerImplementation();
        CLientImplementation client = new CLientImplementation(server);
        client.run();
    }
}
