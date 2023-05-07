package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.ModelView.GameView;
import it.polimi.ingsw.model.PersonalGoalCard;
import it.polimi.ingsw.model.PersonalGoalCardDeck;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.utils.NumberOfPlayers;
import it.polimi.ingsw.model.utils.Position;
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
    public void update(Observable o, Object arg) throws IllegalArgumentException{
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

        if (arg instanceof Event event) {
            if (event.equals(Event.CREATE_GAME)) {
                this.game = new Game(NumberOfPlayers.TWO_PLAYERS);
                GameView gameView = new GameView(this.game);
                gameView.addObserver(this.view);
                this.game.addObserver(gameView);
            }
        } else if (arg instanceof String name) {
            Player player = new Player(name, game.getPersonalGoalCardDeck());
            game.subscribe(player);
            game.setTurnPhase(Game.Phase.INIT_TURN);
        }

        if (arg instanceof Integer) {
            game.setDrawableTiles();
            if (((Integer) arg).intValue() <= 0 | ((Integer) arg).intValue() > 3) {
                throw new IllegalArgumentException();
            }
        }

        if (arg instanceof Position position) {
            Game.Phase turnPhase = game.getTurnPhase();

            switch (turnPhase) {

                case INIT_TURN -> {
                    game.setTurnPhase(Game.Phase.PICKING_TILES);
                    if (game.getDrawableTiles().contains(game.getLivingRoomBoard().getSpace(position))) {
                        game.drawTile(position);
                    } else {
                        throw new IllegalAccessError("Space forbidden or empty");
                    }
                }

                case PICKING_TILES -> {
                    // se arriva una cosa diversa dalla posizione bisogna processarla come messa nella libreria

                    if (game.getDrawableTiles().contains(game.getLivingRoomBoard().getSpace(position))) {
                        game.drawTile(position);
                    } else {
                        throw new IllegalAccessError("Space forbidden or empty");
                    }

                }
            }
        }
    }
}
