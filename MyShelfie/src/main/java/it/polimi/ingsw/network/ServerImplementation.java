package it.polimi.ingsw.network;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.ModelView.GameView;
import it.polimi.ingsw.network.EventMessage;
import it.polimi.ingsw.model.utils.NumberOfPlayers;
import it.polimi.ingsw.view.cli.TextualUI;

public class ServerImplementation implements Server {

    private Game game;
    private GameController gameController;

    //@TODO: 2 PLAYERS
    @Override
    public void register(Client client){
        this.game = new Game();
        this.game.addObserver((observer, eventMessage)-> client.update(new GameView(game), (EventMessage) eventMessage));
        this.gameController = new GameController(game, client);
    }

    @Override
    public void update(Client client, EventMessage eventMessage) {
        this.gameController.update(client, eventMessage);
    }


}
