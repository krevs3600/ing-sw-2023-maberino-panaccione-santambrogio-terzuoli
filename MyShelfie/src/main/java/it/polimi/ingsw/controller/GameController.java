package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.CommonGoalCard.CommonGoalCard;
import it.polimi.ingsw.model.ModelView.LivingRoomBoardView;
import it.polimi.ingsw.model.utils.Position;
import it.polimi.ingsw.model.utils.TileType;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.network.MessagesToClient.requestMessage.IllegalTilePositionErrorMessage;
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
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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


    public void update(Client client, EventMessage eventMessage) throws IllegalArgumentException, RemoteException {
        if (!clients.contains(client)) {
            System.err.println("Discarding event from " + client);
            return;
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


        switch (eventMessage.getType()) {

             /*case NICKNAME -> {
             if (game.getSubscribers().size() < game.getNumberOfPlayers().getValue()) {
             game = new Game();
             }
             Player player = new Player(eventMessage.getNickName(), game.getPersonalGoalCardDeck());
             game.subscribe(player);

             }

              */


            /* to notify other players
            GameNameChoiceMessage gameNameChoiceMessage = (GameNameChoiceMessage) eventMessage;

                if (game.getSubscribers().size() <= game.getNumberOfPlayers().getValue() && !game.getSubscribers().isEmpty()) {
                    System.out.println(this.getClients().size());
                    for (Client player : this.getClients()) {
                        player.onMessage(new PlayerJoinedLobbyMessage(eventMessage.getNickName()));
                    }
                }
                clients.add(client);
                Player newPlayer = new Player(eventMessage.getNickName());
                game.subscribe(newPlayer);

                if (game.getSubscribers().size() < game.getNumberOfPlayers().getValue()){
                    client.onMessage(new WaitingResponseMessage());
                }
                if (game.getSubscribers().size() == game.getNumberOfPlayers().getValue()) {
                    server.removeGameFromLobby(gameNameChoiceMessage.getGameChoice());
                    game.initLivingRoomBoard();
                }
            * */
            case GAME_CHOICE -> {
                GameNameChoiceMessage gameNameChoiceMessage = (GameNameChoiceMessage) eventMessage;
                // clients.add(client);
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
                /*GameCreationMessage numOfPlayerMessage = (GameCreationMessage) eventMessage;
                Player player = new Player(eventMessage.getNickName(), game.getPersonalGoalCardDeck());
                game.subscribe(player);
                //game.initLivingRoomBoard(Arrays.stream(NumberOfPlayers.values()).filter(x -> x.getValue() == numOfPlayerMessage.getNumOfPlayers()).toList().get(0));
                game.setDrawableTiles();

                 */
            }
            case TILE_POSITION -> {
                TilePositionMessage tilePositionMessage = (TilePositionMessage) eventMessage;

                if (game.getDrawableTiles().contains(game.getLivingRoomBoard().getSpace(tilePositionMessage.getPosition()))) {
                    if (game.getBuffer().isEmpty()) {
                        ItemTile itemTile = game.drawTile(tilePositionMessage.getPosition());
                        game.getBuffer().add(tilePositionMessage.getPosition());
                        game.insertTileInTilePack(itemTile);
                    }

                    else if (game.getBuffer().size() == 1) {

                        if (game.getBuffer().get(0).isAdjacent(tilePositionMessage.getPosition())) {
                            if (tilePositionMessage.getPosition().getColumn() == game.getBuffer().get(0).getColumn()) {
                                game.setAlongSideColumn(true);
                            } else {
                                game.setAlongSideRow(true);
                            }
                            ItemTile itemTile = game.drawTile(tilePositionMessage.getPosition());
                            game.getBuffer().add(tilePositionMessage.getPosition());
                            game.insertTileInTilePack(itemTile);
                        }
                        else {
                            client.onMessage(new IllegalTilePositionErrorMessage(eventMessage.getNickname(), new LivingRoomBoardView(game.getLivingRoomBoard())));
                        }
                    }
                    else if (game.getBuffer().size() == 2) {
                        boolean fairPosition = false;
                        for (Position pos : game.getBuffer()) {
                            if (pos.isAdjacent(tilePositionMessage.getPosition())) {
                                fairPosition = true;
                                break;
                            }
                        }
                        if (fairPosition) {

                            if (game.isAlongSideColumn()) {
                                if (tilePositionMessage.getPosition().getColumn() != game.getBuffer().get(0).getColumn()) {
                                    // throw new IllegalAccessError("Space forbidden or empty");
                                    client.onMessage(new IllegalTilePositionErrorMessage(eventMessage.getNickname(), new LivingRoomBoardView(game.getLivingRoomBoard())));
                                }
                            } else if (game.isAlongSideRow()) {
                                if (tilePositionMessage.getPosition().getRow() != game.getBuffer().get(0).getRow()) {
                                    // throw new IllegalAccessError("Space forbidden or empty");
                                    client.onMessage(new IllegalTilePositionErrorMessage(eventMessage.getNickname(), new LivingRoomBoardView(game.getLivingRoomBoard())));
                                }
                            }
                            ItemTile itemTile = game.drawTile(tilePositionMessage.getPosition());
                            game.getBuffer().add(tilePositionMessage.getPosition());
                            game.insertTileInTilePack(itemTile);
                        }
                        client.onMessage(new IllegalTilePositionErrorMessage(eventMessage.getNickname(), new LivingRoomBoardView(game.getLivingRoomBoard())));
                    } else {
                        client.onMessage(new IllegalTilePositionErrorMessage(eventMessage.getNickname(), new LivingRoomBoardView(game.getLivingRoomBoard())));
                        //throw new IllegalAccessError("Space forbidden or empty");
                    }
                }
                else {
                    client.onMessage(new IllegalTilePositionErrorMessage(eventMessage.getNickname(), new LivingRoomBoardView(game.getLivingRoomBoard())));
                    //throw new IllegalAccessError("Space forbidden or empty");
                }
            }

            case BOOKSHELF_COLUMN -> {
                BookshelfColumnMessage bookshelfColumnMessage = (BookshelfColumnMessage) eventMessage;
                // game.getSubscribers().get(0).insertTile(game.getTilePack(), bookshelfColumnMessage.getColumn());
                try {
                    game.setColumnChoice(bookshelfColumnMessage.getColumn());
                } catch (IndexOutOfBoundsException e) {}

                /*
                if (game.getLivingRoomBoard().getAllFree().size()==game.getLivingRoomBoard().getDrawableTiles().size()) game.refillLivingRoomBoard();
                if (!game.getSubscribers().get(0).getBookshelf().isFull()) {
                    game.setTurnPhase(Game.Phase.INIT_TURN);
                    computeScoreMidGame();
                }
                else {
                    game.setTurnPhase(Game.Phase.END_GAME);
                    computeScoreEndGame();
                }

                 */
            }

            case ITEM_TILE_INDEX -> {
                ItemTileIndexMessage itemTileIndexMessage = (ItemTileIndexMessage) eventMessage;
                game.getCurrentPlayer().insertTile(game.getTilePack(), game.getColumnChoice(), itemTileIndexMessage.getIndex());
                try {
                    game.setColumnChoice(game.getColumnChoice());
                } catch (IndexOutOfBoundsException e) {}
            }

            case END_TURN -> {
                computeScoreMidGame();

                if (game.getCurrentPlayer().getBookshelf().isFull()) {
                    game.setFinalTurn();
                }

                if(game.isFinalTurn()) {
                    // game is ended
                    if (game.getCurrentPlayer().equals(game.getLastPlayer())){
                        setPlayersScoreEndGame();
                        /*for (Client c : getClients()){
                            // todo controllare messaggi e conto punteggi e capire come mandarli
                            Player p = game.getSubscribers().stream().filter(x -> x.getName().equals(eventMessage.getNickname())).toList().get(0);
                            c.onMessage(new ScoreMessage(eventMessage.getNickname(), p.getScore()));
                        }

                         */
                        game.endGame();
                    }
                }
                if (!game.isEnded()){
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
    public void setPlayersScoreEndGame() {
        for (Player player : game.getSubscribers()){
            int score = computePlayerScoreEndGame(player);
            game.setPlayerScore(score, player);
        }

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



/**
        if (eventMessage.getType()== instanceof EventMessage event) {
            if (event.equals(Event.CREATE_GAME)) {
                this.game = new Game(NumberOfPlayers.TWO_PLAYERS);
                GameView gameView = new GameView(this.game);
                gameView.addObserver(this.view, );
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
                game.setAlongSideColumn(false);
                game.setAlongSideRow(false);
                game.getBuffer().clear();
            }
            else if (game.getTurnPhase().equals(Game.Phase.PICKING_TILES)) {
                game.setTurnPhase(Game.Phase.PLACING_TILES);
                game.getSubscribers().get(0).insertTile(game.getTilePack(), (int) arg);
                game.setColumnChoice((int) arg);
                if (game.getLivingRoomBoard().getAllFree().size()==game.getLivingRoomBoard().getDrawableTiles().size()) game.refillLivingRoomBoard();
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
                        game.getBuffer().add(position);
                        game.insertTileInTilePack(itemTile);
                    } else {
                        throw new IllegalAccessError("Space forbidden or empty");
                    }
                }

                case PICKING_TILES -> {
                    // se arriva una cosa diversa dalla posizione bisogna processarla come messa nella libreria

                    if (game.getDrawableTiles().contains(game.getLivingRoomBoard().getSpace(position))) {
                        boolean fairPosition = false;
                        for (Position pos : game.getBuffer()) {
                            if (pos.isAdjacent(position)) {
                                fairPosition = true;
                                break;
                            }
                        }
                        if (fairPosition) {
                            game.getBuffer().add(position);
                            if (game.getBuffer().size()==2) {
                                if(position.getColumn() == game.getBuffer().get(0).getColumn()) {
                                    game.setAlongSideRow(true);
                                }else game.setAlongSideColumn(true);
                            }
                            else if (game.getBuffer().size()==3) {
                                if (game.isAlongSideRow()) {
                                    if (position.getColumn() != game.getBuffer().get(0).getColumn()) {
                                        throw new IllegalAccessError("Space forbidden or empty");
                                    }
                                } else if (game.isAlongSideColumn()) {
                                    if (position.getRow() != game.getBuffer().get(0).getRow()) {
                                        throw new IllegalAccessError("Space forbidden or empty");
                                    }
                                }
                            }
                            ItemTile itemTile = game.drawTile(position);
                            game.insertTileInTilePack(itemTile);
                        } else {
                            throw new IllegalAccessError("Space forbidden or empty");
                        }
                    }
                }
            }
        }
    }

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


