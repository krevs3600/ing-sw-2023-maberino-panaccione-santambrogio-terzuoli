package it.polimi.ingsw.network;

import it.polimi.ingsw.model.ModelView.GameView;
import it.polimi.ingsw.view.cli.TextualUI;

public class CLientImplementation implements Client, Runnable {

    TextualUI view = new TextualUI();
    public CLientImplementation(Server server){
        server.register(this);
        view.addObserver((observable, eventMessage)-> server.update(this, (EventMessage) eventMessage));
    }

    @Override
    public void update(GameView gameView, EventMessage eventMessage) {
        view.update(gameView, eventMessage);
    }

    @Override
    public void run() {
        view.run();
    }
}
