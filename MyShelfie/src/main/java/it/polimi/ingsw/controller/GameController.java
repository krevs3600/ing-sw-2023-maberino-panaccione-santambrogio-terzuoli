package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.CommonGoalCard.CommonGoalCard;
import it.polimi.ingsw.model.utils.GamePhase;
import it.polimi.ingsw.model.utils.Position;
import it.polimi.ingsw.model.utils.TileType;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.network.MessagesToClient.errorMessages.IllegalTilePositionErrorMessage;
import it.polimi.ingsw.network.MessagesToClient.errorMessages.NotEnoughInsertableTilesErrorMessage;
import it.polimi.ingsw.network.MessagesToClient.errorMessages.NotEnoughInsertableTilesInColumnErrorMessage;
import it.polimi.ingsw.network.MessagesToClient.errorMessages.UpperBoundTilePackErrorMessage;
import it.polimi.ingsw.network.eventMessages.*;
import it.polimi.ingsw.persistence.Storage;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.*;

public class GameController implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final Game game;


    public static final String savedGameFile = "memoryCard";

    public GameController( Game game){
        this.game = game;
    }

    public Game getGame () {
        return game;
    }


    public void update(Client client, EventMessage eventMessage) throws IllegalArgumentException, IOException {

        switch (eventMessage.getType()) {

            // TODO: do we really need all these cases?
            case GAME_CHOICE, GAME_CREATION, GAME_SPECS -> {
                Player newPlayer = new Player(eventMessage.getNickname());
                game.subscribe(newPlayer);
            }

            case START_GAME -> {
                game.initLivingRoomBoard();
                for (Player player : game.getSubscribers()){
                    player.setPersonalGoalCard(game.getPersonalGoalCardDeck().draw());
                }
                game.setDrawableTiles();
                game.startGame();
            }

            case TILE_POSITION -> {
                TilePositionMessage tilePositionMessage = (TilePositionMessage) eventMessage;

                if (game.getDrawableTiles().contains(game.getLivingRoomBoard().getSpace(tilePositionMessage.getPosition()))) {
                    if (game.getBuffer().isEmpty()) {
                        ItemTile itemTile = game.getLivingRoomBoard().getSpace(tilePositionMessage.getPosition()).drawTile();
                        game.getBuffer().add(tilePositionMessage.getPosition());
                        game.insertTileInTilePack(itemTile);
                        game.setTurnPhase(GamePhase.PICKING_TILES);
                    } else if (game.getBuffer().size() == 1) {

                        if (game.getBuffer().get(0).isAdjacent(tilePositionMessage.getPosition())) {
                            if (game.getCurrentPlayer().getBookshelf().getNumberInsertableTiles() >= game.getBuffer().size() + 1) {
                                if (tilePositionMessage.getPosition().getColumn() == game.getBuffer().get(0).getColumn()) {
                                    game.setAlongSideColumn(true);
                                } else {
                                    game.setAlongSideRow(true);
                                }
                                ItemTile itemTile = game.getLivingRoomBoard().getSpace(tilePositionMessage.getPosition()).drawTile();
                                game.getBuffer().add(tilePositionMessage.getPosition());
                                game.insertTileInTilePack(itemTile);
                                game.setTurnPhase(GamePhase.PICKING_TILES);
                            } else {
                                client.onMessage(new NotEnoughInsertableTilesErrorMessage(eventMessage.getNickname(), "The number of insertable tiles in your bookshelf is too small, you cannot insert all of the tiles then"));
                            }
                        } else {
                            client.onMessage(new IllegalTilePositionErrorMessage(eventMessage.getNickname(), "Invalid position, please choose another one"));
                        }
                    } else if (game.getBuffer().size() == 2) {
                        boolean fairPosition = false;
                        for (Position pos : game.getBuffer()) {
                            if (pos.isAdjacent(tilePositionMessage.getPosition())) {
                                fairPosition = true;
                                break;
                            }
                        }
                        if (fairPosition) {

                            if (game.isAlongSideColumn()) {
                                if (tilePositionMessage.getPosition().getColumn() == game.getBuffer().get(0).getColumn()) {
                                        if (game.getCurrentPlayer().getBookshelf().getNumberInsertableTiles() >= game.getBuffer().size() + 1) {
                                            ItemTile itemTile = game.getLivingRoomBoard().getSpace(tilePositionMessage.getPosition()).drawTile();
                                            game.getBuffer().add(tilePositionMessage.getPosition());
                                            game.insertTileInTilePack(itemTile);
                                            game.setTurnPhase(GamePhase.PICKING_TILES);
                                        } else {
                                            client.onMessage(new NotEnoughInsertableTilesErrorMessage(eventMessage.getNickname(), "The number of insertable tiles in your bookshelf is too small, you cannot insert all of the tiles then"));
                                        }
                                }
                                else {
                                    client.onMessage(new IllegalTilePositionErrorMessage(eventMessage.getNickname(), "Invalid position, please choose another one"));
                                }
                            } else if (game.isAlongSideRow()) {
                                if (tilePositionMessage.getPosition().getRow() == game.getBuffer().get(0).getRow()) {
                                    if (game.getCurrentPlayer().getBookshelf().getNumberInsertableTiles() >= game.getBuffer().size() + 1) {
                                        ItemTile itemTile = game.getLivingRoomBoard().getSpace(tilePositionMessage.getPosition()).drawTile();
                                        game.getBuffer().add(tilePositionMessage.getPosition());
                                        game.insertTileInTilePack(itemTile);
                                        game.setTurnPhase(GamePhase.PICKING_TILES);
                                    } else {
                                        client.onMessage(new NotEnoughInsertableTilesErrorMessage(eventMessage.getNickname(), "The number of insertable tiles in your bookshelf is too small, you cannot insert all of the tiles then"));
                                    }
                                }
                                else {
                                    client.onMessage(new IllegalTilePositionErrorMessage(eventMessage.getNickname(), "Invalid position, please choose another one"));
                                }
                            }

                        } else {
                            client.onMessage(new IllegalTilePositionErrorMessage(eventMessage.getNickname(), "Invalid position, please choose another one"));
                            //throw new IllegalAccessError("Space forbidden or empty")
                        }
                    } else {
                        client.onMessage(new UpperBoundTilePackErrorMessage(eventMessage.getNickname(), "You can take three tiles at most!"));
                        //throw new IllegalAccessError("Space forbidden or empty");
                    }
                }
                else {
                    client.onMessage(new IllegalTilePositionErrorMessage(eventMessage.getNickname(), "Invalid position, please choose another one"));
                    //throw new IllegalAccessError("Space forbidden or empty");
                }
            }

            case BOOKSHELF_COLUMN -> {
                BookshelfColumnMessage bookshelfColumnMessage = (BookshelfColumnMessage) eventMessage;
                if (game.getCurrentPlayer().getBookshelf().getNumberInsertableTilesColumn(bookshelfColumnMessage.getColumn()) < game.getTilePack().getSize()) {
                    client.onMessage(new NotEnoughInsertableTilesInColumnErrorMessage(eventMessage.getNickname(), "There are not enough spaces in this column, please choose another one"));
                }
                try {
                    game.setColumnChoice(bookshelfColumnMessage.getColumn());
                    game.setTurnPhase(GamePhase.PLACING_TILES);
                } catch (IndexOutOfBoundsException e) {}
            }

            case ITEM_TILE_INDEX -> {
                ItemTileIndexMessage itemTileIndexMessage = (ItemTileIndexMessage) eventMessage;
                game.getCurrentPlayer().insertTile(game.getTilePack(), game.getColumnChoice(), itemTileIndexMessage.getIndex());
                try {
                    game.setColumnChoice(game.getColumnChoice());
                    game.setTurnPhase(GamePhase.PLACING_TILES);
                } catch (IndexOutOfBoundsException e) {}
                // TODO gestire le eccezioni!!
            }

            case SWITCH_PHASE -> {
                SwitchPhaseMessage switchPhaseMessage = (SwitchPhaseMessage) eventMessage;
                game.setTurnPhase(switchPhaseMessage.getGamePhase());
            }

            case END_TURN -> {
                Storage storage = new Storage();
                storage.store(this);

                computeScoreMidGame();

                if(game.isFinalTurn()) {
                    // game is ended
                    if (game.getCurrentPlayer().equals(game.getLastPlayer())){
                        Player winner = getWinner();
                        game.endGame(winner);
                    }
                }

                if (game.getCurrentPlayer().getBookshelf().isFull()) {
                    if (!game.isFirstPlayerHasEnded()) {
                        game.setFirstPlayerHasEnded();
                        game.setFinalTurn();
                        game.getCurrentPlayer().setScore(1);
                        if (game.getCurrentPlayer().equals(game.getLastPlayer())){
                            //TODO: show always the score to the clients, updating it when needed
                            Player winner = getWinner();
                            //TODO: create a new message specific to the case when the last player in the rotation fills the bookshelf
                            game.endGame(winner);
                        }
                    }
                }

                if (!game.isEnded()){
                    if(game.getLivingRoomBoard().getAllFreeTiles().size()==game.getLivingRoomBoard().getDrawableTiles().size()) {
                        game.getLivingRoomBoard().refill();
                    }
                    game.setTurnPhase(GamePhase.INIT_TURN);
                }
            }
            case FILL_BOOKSHELF -> {
                game.getCurrentPlayer().getBookshelf().insertTileTest();
                game.insertTileInTilePack(new ItemTile(TileType.CAT));
                game.setTurnPhase(GamePhase.PICKING_TILES);
            }
        }
    }

    /**
     * This method is used to compute the final score of the player at the end of the game
     * @return int It returns the final score of the player
     */
    public Player getWinner() {
        Player winner = null;
        int max_score = 0;
        for (Player player : game.getSubscribers()){
            int score = computePlayerScoreEndGame(player);
            if(score > max_score) {
                max_score = score;
                winner = player;
            }
            player.setScore(score);
        }
        return winner;

    }

    public int computePlayerScoreEndGame(Player player){
        //Computation of points from personal goal card
        ItemTile[][] bookshelf = player.getBookshelf().getGrid();
        int score;
        //personalGoalCard.getScoringItem().forEach((key, value) -> );
        int count = 0;
        for (Map.Entry<Integer, TileType> element :
                player.getPersonalGoalCard().getScoringItem().entrySet()) {
            try{
                if (bookshelf[(element.getKey())/5][(element.getKey())%5].getType().equals(element.getValue())) {
                    count++;
                }
            } catch (NullPointerException ignored){
            }

        }

        ArrayList<Integer> points;
        try {
            Reader file = new FileReader("src/main/java/it/polimi/ingsw/model/configs/PersonalGoalCards.json");
            JSONParser parser = new JSONParser();
            Object jsonObj = parser.parse(file);
            JSONObject jsonObject = (JSONObject) jsonObj;
            // read points from json
            points = new ArrayList<>(((ArrayList<Long>)jsonObject.get("points")).stream().map(Long::intValue).toList());
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }

        score=points.get(count);

        //Computation of points from adjacent tiles groups at end of game
        ArrayList<List<Integer>> pointsAdj = new ArrayList<>();
        pointsAdj.add(Arrays.asList(3, 4, 5, 6));
        pointsAdj.add(Arrays.asList(2, 3, 5, 8));

        for (TileType type : TileType.values()) {
            Map<Integer, Integer> adjacentTiles = player.getBookshelf().getNumberAdjacentTiles(type);
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

        return score;
    }


    /**
     * This method is used to keep track of score changes of a player during the game.
     * In particular, the score is updated every time the player receives a scoring token
     */
    private void computeScoreMidGame() {
        List<CommonGoalCard> commonGoalCards = game.getLivingRoomBoard().getCommonGoalCards();
        for(CommonGoalCard card : commonGoalCards) {
           Player currentPlayer = game.getCurrentPlayer();
            if (card.toBeChecked(currentPlayer.getBookshelf())) {
                if(card.checkPattern(currentPlayer.getBookshelf())){
                    //ScoringToken tempToken = card.getStack().pop();
                    //score+=tempToken.getValue();
                    if(card.equals(commonGoalCards.get(0)) && !currentPlayer.isFirstCommonGoalAchieved()) {
                        //Stack<ScoringToken> oldStack = commonGoalCards.get(0).getStack();
                        int value = game.popCommonGoalCardStack(0);
                        currentPlayer.setScore(value);
                        currentPlayer.hasAchievedFirstGoal();
                        // client.onMessage(new FirstCommonGoalMessage(currentPlayer.getName()));
                    }
                    if(card.equals(commonGoalCards.get(1)) && !currentPlayer.isSecondCommonGoalAchieved()) {
                        currentPlayer.setScore(card.getStack().pop().getValue());
                        int value = game.popCommonGoalCardStack(1);
                        currentPlayer.setScore(value);
                        currentPlayer.hasAchievedFirstGoal();
                        currentPlayer.hasAchievedSecondGoal();
                        // client.onMessage(new SecondCommonGoalMessage((currentPlayer.getName())));
                    }
                }
            }
        }
    }
}


