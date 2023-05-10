package it.polimi.ingsw.network;

import it.polimi.ingsw.model.ModelView.GameView;

public interface Client {
    void update(GameView gameView, EventMessage eventMessage);
}
