package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.CommonGoalCard.CommonGoalCard;
import it.polimi.ingsw.model.ModelView.LivingRoomBoardView;
import it.polimi.ingsw.model.utils.GamePhase;
import it.polimi.ingsw.model.utils.Position;
import it.polimi.ingsw.model.utils.TileType;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.network.MessagesToClient.errorMessages.IllegalTilePositionErrorMessage;
import it.polimi.ingsw.network.MessagesToClient.errorMessages.NotEnoughInsertableTilesErrorMessage;
import it.polimi.ingsw.network.MessagesToClient.errorMessages.NotEnoughInsertableTilesInColumnErrorMessage;
import it.polimi.ingsw.network.MessagesToClient.errorMessages.UpperBoundTilePackErrorMessage;
import it.polimi.ingsw.network.MessagesToClient.requestMessage.PlayerJoinedLobbyMessage;
import it.polimi.ingsw.network.MessagesToClient.requestMessage.WaitingResponseMessage;
import it.polimi.ingsw.network.Server;
import it.polimi.ingsw.network.eventMessages.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;

public class GameController {

    private Server server;
    private Game game;

    private List<Client> clients = new ArrayList<>();

    public GameController(Server server, Game game){
        this.server = server;
        this.game = game;
    }

    public Game getGame () {
        return game;
    }
    public List<Client> getClients () {
        return this.clients;
    }


    public void update(Client client, EventMessage eventMessage) throws IllegalArgumentException, IOException {
        if (!clients.contains(client)) {
            System.err.println("Discarding event from " + client);
            return;
        }


        switch (eventMessage.getType()) {

            case GAME_CHOICE -> {
                GameNameChoiceMessage gameNameChoiceMessage = (GameNameChoiceMessage) eventMessage;
                //clients.add(client);
                Player newPlayer = new Player(eventMessage.getNickname());
                game.subscribe(newPlayer);
                // if players are still missing a wait message is sent to the new player
                if (game.getSubscribers().size() < game.getNumberOfPlayers().getValue()-1){
                    client.onMessage(new WaitingResponseMessage(game.getNumberOfPlayers().getValue()-game.getSubscribers().size()));
                }
                for (Client player : this.getClients()){
                    // notify other clients that a new player has joined the game
                    if (!player.equals(client)){
                        player.onMessage(new PlayerJoinedLobbyMessage(eventMessage.getNickname()));
                    }

                    if (game.getSubscribers().size() < game.getNumberOfPlayers().getValue()){
                        player.onMessage(new WaitingResponseMessage(game.getNumberOfPlayers().getValue()-game.getSubscribers().size()));
                    }
                }

                if (game.getSubscribers().size() == game.getNumberOfPlayers().getValue()) {
                    server.removeGameFromLobby(gameNameChoiceMessage.getGameChoice());
                    game.initLivingRoomBoard();
                    for (Player player : game.getSubscribers()){
                        game.setPersonalGoalCard(player, game.getPersonalGoalCardDeck().draw());
                    }
                    game.setDrawableTiles();
                    game.startGame();
                }
            }

            case GAME_CREATION -> {
                GameCreationMessage gameCreationMessage = (GameCreationMessage) eventMessage;
                game.setGameName(gameCreationMessage.getGameName());
                Player newPlayer = new Player(eventMessage.getNickname());
                game.subscribe(newPlayer);
            }

            case GAME_SPECS -> {
                GameSpecsMessage gameSpecsMessage = (GameSpecsMessage) eventMessage;
                game.setGameName(gameSpecsMessage.getGameName());
                Player newPlayer = new Player(eventMessage.getNickname());
                game.subscribe(newPlayer);
            }

            case TILE_POSITION -> { // quindi dal server ho chiamato l'update passandogli il nickname e il messaggio di tipo TilePack
                 // il tile position message all'interno ha anche la posizione che sto prendendo
                TilePositionMessage tilePositionMessage = (TilePositionMessage) eventMessage;

                if (game.getDrawableTiles().contains(game.getLivingRoomBoard().getSpace(tilePositionMessage.getPosition()))) {
                    if (game.getBuffer().isEmpty()) {
                        ItemTile itemTile = game.drawTile(tilePositionMessage.getPosition());
                        game.getBuffer().add(tilePositionMessage.getPosition());
                        game.insertTileInTilePack(itemTile);
                    } else if (game.getBuffer().size() == 1) { // quindi se ho già una tile nel buffer delle tre tiles prendibili allora devo controllare le adiacenze nel buffer

                        if (game.getBuffer().get(0).isAdjacent(tilePositionMessage.getPosition())) {
                            if (game.getCurrentPlayer().getBookshelf().getNumberInsertableTiles() < game.getBuffer().size() + 1) {
                                if (tilePositionMessage.getPosition().getColumn() == game.getBuffer().get(0).getColumn()) {
                                    game.setAlongSideColumn(true);
                                } else {
                                    game.setAlongSideRow(true);
                                }
                                ItemTile itemTile = game.drawTile(tilePositionMessage.getPosition());
                                game.getBuffer().add(tilePositionMessage.getPosition());
                                game.insertTileInTilePack(itemTile);
                            } else {
                                client.onMessage(new NotEnoughInsertableTilesErrorMessage(eventMessage.getNickname(), "The number of insertable tiles in your bookshelf is too small, you cannot insert all of the tiles then"));
                            }
                        } else {
                            client.onMessage(new IllegalTilePositionErrorMessage(eventMessage.getNickname(), "Invalid position, please choose another one"));
                        }
                    } else if (game.getBuffer().size() == 2) {
                        boolean fairPosition = false;
                        for (Position pos : game.getBuffer()) { // scorro le posizioni indicate nel buffer degli adiacenti
                            if (pos.isAdjacent(tilePositionMessage.getPosition())) {
                                fairPosition = true;
                                break;
                            }
                        }
                        if (fairPosition) {

                            if (game.isAlongSideColumn()) {
                                if (tilePositionMessage.getPosition().getColumn() == game.getBuffer().get(0).getColumn()) {
                                        if (game.getCurrentPlayer().getBookshelf().getNumberInsertableTiles() < game.getBuffer().size() + 1) {
                                            ItemTile itemTile = game.drawTile(tilePositionMessage.getPosition()); // controllo sulla bookshelf del numero di Tiles che posso inserire, escludendo a pripri il caso in cui  sono minori di quelli nel tiles.

                                            game.getBuffer().add(tilePositionMessage.getPosition());
                                            game.insertTileInTilePack(itemTile);
                                        } else {
                                            client.onMessage(new NotEnoughInsertableTilesErrorMessage(eventMessage.getNickname(), "The number of insertable tiles in your bookshelf is too small, you cannot insert all of the tiles then"));
                                        }
                                }
                                else {
                                    client.onMessage(new IllegalTilePositionErrorMessage(eventMessage.getNickname(), "Invalid position, please choose another one"));
                                }
                            } else if (game.isAlongSideRow()) {
                                if (tilePositionMessage.getPosition().getRow() == game.getBuffer().get(0).getRow()) {
                                    if (game.getCurrentPlayer().getBookshelf().getNumberInsertableTiles() < game.getBuffer().size() + 1) {
                                        ItemTile itemTile = game.drawTile(tilePositionMessage.getPosition());
                                        game.getBuffer().add(tilePositionMessage.getPosition());
                                        game.insertTileInTilePack(itemTile);
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
                } catch (IndexOutOfBoundsException e) {}
            }

            case ITEM_TILE_INDEX -> {
                ItemTileIndexMessage itemTileIndexMessage = (ItemTileIndexMessage) eventMessage;
                game.getCurrentPlayer().insertTile(game.getTilePack(), game.getColumnChoice(), itemTileIndexMessage.getIndex());
                try {
                    game.setColumnChoice(game.getColumnChoice());
                } catch (IndexOutOfBoundsException e) {}
            }

            case SWITCH_PHASE -> {
                SwitchPhaseMessage switchPhaseMessage = (SwitchPhaseMessage) eventMessage;
                game.setTurnPhase(switchPhaseMessage.getGamePhase());
            }

            case END_TURN -> {
                computeScoreMidGame();

                if (game.getCurrentPlayer().getBookshelf().isFull()) {
                    if (game.getCurrentPlayer().equals(game.getLastPlayer())){
                        //TODO: show always the score to the clients, updating it when needed
                        Player winner = getWinner();
                        /*for (Client c : getClients()){
                            // todo controllare messaggi e conto punteggi e capire come mandarli
                            Player p = game.getSubscribers().stream().filter(x -> x.getName().equals(eventMessage.getNickname())).toList().get(0);
                            c.onMessage(new ScoreMessage(eventMessage.getNickname(), p.getScore()));
                        }

                         */
                        //TODO: create a new message specific to the case when the last player in the rotation fills the bookshelf
                        game.endGame(winner);
                    }
                    game.setFinalTurn();
                }

                if(game.isFinalTurn()) {
                    // game is ended
                    if (game.getCurrentPlayer().equals(game.getLastPlayer())){
                        Player winner = getWinner();
                        game.endGame(winner);
                    }
                }
                if (!game.isEnded()){
                    if(game.getLivingRoomBoard().getAllFree().size()==game.getLivingRoomBoard().getDrawableTiles().size()) {
                        game.refillLivingRoomBoard();
                    }
                    game.setTurnPhase(GamePhase.PICKING_TILES);
                    game.changeTurn();
                }
            }
            case FILL_BOOKSHELF -> {
                game.getCurrentPlayer().getBookshelf().insertTileTest();
                game.insertTileInTilePack(new ItemTile(TileType.CAT));
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
            game.setPlayerScore(score, player);
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

/*
 //@TODO iterare sui giocatori, non get(0)
 public void addNewGame(int numOfPlayers) {
 Game game = new Game(Arrays.stream(NumberOfPlayers.values()).filter(x -> x.getValue() == numOfPlayers).toList().get(0));
 }


*/

    /**
     * This method is used to keep track of score changes of a player during the game.
     * In particular, the score is updated every time the player receives a scoring token
     * @return int It returns the updated score of the player
     */
    private void computeScoreMidGame(){
        List<CommonGoalCard> commonGoalCards = game.getLivingRoomBoard().getCommonGoalCards();
        for(CommonGoalCard card : commonGoalCards) {
            Player currentPlayer = game.getCurrentPlayer();
            if (card.toBeChecked(currentPlayer.getBookshelf())) {
                if(card.CheckPattern(currentPlayer.getBookshelf())){
                    //ScoringToken tempToken = card.getStack().pop();
                    //score+=tempToken.getValue();
                    if(card.equals(commonGoalCards.get(0)) && !currentPlayer.isFirstCommonGoalAchieved()) {
                        game.setPlayerScore(card.getStack().pop().getValue(), currentPlayer);
                        currentPlayer.hasAchievedFirstGoal();
                    }
                    if(card.equals(commonGoalCards.get(1)) && !currentPlayer.isSecondCommonGoalAchieved()) {
                        game.setPlayerScore(card.getStack().pop().getValue(), currentPlayer);
                        currentPlayer.hasAchievedSecondGoal();
                    }
                }
            }
        }
    }
}


