package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.PersonalGoalCard;
import it.polimi.ingsw.model.PersonalGoalCardDeck;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.utils.NumberOfPlayers;
import it.polimi.ingsw.view.cli.TextualUI;

import java.util.*;

public class GameController implements Observer {
    public enum Event {
        CREATE_GAME,
        JOIN_GAME;
    }
    private final TextualUI view;
    private Game game;

    public GameController(TextualUI view){
        this.view = view;
    }

    public void addNewGame(int numOfPlayers) {
        Game game = new Game(Arrays.stream(NumberOfPlayers.values()).filter(x -> x.getValue() == numOfPlayers).toList().get(0));
    }






    @Override
    public void update(Observable o, Object arg) {
        if (o != this.view) {
            System.err.println("Discarding event from " + o);
        }

        /**
        Event event = (Event)arg;
        switch (event) {
            case CREATE_GAME -> {
                Game game = new Game(NumberOfPlayers.TWO_PLAYERS);
                currentGames.put(game.getId(), game);
                game.addObserver(this.view);
                game.subscribe(new Player("iFra&Carlo", game.getPersonalGoalCardDeck()));
            }
        }
         */

        if (arg instanceof Event event){
            if (event.equals(Event.CREATE_GAME)){
                this.game = new Game(NumberOfPlayers.TWO_PLAYERS);
                game.addObserver(this.view);
            }
        } else if (arg instanceof String name) {
            Player player = new Player(name, game.getPersonalGoalCardDeck());
            game.subscribe(player);
        }
    }
}
