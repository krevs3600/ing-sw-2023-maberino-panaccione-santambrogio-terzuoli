package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.CommonGoalCard.CommonGoalCard;
import it.polimi.ingsw.model.ModelView.GameView;
import it.polimi.ingsw.model.utils.NumberOfPlayers;
import it.polimi.ingsw.model.utils.Position;
import it.polimi.ingsw.model.utils.TileType;
import it.polimi.ingsw.view.cli.TextualUI;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
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
            if (game.getTurnPhase().equals(Game.Phase.INIT_TURN)) {
                game.setDrawableTiles();
                if (((Integer) arg).intValue() <= 0 | ((Integer) arg).intValue() > 3) {
                    throw new IllegalArgumentException();
                }
            }
            else if (game.getTurnPhase().equals(Game.Phase.PICKING_TILES)) {
                game.setTurnPhase(Game.Phase.PLACING_TILES);
                game.getSubscribers().get(0).insertTile(game.getTilePack(), (int) arg);
                game.setColumnChoice((int) arg);
                if (!game.getSubscribers().get(0).getBookshelf().isFull()) {
                    game.setTurnPhase(Game.Phase.INIT_TURN);
                    computeScoreMidGame();
                }
                else {
                    game.setTurnPhase(Game.Phase.END_GAME);
                    computeScoreEndGame();
                }
            }
        }

        if (arg instanceof Position position) {
            Game.Phase turnPhase = game.getTurnPhase();

            switch (turnPhase) {

                case INIT_TURN -> {
                    game.setTurnPhase(Game.Phase.PICKING_TILES);
                    if (game.getDrawableTiles().contains(game.getLivingRoomBoard().getSpace(position))) {
                        ItemTile itemTile = game.drawTile(position);
                        game.insertTileInTilePack(itemTile);
                    } else {
                        throw new IllegalAccessError("Space forbidden or empty");
                    }
                }

                case PICKING_TILES -> {
                    // se arriva una cosa diversa dalla posizione bisogna processarla come messa nella libreria

                    if (game.getDrawableTiles().contains(game.getLivingRoomBoard().getSpace(position))) {
                        ItemTile itemTile = game.drawTile(position);
                        game.insertTileInTilePack(itemTile);
                    } else {
                        throw new IllegalAccessError("Space forbidden or empty");
                    }
                }
            }
        }
    }
    /**
     * This method is used to keep track of score changes of a player during the game.
     * In particular, the score is updated every time the player receives a scoring token
     * @return int It returns the updated score of the player
     */
    //TODO: change getSubscribers().get(0) into getCurrentPlayer()
    private void computeScoreMidGame(){
        List<CommonGoalCard> commonGoalCards = game.getLivingRoomBoard().getCommonGoalCards();
        for(CommonGoalCard card : commonGoalCards) {
            if (card.toBeChecked(game.getSubscribers().get(0).getBookshelf())) {
                if(card.CheckPattern(game.getSubscribers().get(0).getBookshelf())){
                    //ScoringToken tempToken = card.getStack().pop();
                    //score+=tempToken.getValue();
                    if(card.equals(commonGoalCards.get(0)) && !game.getSubscribers().get(0).isFirstCommonGoalAchieved()) {
                        game.setCurrentPlayerScore(card.getStack().pop().getValue());
                        game.getSubscribers().get(0).hasAchievedFirstGoal();
                    }
                    if(card.equals(commonGoalCards.get(1)) && !game.getSubscribers().get(0).isSecondCommonGoalAchieved()) {
                        game.setCurrentPlayerScore(card.getStack().pop().getValue());
                        game.getSubscribers().get(1).hasAchievedSecondGoal();
                    }
                }
            }
        }
    }

    /**
     * This method is used to compute the final score of the player at the end of the game
     * @return int It returns the final score of the player
     */
    public void computeScoreEndGame() {
        //Computation of points from personal goal card
        ItemTile[][] bookshelf = this.game.getSubscribers().get(0).getBookshelf().getGrid();
        int score;
        //personalGoalCard.getScoringItem().forEach((key, value) -> );
        int count = 0;
        for (Map.Entry<Integer, TileType> element :
                game.getSubscribers().get(0).getPersonalGoalCard().getScoringItem().entrySet()) {
            if (bookshelf[(element.getKey())/5][(element.getKey())%5].getType().equals(element.getValue())) {
                count++;
            }
        }

        ArrayList<Integer> points;
        try {
            Reader file = new FileReader("src/main/java/it/polimi/ingsw/model/PersonalGoalCards.json");
            JSONParser parser = new JSONParser();
            Object jsonObj = parser.parse(file);
            JSONObject jsonObject = (JSONObject) jsonObj;
            // read points from json
            points = (ArrayList<Integer>) jsonObject.get("points");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        score=points.get(count);

        //Computation of points from adjacent tiles groups at end of game
        ArrayList<List<Integer>> pointsAdj = new ArrayList<>();
        pointsAdj.add(Arrays.asList(3, 4, 5, 6));
        pointsAdj.add(Arrays.asList(2, 3, 5, 8));

        for (TileType type : TileType.values()) {
            Map<Integer, Integer> adjacentTiles = this.game.getSubscribers().get(0).getBookshelf().getNumberAdjacentTiles(type);
            for (Integer key : adjacentTiles.keySet()) {
                for (int i = 0; i < pointsAdj.get(0).size(); i++) {
                    if (key.equals(pointsAdj.get(0).get(i))) {
                        score = score + (pointsAdj.get(1).get(i)) * adjacentTiles.get(key);
                    } else if (key > pointsAdj.get(0).get(3)) {
                        score = score + (pointsAdj.get(1).get(3)) * adjacentTiles.get(key);
                    }
                }
            }

        }
        game.setCurrentPlayerScore(score);
    }

}
