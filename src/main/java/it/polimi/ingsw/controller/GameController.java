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
import java.rmi.RemoteException;
import java.util.*;

/**
 * <h1>Class GameController</h1>
 * The class GameController contains all the logic and applies the rules of the game. Every attempt to change
 * the state of the game made by the players must pass through this class since it is the only one that has
 * the right to modify the game model
 *
 * @author Carlo Terzuoli, Francesco Maberino, Francesca Pia Panaccione, Francesco Santambrogio
 * @version 1.0
 * @since 06/05/2023
 */

public class GameController implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final Game game;

    public static final String savedGameFile = "memoryCard";

    /**
     * Class constructor
     * @param game the {@link Game} that is going to be played and controlled by this class
     */
    public GameController(Game game){
        this.game = game;
    }


    /**
     * The purpose of this method is to manage every {@link EventMessage} it receives
     * in the correct manner and modify the model accordingly
     * @param client who sends the message containing an action he/she wants to perform
     * @param eventMessage containing the desired action by the client
     */
    public void update(Client client, EventMessage eventMessage) throws IllegalArgumentException, IOException {

        switch (eventMessage.getType()) {

            // In response to these messages, the controller creates a new player and adds it to the game
            case GAME_CHOICE, GAME_SPECS -> {
                Player newPlayer = new Player(eventMessage.getNickname());
                getGame().subscribe(newPlayer);
            }

            // once all the players have entered the game, the living room board can be initialized, the personal goal cards are distributed
            // and the game can begin
            case START_GAME -> {
                getGame().initLivingRoomBoard();
                for (Player player : game.getSubscribers()){
                    player.setPersonalGoalCard(game.getPersonalGoalCardDeck().draw());
                }
                getGame().setDrawableTiles();
                getGame().startGame();
            }

            // when triggered, the controller switches game phase to the subsequent one
            case SWITCH_PHASE -> {
                SwitchPhaseMessage switchPhaseMessage = (SwitchPhaseMessage) eventMessage;
                getGame().setTurnPhase(switchPhaseMessage.getGamePhase());
            }

            // every time a player moves to pick a tile, this case is triggered. The controller checks whether the position the
            // player has selected is legal. If so, it proceeds and adds the item tile located in such spot to the tile pack,
            // otherwise an error message is sent and the player is requested to select a different position
            case TILE_POSITION -> {
                TilePositionMessage tilePositionMessage = (TilePositionMessage) eventMessage;

                // the position must be among the drawable ones
                if (getGame().getDrawableTiles().contains(getGame().getLivingRoomBoard().getSpace(tilePositionMessage.getPosition()))) {

                    switch (getGame().getBuffer().size()) {

                        // first case: the buffer of positions is empty, it is the first tile picked, no further checks to do
                        case 0 -> drawTileAndInsertInTilePack(tilePositionMessage);

                        // second case: the buffer of positions has already a tile, so it is the second tile picked
                        // it must be adjacent to the already taken tile, saving the information about whether it is adjacent
                        // along the row or along the column
                        case 1 -> {
                            if (getGame().getBuffer().get(0).isAdjacent(tilePositionMessage.getPosition())) {
                                if (getGame().getCurrentPlayer().getBookshelf().getNumberInsertableTiles() >= getGame().getBuffer().size() + 1) {
                                    setAlongsideWhat(tilePositionMessage);
                                    drawTileAndInsertInTilePack(tilePositionMessage);
                                } else {
                                    try {client.onMessage(new NotEnoughInsertableTilesErrorMessage(eventMessage.getNickname(), "THE NUMBER OF INSERTABLE TILES IN YOUR BOOKSHELF IS TOO SMALL,\n YOU CANNOT INSERT ALL OF THE TILES THEN"));} catch (RemoteException e) {System.err.println(e.getMessage());}
                                }
                            } else {
                                try {
                                    client.onMessage(new IllegalTilePositionErrorMessage(eventMessage.getNickname(), "INVALID POSITION, PLEASE CHOOSE ANOTHER ONE"));} catch (RemoteException e) {System.err.println(e.getMessage());}
                            }
                        }

                        // third case: the buffer of positions has already two tiles, so it is the third tile picked
                        // it must be adjacent to the already taken tiles and along the row if the already taken ones are adjacent
                        // along the row or along the column otherwise
                        case 2 -> {
                            boolean fairPosition = false;
                            for (Position pos : getGame().getBuffer()) {
                                if (pos.isAdjacent(tilePositionMessage.getPosition())) {
                                    fairPosition = true;
                                    break;
                                }
                            }
                            if (fairPosition) {

                                if (getGame().isAlongSideColumn()) {
                                    if (tilePositionMessage.getPosition().getColumn() == getGame().getBuffer().get(0).getColumn()) {
                                        if (getGame().getCurrentPlayer().getBookshelf().getNumberInsertableTiles() >= getGame().getBuffer().size() + 1) {
                                            drawTileAndInsertInTilePack(tilePositionMessage);
                                        } else {
                                            try {
                                                client.onMessage(new NotEnoughInsertableTilesErrorMessage(eventMessage.getNickname(), "THE NUMBER OF INSERTABLE TILES IN YOUR BOOKSHELF IS TOO SMALL\n, YOU CANNOT INSERT ALL OF THE TILES THEN"));} catch (RemoteException e) {System.err.println(e.getMessage());}
                                        }
                                    } else {
                                        try {client.onMessage(new IllegalTilePositionErrorMessage(eventMessage.getNickname(), "INVALID POSITION\n, PLEASE CHOOSE ANOTHER ONE"));} catch (RemoteException e) {System.err.println(e.getMessage());}
                                    }
                                } else if (getGame().isAlongSideRow()) {
                                    if (tilePositionMessage.getPosition().getRow() == getGame().getBuffer().get(0).getRow()) {
                                        if (getGame().getCurrentPlayer().getBookshelf().getNumberInsertableTiles() >= getGame().getBuffer().size() + 1) {
                                            drawTileAndInsertInTilePack(tilePositionMessage);
                                        } else {
                                            try {client.onMessage(new NotEnoughInsertableTilesErrorMessage(eventMessage.getNickname(), "THE NUMBER OF INSERTABLE TILES IN YOUR BOOKSHELF IS TOO SMALL,\n YOU CANNOT INSERT ALL OF THE TILES THEN"));} catch (RemoteException e) {System.err.println(e.getMessage());}
                                        }
                                    } else {
                                        try {client.onMessage(new IllegalTilePositionErrorMessage(eventMessage.getNickname(), "INVALID POSITION\n PLEASE CHOOSE ANOTHER ONE"));} catch (RemoteException e) {System.err.println(e.getMessage());}
                                    }
                                }

                            }
                            else {
                                try {client.onMessage(new IllegalTilePositionErrorMessage(eventMessage.getNickname(), "INVALID POSITION,\n PLEASE CHOOSE ANOTHER ONE"));} catch (RemoteException e) {System.err.println(e.getMessage());}
                            }
                        }

                        // if it is none of the cases above, an error message is sent
                        default -> {try {client.onMessage(new UpperBoundTilePackErrorMessage(eventMessage.getNickname(), "YOU CAN TAKE THREE TILES AT MOST!"));} catch (RemoteException e) {System.err.println(e.getMessage());}}}
                } else {try {client.onMessage(new IllegalTilePositionErrorMessage(eventMessage.getNickname(), "INVALID POSITION,\nPLEASE CHOOSE ANOTHER ONE"));} catch (RemoteException e) {System.err.println(e.getMessage());}}
            }

            // this case is used to select the column where to insert the item tiles from the tile pack
            case BOOKSHELF_COLUMN -> {
                BookshelfColumnMessage bookshelfColumnMessage = (BookshelfColumnMessage) eventMessage;
                if (getGame().getCurrentPlayer().getBookshelf().getNumberInsertableTilesColumn(bookshelfColumnMessage.getColumn()) < getGame().getTilePack().getSize()) {
                    try {client.onMessage(new NotEnoughInsertableTilesInColumnErrorMessage(eventMessage.getNickname(), "THERE ARE NOT ENOUGH SPACES IN THIS COLUMN, \nPLEASE CHOOSE ANOTHER ONE"));} catch (RemoteException e) {System.err.println(e.getMessage());}}
                try {
                    getGame().setColumnChoice(bookshelfColumnMessage.getColumn());
                    getGame().setTurnPhase(GamePhase.PLACING_TILES);
                } catch (IndexOutOfBoundsException e) {System.err.println(e.getMessage());}
            }

            // the player can select which tile to insert first into the bookshelf from the tile pack. The tile will be inserted
            // into the column the player has selected with the previous message
            case ITEM_TILE_INDEX -> {
                ItemTileIndexMessage itemTileIndexMessage = (ItemTileIndexMessage) eventMessage;
                getGame().getCurrentPlayer().insertTile(getGame().getTilePack(), getGame().getColumnChoice(), itemTileIndexMessage.getIndex());
                getGame().setColumnChoice(getGame().getColumnChoice());
                getGame().setTurnPhase(GamePhase.PLACING_TILES);
            }


            // every time a turn ends, after saving the game controller, and therefore the game data, in a file,
            // and after computing the score related to the common goals, several checks are performed.
            // First, if the final turn has ended, it takes the winner and the game ends then,
            // If it is not the final turn, the bookshelf of the player is full and nobody has filled his bookshelf before, the game enters the last turn
            // If the game is not ended and there are only free tiles on the board, the refill method is called.
            // otherwise the game proceeds with the next turn
            case END_TURN -> {
                Storage storage = new Storage();
                storage.store(this);

                computeScoreMidGame();

                if(getGame().isFinalTurn()) {
                    // game is ended immediately
                    if (getGame().getCurrentPlayer().equals(getGame().getLastPlayer())){
                        // getWinner updates the players' scores
                        Player winner = getWinner();
                        getGame().endGame(winner);
                    }
                }

                if (getGame().getCurrentPlayer().getBookshelf().isFull()) {
                    if (!getGame().isFirstPlayerHasEnded()) {
                        getGame().setFirstPlayerHasEnded();
                        getGame().setFinalTurn();
                        Player currentPlayer = getGame().getCurrentPlayer();
                        currentPlayer.setScore(1);
                        if (currentPlayer.equals(getGame().getLastPlayer())){
                            Player winner = getWinner();
                            getGame().endGame(winner);
                        }
                    }
                }

                if (!getGame().isEnded()){
                    if(getGame().getLivingRoomBoard().getAllFreeTiles().size()==getGame().getLivingRoomBoard().getDrawableTiles().size()) {
                        getGame().getLivingRoomBoard().refill();
                    }
                    getGame().setTurnPhase(GamePhase.INIT_TURN);
                }
            }

            case EASTER_EGG -> {
                getGame().getCurrentPlayer().getBookshelf().insertTilesRandomly();
                getGame().insertTileInTilePack(new ItemTile(TileType.CAT));
                getGame().setTurnPhase(GamePhase.PICKING_TILES);
            }
        }
    }

    /**
     * This method is used to compute the final score of the {@link Player}s at the end of the game
     * @return {@link Player} who has won the game, namely the one with the higher score
     */
    public Player getWinner() {
        Player winner = null;
        for (Player player : getGame().getSubscribers()){
            player.setScore(computePlayerScoreEndGame(player));
        }
        int max_score = 0;
        for (Player player : getGame().getSubscribers()){
            int score = player.getScore();
            if(score > max_score) {
                max_score = score;
                winner = player;
            }
        }
        return winner;
    }

    /**
     * This method is used to compute the score of a {@link Player} once the game is finished. Specifically, the points
     * regarding the adjacent groups of tiles as well as the points regarding the {@link PersonalGoalCard} of the {@link Player}
     * are calculated
     * @return the {@code int} representing the final score of the {@link Player}
     */
    private int computePlayerScoreEndGame(Player player){
        int adjacentTilesScore = 0;
        //Computation of points from personal goal card
        ItemTile[][] bookshelf = player.getBookshelf().getGrid();
        int personalCardScore;
        int score = 0;
        int count = 0;
        for (Map.Entry<Integer, TileType> element : player.getPersonalGoalCard().getScoringItemTiles().entrySet()) {
            try {
                if (bookshelf[(element.getKey()) / 5][(element.getKey()) % 5].getType().equals(element.getValue())) {
                    count++;
                }
            }catch (NullPointerException e) {
                System.err.println(e.getMessage());
            }
        }
        // loading points rule
        ArrayList<Integer> points;
        try {
            String path = "/configs/PersonalGoalCards.json";
            Reader file = new InputStreamReader(getClass().getResourceAsStream(path));
            JSONParser parser = new JSONParser();
            Object jsonObj = parser.parse(file);
            JSONObject jsonObject = (JSONObject) jsonObj;
            // read points from json
            points = new ArrayList<>(((ArrayList<Long>)jsonObject.get("points")).stream().map(Long::intValue).toList());
        } catch (IOException | ParseException e) {throw new RuntimeException(e);}
        personalCardScore = points.get(count);
        player.setPersonalGoalCardScore(personalCardScore);

        //Computation of points from adjacent tiles groups at end of game
        ArrayList<List<Integer>> pointsAdj = new ArrayList<>();
        pointsAdj.add(Arrays.asList(3, 4, 5, 6));
        pointsAdj.add(Arrays.asList(2, 3, 5, 8));

        for (TileType type : TileType.values()) {
            Map<Integer, Integer> adjacentTiles = player.getBookshelf().getNumberAdjacentTiles(type);
            for (Integer key : adjacentTiles.keySet()) {
                for (int i = 0; i < pointsAdj.get(0).size(); i++) {
                    if (key.equals(pointsAdj.get(0).get(i))) {
                        score += (pointsAdj.get(1).get(i)) * adjacentTiles.get(key);
                        adjacentTilesScore += (pointsAdj.get(1).get(i)) * adjacentTiles.get(key);
                    } else if (key > pointsAdj.get(0).get(3)) {
                        score += (pointsAdj.get(1).get(3)) * adjacentTiles.get(key);
                        adjacentTilesScore += (pointsAdj.get(1).get(3)) * adjacentTiles.get(key);
                        break;
                    }
                }
            }
        }
        player.setAdjacentTilesScore(score);
        score += personalCardScore;
        return score;
    }


    /**
     * This method is used to keep track of score changes of a {@link Player} during the game.
     * In particular, the score is updated every time the {@link Player} receives a {@link ScoringToken}
     * when he completes the goal of a {@link CommonGoalCard}
     */
    private void computeScoreMidGame() {
        List<CommonGoalCard> commonGoalCards = getGame().getLivingRoomBoard().getCommonGoalCards();
        for (CommonGoalCard card : commonGoalCards) {
            Player currentPlayer = getGame().getCurrentPlayer();
            if (card.checkPattern(currentPlayer.getBookshelf())) {
                if (card.equals(commonGoalCards.get(0)) && !currentPlayer.isFirstCommonGoalAchieved()) {
                    currentPlayer.winToken(card.getStack().get(card.getStack().size() - 1));
                    int value = getGame().popCommonGoalCardStack(0);
                    currentPlayer.setScore(value);
                    currentPlayer.hasAchievedFirstGoal();
                }
                if (card.equals(commonGoalCards.get(1)) && !currentPlayer.isSecondCommonGoalAchieved()) {
                    currentPlayer.winToken(card.getStack().get(card.getStack().size() - 1));
                    int value = getGame().popCommonGoalCardStack(1);
                    currentPlayer.setScore(value);
                    currentPlayer.hasAchievedSecondGoal();
                }
            }
        }
    }


    /**
     * This method is used to establish whether the {@link Player} is picking {@link ItemTile}s in vertical or
     * horizontal line, when the second {@link ItemTile} is being picked
     * @param tilePositionMessage the {@link EventMessage} containing the position that the {@link Player} intends to pick
     */
    private void setAlongsideWhat(TilePositionMessage tilePositionMessage){
        if (tilePositionMessage.getPosition().getColumn() == getGame().getBuffer().get(0).getColumn()) {
            getGame().setAlongSideColumn(true);
        } else {
            getGame().setAlongSideRow(true);
        }
    }

    /**
     * This method is used by the controller to get the tile {@link ItemTile} the {@link Player} has picked from the
     * {@link LivingRoomBoard} and insert it in the {@link TilePack}. It also keeps track of the {@link Position} of the
     * tiles a {@link Player} picks during a turn, and checks that he can pick only in a straight line
     * @param tilePositionMessage containing the position the {@link Player} intends to pick
     */
    private void drawTileAndInsertInTilePack(TilePositionMessage tilePositionMessage) {
        ItemTile itemTile = getGame().getLivingRoomBoard().getSpace(tilePositionMessage.getPosition()).drawTile();
        getGame().getBuffer().add(tilePositionMessage.getPosition());
        getGame().insertTileInTilePack(itemTile);
        getGame().setTurnPhase(GamePhase.PICKING_TILES);
    }


    /**
     * Getter method
     * @return the object referring to the {@link Game#} that is being played
     */
    public Game getGame () {
        return game;
    }
}


