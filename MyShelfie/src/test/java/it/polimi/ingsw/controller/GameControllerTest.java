package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.ItemTile;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerStatus;
import it.polimi.ingsw.model.utils.GamePhase;
import it.polimi.ingsw.model.utils.NumberOfPlayers;
import it.polimi.ingsw.model.utils.Position;
import it.polimi.ingsw.model.utils.TileType;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.network.ClientImplementation;
import it.polimi.ingsw.network.Server;
import it.polimi.ingsw.network.ServerImplementation;
import it.polimi.ingsw.network.eventMessages.*;
import it.polimi.ingsw.view.cli.CLI;
import org.junit.Before;
import org.junit.Test;




import static org.junit.Assert.*;


import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class GameControllerTest {

    private GameController gameController2P, gameController3P;

    private Client client1, client2, client3;


    //Messages
    private GameSpecsMessage gameSpecsMessage2P;
    private GameSpecsMessage gameSpecsMessage3P;
    private GameNameChoiceMessage gameNameChoiceMessage2P;
    private StartGameMessage startGameMessage2P;
    private GameNameChoiceMessage gameNameChoiceMessage3P1, gameNameChoiceMessage3P2;
    private StartGameMessage startGameMessage3P;
    private TilePositionMessage tilePositionMessage1;
    private TilePositionMessage tilePositionMessage2;

    @Before
    public void setUp() throws RemoteException {
        String twoPlayerGameName = "twoPlayersGameName";
        String threePlayerGameName = "threePlayerGameName";
        Game twoPlayerGame = new Game(NumberOfPlayers.TWO_PLAYERS, twoPlayerGameName);
        Game threePlayerGame = new Game(NumberOfPlayers.THREE_PLAYERS, threePlayerGameName);
        gameController2P = new GameController(twoPlayerGame);
        gameController3P = new GameController(threePlayerGame);
        Server server = new ServerImplementation();
        CLI cli1 = new CLI();
        client1 = new ClientImplementation(cli1, server);
        CLI cli2 = new CLI();
        client2 = new ClientImplementation(cli2, server);
        CLI cli3 = new CLI();
        client3= new ClientImplementation(cli3, server);
    }


    @Test
    public void gameCreationMessageTest() throws IOException {
        gameSpecsMessage2P = new GameSpecsMessage("Player1","twoPlayersGameName", 2);

        // before game specifications event message
        assertEquals(gameController2P.getGame().getSubscribers().size(), 0);

        // after game specification event message
        gameController2P.update(client1, gameSpecsMessage2P);
        assertEquals("twoPlayersGameName", gameController2P.getGame().getGameName());
        assertEquals(gameController2P.getGame().getSubscribers().size(), 1);
        assertEquals("Player1", gameController2P.getGame().getSubscribers().get(0).getName());
    }

    @Test
    public void gameChoiceMessageTest() throws IOException {
        gameSpecsMessage2P = new GameSpecsMessage("Player1","twoPlayersGameName", 2);
        gameNameChoiceMessage2P = new GameNameChoiceMessage("Player2", "twoPlayersGameName");
        gameController2P.update(client1, gameSpecsMessage2P);

        // before game choice event message
        assertEquals(1, gameController2P.getGame().getSubscribers().size());

        // after game choice event message
        gameController2P.update(client2, gameNameChoiceMessage2P);
        assertEquals(2, gameController2P.getGame().getSubscribers().size());
        assertEquals("Player1", gameController2P.getGame().getSubscribers().get(0).getName());
        assertEquals("Player2", gameController2P.getGame().getSubscribers().get(1).getName());
    }

    @Test
    public void startGameMessageTest() throws IOException {
        gameSpecsMessage2P = new GameSpecsMessage("Player1","twoPlayersGameName", 2);
        gameNameChoiceMessage2P = new GameNameChoiceMessage("Player2", "twoPlayersGameName");
        startGameMessage2P = new StartGameMessage("Player1");
        gameController2P.update(client1, gameSpecsMessage2P);
        gameController2P.update(client1, gameNameChoiceMessage2P);

        // before game start event message, the living room board must be null, since the game has not started yet
        assertNull(gameController2P.getGame().getLivingRoomBoard());
        assertEquals(0, gameController2P.getGame().getDrawableTiles().size());
        gameController2P.update(client1, startGameMessage2P);

        // after game start event message, the living room board has now been initialized
        assertNotNull(gameController2P.getGame().getLivingRoomBoard());
        for (Player player : gameController2P.getGame().getSubscribers()){
            assertNotNull(player.getPersonalGoalCard());
            if (!player.equals(gameController2P.getGame().getFirstPlayer())) {
                assertEquals(player.getStatus(), PlayerStatus.ACTIVE);
            }
        }
        assertEquals(16, gameController2P.getGame().getDrawableTiles().size());
        assertEquals(gameController2P.getGame().getFirstPlayer().getStatus(), PlayerStatus.PICKING_TILES);
        assertEquals(0, gameController2P.getGame().getCursor());
        assertEquals(GamePhase.INIT_GAME, gameController2P.getGame().getTurnPhase());
    }

    @Test
    public void switchPhaseMessageTest() throws IOException {;
        gameSpecsMessage2P = new GameSpecsMessage("Player1","twoPlayersGameName", 2);
        gameNameChoiceMessage2P = new GameNameChoiceMessage("Player2", "twoPlayersGameName");
        startGameMessage2P = new StartGameMessage("Player1");
        SwitchPhaseMessage switchPhaseMessage = new SwitchPhaseMessage("Player1", GamePhase.PICKING_TILES);
        gameController2P.update(client1, gameSpecsMessage2P);
        gameController2P.update(client1, gameNameChoiceMessage2P);
        gameController2P.update(client1, startGameMessage2P);

        // at the beginning the phase of the game is INIT_GAME
        assertEquals(GamePhase.INIT_GAME, gameController2P.getGame().getTurnPhase());

        // after switching phase
        gameController2P.update(client1, switchPhaseMessage);
        assertEquals(GamePhase.PICKING_TILES, gameController2P.getGame().getTurnPhase());
    }


    @Test
    public void tilePositionMessageTest() throws IOException {
        gameSpecsMessage2P = new GameSpecsMessage("Player1","twoPlayersGameName", 2);
        gameNameChoiceMessage2P = new GameNameChoiceMessage("Player2", "twoPlayersGameName");
        startGameMessage2P = new StartGameMessage("Player1");
        gameController2P.update(client1, gameSpecsMessage2P);
        gameController2P.update(client1, gameNameChoiceMessage2P);
        gameController2P.update(client1, startGameMessage2P);

        //buffer's size must be 0, since no item tile has been picked
        assertEquals(0, gameController2P.getGame().getBuffer().size());
        tilePositionMessage1 = new TilePositionMessage(gameController2P.getGame().getCurrentPlayer().getName(), new Position(1,3));
        gameController2P.update(client2, tilePositionMessage1);
        assertEquals(1, gameController2P.getGame().getBuffer().size());

        tilePositionMessage2 = new TilePositionMessage(gameController2P.getGame().getCurrentPlayer().getName(), new Position(1,4));
        gameController2P.update(client2, tilePositionMessage2);
        assertEquals(2, gameController2P.getGame().getBuffer().size());
        System.out.println(gameController2P.getGame().getLivingRoomBoard().getDrawableTiles().size());
        gameController2P.getGame().getSubscribers().get(0).getBookshelf().insertTile(gameController2P.getGame().getTilePack(), 0, 0);
        EndTurnMessage endTurnMessage2 = new EndTurnMessage(gameController2P.getGame().getCurrentPlayer().getName());
        gameController2P.update(client2, endTurnMessage2);

        //attempt to pick 3 tiles in a straight horizontal line
        //buffer size must now be zero, since a new turn has started
        assertEquals(0, gameController2P.getGame().getBuffer().size());

        ItemTile itemTile1 = gameController2P.getGame().getLivingRoomBoard().getSpace(new Position(2,3)).getTile();
        TilePositionMessage tilePositionMessage4 = new TilePositionMessage(gameController2P.getGame().getCurrentPlayer().getName(), new Position(2,3));
        gameController2P.update(client1, tilePositionMessage4);
        assertEquals(1, gameController2P.getGame().getBuffer().size());

        TilePositionMessage tilePositionMessage3 = new TilePositionMessage(gameController2P.getGame().getCurrentPlayer().getName(), new Position(2, 4));
        gameController2P.update(client1, tilePositionMessage3);
        assertEquals(2, gameController2P.getGame().getBuffer().size());

        TilePositionMessage tilePositionMessage5 = new TilePositionMessage(gameController2P.getGame().getCurrentPlayer().getName(), new Position(2, 5));
        gameController2P.update(client1, tilePositionMessage5);
        //check that tile pack and buffer have been filled correctly
        assertEquals(3, gameController2P.getGame().getBuffer().size());
        assertEquals(3, gameController2P.getGame().getTilePack().getSize());
        assertEquals(itemTile1, gameController2P.getGame().getTilePack().getTiles().get(0));
        gameController2P.getGame().getSubscribers().get(0).getBookshelf().insertTile(gameController2P.getGame().getTilePack(), 0, 0);

        EndTurnMessage endTurnMessage1 = new EndTurnMessage(gameController2P.getGame().getCurrentPlayer().getName());
        gameController2P.update(client1, endTurnMessage1);
        assertEquals(0, gameController2P.getGame().getBuffer().size());

        //preparation for picking 3 tiles in a vertical line
        TilePositionMessage tilePositionMessage6 = new TilePositionMessage(gameController2P.getGame().getCurrentPlayer().getName(), new Position(4, 1));
        gameController2P.update(client2, tilePositionMessage6);
        TilePositionMessage tilePositionMessage7 = new TilePositionMessage(gameController2P.getGame().getCurrentPlayer().getName(), new Position(5, 1));
        gameController2P.update(client2, tilePositionMessage7);
        gameController2P.getGame().getSubscribers().get(1).getBookshelf().insertTile(gameController2P.getGame().getTilePack(), 0, 0);
        endTurnMessage2 = new EndTurnMessage(gameController2P.getGame().getCurrentPlayer().getName());
        gameController2P.update(client2, endTurnMessage2);

        //test picking three tiles in a straight vertical line
        TilePositionMessage tilePositionMessage8 = new TilePositionMessage(gameController2P.getGame().getCurrentPlayer().getName(), new Position(3, 2));
        gameController2P.update(client1, tilePositionMessage8);
        TilePositionMessage tilePositionMessage9 = new TilePositionMessage(gameController2P.getGame().getCurrentPlayer().getName(), new Position(4, 2));
        gameController2P.update(client1, tilePositionMessage9);
        TilePositionMessage tilePositionMessage10 = new TilePositionMessage(gameController2P.getGame().getCurrentPlayer().getName(), new Position(5, 2));
        gameController2P.update(client1, tilePositionMessage10);
        assertEquals(3, gameController2P.getGame().getBuffer().size());
        assertEquals(3, gameController2P.getGame().getTilePack().getSize());

    }

    @Test
    public void tilePackIndexAndBookshelfMessagesTest() throws IOException {
        gameSpecsMessage2P = new GameSpecsMessage("Player1","twoPlayersGameName", 2);
        gameNameChoiceMessage2P = new GameNameChoiceMessage("Player2", "twoPlayersGameName");
        startGameMessage2P = new StartGameMessage("Player1");
        gameController2P.update(client1, gameSpecsMessage2P);
        gameController2P.update(client1, gameNameChoiceMessage2P);
        gameController2P.update(client1, startGameMessage2P);

        // before picking tiles
        assertEquals(0, gameController2P.getGame().getTilePack().getSize());
        assertEquals(0, gameController2P.getGame().getSubscribers().get(0).getBookshelf().getNumberOfTiles());
        assertEquals(0, gameController2P.getGame().getSubscribers().get(1).getBookshelf().getNumberOfTiles());

        // picking 2 tiles adjacent along the row from the board and inserting them in the tile pack
        Position position1 = new Position(1,3);
        ItemTile tile1 = gameController2P.getGame().getLivingRoomBoard().getSpace(position1).getTile();
        tilePositionMessage1 = new TilePositionMessage(gameController2P.getGame().getCurrentPlayer().getName(), position1);
        gameController2P.update(client1, tilePositionMessage1);
        Position position2 = new Position(1,4);
        ItemTile tile2 = gameController2P.getGame().getLivingRoomBoard().getSpace(position2).getTile();
        tilePositionMessage2 = new TilePositionMessage(gameController2P.getGame().getCurrentPlayer().getName(), position2);
        gameController2P.update(client1, tilePositionMessage2);

        // check buffer and tile pack
        assertEquals(2, gameController2P.getGame().getBuffer().size());
        assertEquals(2, gameController2P.getGame().getTilePack().getSize());
        assertEquals(tile1, gameController2P.getGame().getTilePack().getTiles().get(0));
        assertEquals(tile2, gameController2P.getGame().getTilePack().getTiles().get(1));
        assertEquals(position1, gameController2P.getGame().getBuffer().get(0));
        assertEquals(position2, gameController2P.getGame().getBuffer().get(1));

        // selecting column and in which the tile will be inserted and the index of the tile pack from which it'll be taken
        BookshelfColumnMessage bookshelfColumnMessage1 = new BookshelfColumnMessage("Player2", 0);
        ItemTileIndexMessage itemTileIndexMessage1 = new ItemTileIndexMessage("Player1", 0);
        gameController2P.update(client1, bookshelfColumnMessage1);
        gameController2P.update(client1, itemTileIndexMessage1);

        //checking that the bookshelf has been filled correctly
        assertEquals(tile1, gameController2P.getGame().getCurrentPlayer().getBookshelf().getGrid()[gameController2P.getGame().getCurrentPlayer().getBookshelf().getMaxHeight()-1][0]);
        assertEquals(1, gameController2P.getGame().getTilePack().getSize());
        assertEquals(1, gameController2P.getGame().getCurrentPlayer().getBookshelf().getNumberOfTiles());
        assertEquals(1, gameController2P.getGame().getSubscribers().get(0).getBookshelf().getNumberOfTiles());
        assertEquals(0, gameController2P.getGame().getSubscribers().get(1).getBookshelf().getNumberOfTiles());

        // check column choice and phase
        assertEquals(0, gameController2P.getGame().getColumnChoice());
        assertEquals(GamePhase.PLACING_TILES, gameController2P.getGame().getTurnPhase());

        //same, but for a second tile
        BookshelfColumnMessage bookshelfColumnMessage2 = new BookshelfColumnMessage(gameController2P.getGame().getCurrentPlayer().getName(), 1);
        ItemTileIndexMessage itemTileIndexMessage2 = new ItemTileIndexMessage("Player2", 0);
        gameController2P.update(client1, bookshelfColumnMessage2);
        gameController2P.update(client1, itemTileIndexMessage2);

        assertEquals(tile2, gameController2P.getGame().getCurrentPlayer().getBookshelf().getGrid()[gameController2P.getGame().getCurrentPlayer().getBookshelf().getMaxHeight()-1][1]);
        assertEquals(0, gameController2P.getGame().getTilePack().getSize());
        assertEquals(2, gameController2P.getGame().getCurrentPlayer().getBookshelf().getNumberOfTiles());
        assertEquals(2, gameController2P.getGame().getSubscribers().get(0).getBookshelf().getNumberOfTiles());
        assertEquals(0, gameController2P.getGame().getSubscribers().get(1).getBookshelf().getNumberOfTiles());

        assertEquals(1, gameController2P.getGame().getColumnChoice());
        assertEquals(GamePhase.PLACING_TILES, gameController2P.getGame().getTurnPhase());

        // picking 2 tiles adjacent along the column from the board and inserting them in the tile pack
        gameController2P.getGame().getBuffer().clear();
        Position position3 = new Position(3,7);
        ItemTile tile3 = gameController2P.getGame().getLivingRoomBoard().getSpace(position3).getTile();
        tilePositionMessage1 = new TilePositionMessage(gameController2P.getGame().getCurrentPlayer().getName(), position3);
        gameController2P.update(client2, tilePositionMessage1);
        Position position4 = new Position(4,7);
        ItemTile tile4 = gameController2P.getGame().getLivingRoomBoard().getSpace(position4).getTile();
        tilePositionMessage2 = new TilePositionMessage(gameController2P.getGame().getCurrentPlayer().getName(), position4);
        gameController2P.update(client2, tilePositionMessage2);

        // check buffer and tile pack
        assertEquals(2, gameController2P.getGame().getBuffer().size());
        assertEquals(2, gameController2P.getGame().getTilePack().getSize());
        assertEquals(tile3, gameController2P.getGame().getTilePack().getTiles().get(0));
        assertEquals(tile4, gameController2P.getGame().getTilePack().getTiles().get(1));
        assertEquals(position3, gameController2P.getGame().getBuffer().get(0));
        assertEquals(position4, gameController2P.getGame().getBuffer().get(1));

    }

    @Test
    public void endGameMessage2PLayersTest() throws IOException {
        //initialize game
        gameSpecsMessage2P = new GameSpecsMessage("Player1","twoPlayersGameName", 2);
        gameNameChoiceMessage2P = new GameNameChoiceMessage("Player2", "twoPlayersGameName");
        startGameMessage2P = new StartGameMessage("Player1");
        gameController2P.update(client1, gameSpecsMessage2P);
        gameController2P.update(client1, gameNameChoiceMessage2P);
        gameController2P.update(client1, startGameMessage2P);

        // check that at the start of the game the bookshelves of all the players are empty and their score is equal to zero
        for(int i = 0; i< gameController2P.getGame().getSubscribers().size(); i++){
            assertEquals(0, gameController2P.getGame().getSubscribers().get(i).getScore());
            assertEquals(0, gameController2P.getGame().getSubscribers().get(i).getBookshelf().getNumberOfTiles());
        }

        // end of a normal turn
        EndTurnMessage endGame = new EndTurnMessage(gameController2P.getGame().getCurrentPlayer().getName());
        gameController2P.update(client1, endGame);
        assertEquals(GamePhase.INIT_TURN, gameController2P.getGame().getTurnPhase());

        // after letting on the board only free tiles
        List<Position> positions = new ArrayList<>();
        positions.add(new Position(1, 3));
        positions.add(new Position(2, 4));
        positions.add(new Position(3, 5));
        positions.add(new Position(4, 6));
        positions.add(new Position(3, 7));
        positions.add(new Position(3, 3));
        positions.add(new Position(4, 4));
        positions.add(new Position(5, 5));
        positions.add(new Position(4, 2));
        positions.add(new Position(5, 3));
        positions.add(new Position(6, 4));
        positions.add(new Position(7, 5));
        positions.add(new Position(5, 1));

        for (Position position: positions) {
            gameController2P.getGame().getLivingRoomBoard().getSpace(position).drawTile();
        }

        EndTurnMessage endGame3 = new EndTurnMessage(gameController2P.getGame().getCurrentPlayer().getName());
        gameController2P.update(client1, endGame3);

        assertEquals(16, gameController2P.getGame().getDrawableTiles().size());

        // fill the player's bookshelf, so that once his turn ends, the game will go in endgame mode
        for (int k = 0; k< gameController2P.getGame().getCurrentPlayer().getBookshelf().getMaxWidth(); k++) {
            while (gameController2P.getGame().getCurrentPlayer().getBookshelf().getNumberInsertableTilesColumn(k)>0) {
                gameController2P.getGame().getTilePack().insertTile(new ItemTile(TileType.CAT));
                gameController2P.getGame().getTilePack().insertTile(new ItemTile(TileType.BOOK));
                gameController2P.getGame().getTilePack().insertTile(new ItemTile(TileType.TROPHY));
                for (int i = 0; i < 3; i++) {
                    gameController2P.getGame().getCurrentPlayer().getBookshelf().insertTile(gameController2P.getGame().getTilePack(), k, 0);
                }
            }
        }

        assertTrue(gameController2P.getGame().getCurrentPlayer().getBookshelf().isFull());

        // send endTurn message, and check that final turn is triggered correctly
        EndTurnMessage endGame1 = new EndTurnMessage(gameController2P.getGame().getCurrentPlayer().getName());
        gameController2P.update(client1, endGame1);
        assertTrue(gameController2P.getGame().isFinalTurn());
        assertTrue(gameController2P.getGame().isFirstPlayerHasEnded());

        // the other player sends an endTurnMessage, so the game will be over
        EndTurnMessage endGame2 = new EndTurnMessage(gameController2P.getGame().getCurrentPlayer().getName());
        gameController2P.update(client2, endGame2);

        // check if scores and winner are correct
        assertTrue(gameController2P.getGame().getSubscribers().get(0).getScore()>0);
        assertEquals(0, gameController2P.getGame().getSubscribers().get(1).getScore());
        assertEquals(gameController2P.getGame().getSubscribers().get(0), gameController2P.getWinner());

        // check that the game has ended correctly
        assertTrue(gameController2P.getGame().isEnded());
        for(int i = 0; i< gameController2P.getGame().getSubscribers().size(); i++){
            assertEquals(PlayerStatus.INACTIVE, gameController2P.getGame().getSubscribers().get(i).getStatus());
        }
    }

    @Test
    public void endGameMessage3PLayersTest() throws IOException {
        //initialize game
        gameSpecsMessage3P = new GameSpecsMessage("Player1","threePlayersGameName", 3);
        gameNameChoiceMessage3P1 = new GameNameChoiceMessage("Player2", "threePlayersGameName");
        gameNameChoiceMessage3P2 = new GameNameChoiceMessage("Player3", "threePlayersGameName");
        startGameMessage3P = new StartGameMessage("Player1");
        gameController3P.update(client1, gameSpecsMessage3P);
        gameController3P.update(client2, gameNameChoiceMessage3P1);
        gameController3P.update(client3, gameNameChoiceMessage3P2);
        gameController3P.update(client1, startGameMessage3P);


        //check that at the start of the game the bookshelves of all the players are empty and their score is equal to zero
        for(int i = 0; i< gameController3P.getGame().getSubscribers().size(); i++){
            assertEquals(0, gameController3P.getGame().getSubscribers().get(i).getScore());
            assertEquals(0, gameController3P.getGame().getSubscribers().get(i).getBookshelf().getNumberOfTiles());
        }


        // first and second player send endturn message, so that we can check if the bookshelf is filled first by the last
        // player in the rotation, the game will be finished immediately after his turn
        EndTurnMessage endGame = new EndTurnMessage(gameController3P.getGame().getCurrentPlayer().getName());
        gameController3P.update(client1, endGame);

        EndTurnMessage endGame2 = new EndTurnMessage(gameController3P.getGame().getCurrentPlayer().getName());
        gameController3P.update(client2, endGame2);

        //fill the player's bookshelf, so that once his turn ends, the game will go in endgame mode
        for (int k = 0; k< gameController3P.getGame().getCurrentPlayer().getBookshelf().getMaxWidth(); k++) {
            while (gameController3P.getGame().getCurrentPlayer().getBookshelf().getNumberInsertableTilesColumn(k)>0) {
                gameController3P.getGame().getTilePack().insertTile(new ItemTile(TileType.CAT));
                gameController3P.getGame().getTilePack().insertTile(new ItemTile(TileType.BOOK));
                gameController3P.getGame().getTilePack().insertTile(new ItemTile(TileType.TROPHY));
                for (int i = 0; i < 3; i++) {
                    gameController3P.getGame().getCurrentPlayer().getBookshelf().insertTile(gameController3P.getGame().getTilePack(), k, 0);
                }
            }
        }

        assertTrue(gameController3P.getGame().getCurrentPlayer().getBookshelf().isFull());

        // send endTurn message, and check that the game has correctly ended, since the bookshelf was filled by the last
        // player in the turn
        EndTurnMessage endGame3 = new EndTurnMessage(gameController3P.getGame().getCurrentPlayer().getName());
        gameController3P.update(client3, endGame3);
        assertTrue(gameController3P.getGame().isEnded());
        for(int i = 0; i< gameController3P.getGame().getSubscribers().size(); i++){
            assertEquals(PlayerStatus.INACTIVE, gameController3P.getGame().getSubscribers().get(i).getStatus());
        }

        // check if scores and winner are correct!
        assertEquals(0, gameController3P.getGame().getSubscribers().get(0).getScore());
        assertEquals(0, gameController3P.getGame().getSubscribers().get(1).getScore());
        assertTrue(gameController3P.getGame().getSubscribers().get(2).getScore()>0);
        assertEquals(gameController3P.getGame().getSubscribers().get(2), gameController3P.getWinner());
    }

    @Test
    public void easterEggMessageTest () throws IOException {
        gameSpecsMessage3P = new GameSpecsMessage("Player1","threePlayersGameName", 3);
        gameNameChoiceMessage3P1 = new GameNameChoiceMessage("Player2", "threePlayersGameName");
        gameNameChoiceMessage3P2 = new GameNameChoiceMessage("Player3", "threePlayersGameName");
        startGameMessage3P = new StartGameMessage("Player1");
        EasterEggMessage easterEggMessage = new EasterEggMessage("Player1");
        gameController3P.update(client1, gameSpecsMessage3P);
        gameController3P.update(client2, gameNameChoiceMessage3P1);
        gameController3P.update(client3, gameNameChoiceMessage3P2);
        gameController3P.update(client1, startGameMessage3P);

        gameController3P.update(client1, easterEggMessage);
        assertEquals(2, gameController3P.getGame().getCurrentPlayer().getBookshelf().getNumberInsertableTiles());
        assertEquals(GamePhase.PICKING_TILES, gameController3P.getGame().getTurnPhase());

    }

}
