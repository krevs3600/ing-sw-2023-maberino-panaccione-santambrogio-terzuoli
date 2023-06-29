package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.ItemTile;
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

public class GameControllerTest {

    private GameController gameController2P, gameController3P;

    private Game twoPlayerGame;
    private Game threePlayerGame;
    private Game fourPlayerGame;

    private String twoPlayerGameName, threePlayerGameName, fourPlayerGameName;
    private Client client1, client2, client3;
    private Server server;
    private CLI cli1, cli2, cli3;




    //Messages
    private GameCreationMessage gameCreationMessage2P = new GameCreationMessage("Player1", 2, "twoPlayersGameName");
    private GameNameChoiceMessage gameChoiceMessage2P = new GameNameChoiceMessage("Player2", "twoPlayersGameName");
    private GameSpecsMessage gameSpecsMessage2P = new GameSpecsMessage("Player2", "TwoPLayersGameName", 2);
    private StartGameMessage gameStartMessage2P = new StartGameMessage("Player1");
    private GameCreationMessage gameCreationMessage3P;
    private GameNameChoiceMessage gameNameChoiceMessage3P1, gameNameChoiceMessage3P2;
    private StartGameMessage startGameMessage3P;
    private TilePositionMessage tilePositionMessage1, tilePositionMessage2, tilePositionMessage3,
            tilePositionMessage4, tilePositionMessage5, tilePositionMessage6, tilePositionMessage7,
            tilePositionMessage8, tilePositionMessage9, tilePositionMessage10, tilePositionMessage11,
            tilePositionMessage12;

    private ItemTileIndexMessage itemTileIndexMessage1, itemTileIndexMessage2;

    private BookshelfColumnMessage bookshelfColumnMessage1, bookshelfColumnMessage2;
    private SwitchPhaseMessage switchPhaseMessage;
    private EndTurnMessage endTurnMessage1, endTurnMessage2;

    @Before
    public void setUp() throws RemoteException {
        twoPlayerGameName = "twoPlayersGameName";
        threePlayerGameName = "threePlayerGameName";
        fourPlayerGameName = "fourPlayerGameName";
        twoPlayerGame = new Game(NumberOfPlayers.TWO_PLAYERS, twoPlayerGameName);
        threePlayerGame = new Game(NumberOfPlayers.THREE_PLAYERS, threePlayerGameName);
        fourPlayerGame = new Game(NumberOfPlayers.FOUR_PLAYERS, fourPlayerGameName);
        gameController2P = new GameController(twoPlayerGame);
        gameController3P = new GameController(threePlayerGame);
        server = new ServerImplementation();
        cli1 = new CLI();
        client1 = new ClientImplementation(cli1,server);
        cli2 = new CLI();
        client2 = new ClientImplementation(cli2, server);
        cli3 = new CLI();
        client3= new ClientImplementation(cli3, server);
        gameCreationMessage2P = new GameCreationMessage("Player1", 2, "twoPlayersGameName");
        gameChoiceMessage2P = new GameNameChoiceMessage("Player2", "twoPlayersGameName");
        gameStartMessage2P = new StartGameMessage("Player1");
        tilePositionMessage2 = new TilePositionMessage("Player2", new Position(1,3));
        tilePositionMessage3 = new TilePositionMessage("Player2", new Position(1,4));
        tilePositionMessage4 = new TilePositionMessage("Player2", new Position(0,0));

        itemTileIndexMessage1 = new ItemTileIndexMessage("Player1", 0);
        itemTileIndexMessage2 = new ItemTileIndexMessage("Player1", 0);
        bookshelfColumnMessage1 = new BookshelfColumnMessage("Player2", 0);
        bookshelfColumnMessage2 = new BookshelfColumnMessage("Player2", 0);
        endTurnMessage1 = new EndTurnMessage("Player1");
        endTurnMessage2 = new EndTurnMessage("Player2");
        switchPhaseMessage = new SwitchPhaseMessage("Player1", GamePhase.PICKING_TILES);

        //3 Players game
        gameCreationMessage3P = new GameCreationMessage("Player1", 3, "threePlayersGameName");
        gameNameChoiceMessage3P1 = new GameNameChoiceMessage("Player2", "threePlayersGameName");
        gameNameChoiceMessage3P2 = new GameNameChoiceMessage("Player3", "threePlayersGameName");
        startGameMessage3P = new StartGameMessage("Player1");
    }


    @Test
    public void gameCreationMessageTest() throws IOException {
        assertEquals(gameController2P.getGame().getSubscribers().size(), 0);
        gameController2P.update(client1, gameCreationMessage2P);
        assertNotNull(gameController2P.getGame());

        assertEquals("twoPlayersGameName", gameController2P.getGame().getGameName());
        assertEquals(gameController2P.getGame().getSubscribers().size(), 1);
        assertEquals("Player1", gameController2P.getGame().getSubscribers().get(0).getName());
    }

    @Test
    public void gameChoiceMessageTest() throws IOException {
        gameController2P.update(client1, gameCreationMessage2P);
        assertEquals(1, gameController2P.getGame().getSubscribers().size());
        gameController2P.update(client2, gameChoiceMessage2P);
        assertEquals(2, gameController2P.getGame().getSubscribers().size());
        assertEquals("Player1", gameController2P.getGame().getSubscribers().get(0).getName());
        assertEquals("Player2", gameController2P.getGame().getSubscribers().get(1).getName());
    }

    @Test
    public void startGameMessageTest() throws IOException {
        gameController2P.update(client1, gameCreationMessage2P);
        gameController2P.update(client2, gameChoiceMessage2P);
        //living room board must be null, since the game has not started yet
        assertNull(gameController2P.getGame().getLivingRoomBoard());
        assertEquals(0, gameController2P.getGame().getDrawableTiles().size());
        gameController2P.update(client1, gameStartMessage2P);
        //living room board has now been initialized, thus it must not be null
        assertNotNull(gameController2P.getGame().getLivingRoomBoard());
        assertEquals(16, gameController2P.getGame().getDrawableTiles().size());
        assertEquals(gameController2P.getGame().getFirstPlayer().getStatus(), PlayerStatus.PICKING_TILES);
    }


    @Test
    public void tilePositionMessageTest() throws IOException {
        // TODO: SISTEMARE INSERT TILES
        /*
        gameController2P.update(client1, gameCreationMessage2P);
        gameController2P.update(client2, gameChoiceMessage2P);
        gameController2P.update(client1, gameStartMessage2P);
        //buffer's size must be 0, since no item tile has been picked
        assertEquals(0, gameController2P.getGame().getBuffer().size());
        tilePositionMessage1 = new TilePositionMessage(gameController2P.getGame().getCurrentPlayer().getName(), new Position(1,3));
        gameController2P.update(client2, tilePositionMessage1);

        assertEquals(1, gameController2P.getGame().getBuffer().size());

        tilePositionMessage1 = new TilePositionMessage(gameController2P.getGame().getCurrentPlayer().getName(), new Position(1,4));
        gameController2P.update(client2, tilePositionMessage3);
        assertEquals(2, gameController2P.getGame().getBuffer().size());
        System.out.println(gameController2P.getGame().getLivingRoomBoard().getDrawableTiles().size());
        gameController2P.getGame().getSubscribers().get(0).getBookshelf().insertTile(gameController2P.getGame().getTilePack(), 0);
        gameController2P.update(client2, endTurnMessage2);

        //attempt to pick 3 tiles in a straight horizontal line
        //buffer size must now be zero, since a new turn has started
        assertEquals(0, gameController2P.getGame().getBuffer().size());

        ItemTile itemTile1 = gameController2P.getGame().getLivingRoomBoard().getSpace(new Position(2,3)).getTile();
        tilePositionMessage5 = new TilePositionMessage(gameController2P.getGame().getCurrentPlayer().getName(), new Position(2,3));
        gameController2P.update(client1, tilePositionMessage5);
        assertEquals(1, gameController2P.getGame().getBuffer().size());

        tilePositionMessage6 = new TilePositionMessage(gameController2P.getGame().getCurrentPlayer().getName(), new Position(2,4));
        gameController2P.update(client1, tilePositionMessage6);
        assertEquals(2, gameController2P.getGame().getBuffer().size());

        tilePositionMessage7 = new TilePositionMessage(gameController2P.getGame().getCurrentPlayer().getName(), new Position(2,5));
        gameController2P.update(client1, tilePositionMessage7);
        //check that tile pack and buffer have been filled correctly
        assertEquals(3, gameController2P.getGame().getBuffer().size());
        assertEquals(3, gameController2P.getGame().getTilePack().getSize());
        assertEquals(itemTile1, gameController2P.getGame().getTilePack().getTiles().get(0));
        gameController2P.getGame().getSubscribers().get(0).getBookshelf().insertTile(gameController2P.getGame().getTilePack(), 0);

        endTurnMessage1 =  new EndTurnMessage(gameController2P.getGame().getCurrentPlayer().getName());
        gameController2P.update(client1, endTurnMessage1);
        assertEquals(0, gameController2P.getGame().getBuffer().size());

        //preparation for picking 3 tiles in a vertical line
        tilePositionMessage8 = new TilePositionMessage(gameController2P.getGame().getCurrentPlayer().getName(), new Position(4,1));
        gameController2P.update(client2, tilePositionMessage8);
        tilePositionMessage9 = new TilePositionMessage(gameController2P.getGame().getCurrentPlayer().getName(), new Position(5,1));
        gameController2P.update(client2, tilePositionMessage9);
        gameController2P.getGame().getSubscribers().get(1).getBookshelf().insertTile(gameController2P.getGame().getTilePack(), 0);
        endTurnMessage2 = new EndTurnMessage(gameController2P.getGame().getCurrentPlayer().getName());
        gameController2P.update(client2, endTurnMessage2);

        //test picking three tiles in a straight vertical line
        tilePositionMessage10 = new TilePositionMessage(gameController2P.getGame().getCurrentPlayer().getName(), new Position(3,2));
        gameController2P.update(client1, tilePositionMessage10);
        tilePositionMessage11 = new TilePositionMessage(gameController2P.getGame().getCurrentPlayer().getName(), new Position(4,2));
        gameController2P.update(client1, tilePositionMessage11);
        tilePositionMessage12 = new TilePositionMessage(gameController2P.getGame().getCurrentPlayer().getName(), new Position(5,2));
        gameController2P.update(client1, tilePositionMessage12);
        assertEquals(3, gameController2P.getGame().getBuffer().size());
        assertEquals(3, gameController2P.getGame().getTilePack().getSize());

         */
    }

    @Test
    public void switchPhaseMessageTest() throws IOException {;
        gameController2P.update(client1, gameCreationMessage2P);
        gameController2P.update(client2, gameChoiceMessage2P);
        gameController2P.update(client1, gameStartMessage2P);
        //Checking that upon beginning of the game, the gamephase corresponds to INIT_GAME
        assertEquals(GamePhase.INIT_GAME, gameController2P.getGame().getTurnPhase());
        //switching phase and testing whether the controller reacted correctly to the switch
        gameController2P.update(client1, switchPhaseMessage);
        assertEquals(GamePhase.PICKING_TILES, gameController2P.getGame().getTurnPhase());


    }

    @Test
    public void TilePackIndexAndBookshelfMessagesTest() throws IOException {
        gameController2P.update(client1, gameCreationMessage2P);
        gameController2P.update(client2, gameChoiceMessage2P);
        gameController2P.update(client1, gameStartMessage2P);


        //picking 2 tiles from the board and inserting them in the tilepack
        assertEquals(0, gameController2P.getGame().getTilePack().getSize());
        assertEquals(0, gameController2P.getGame().getSubscribers().get(0).getBookshelf().getNumberOfTiles());
        assertEquals(0, gameController2P.getGame().getSubscribers().get(1).getBookshelf().getNumberOfTiles());

        ItemTile tile1 = gameController2P.getGame().getLivingRoomBoard().getSpace(new Position(1,3)).getTile();
        tilePositionMessage1 = new TilePositionMessage(gameController2P.getGame().getCurrentPlayer().getName(), new Position(1,3));
        gameController2P.update(client1, tilePositionMessage1);
        tilePositionMessage3 = new TilePositionMessage(gameController2P.getGame().getCurrentPlayer().getName(), new Position(1,4));
        gameController2P.update(client1, tilePositionMessage3);

        //check that sizes of buffer and tile pack are correct
        assertEquals(2, gameController2P.getGame().getTilePack().getSize());
        assertEquals(tile1, gameController2P.getGame().getTilePack().getTiles().get(0));

        //selecting column and in which the tile will be inserted and the index of the tilepack from which it'll be taken
        gameController2P.update(client1, bookshelfColumnMessage1);
        gameController2P.update(client1, itemTileIndexMessage1);

        //checking that the bookshelf has been filled correctly
        assertEquals(tile1.getType(), gameController2P.getGame().getCurrentPlayer().getBookshelf().getGrid()[gameController2P.getGame().getCurrentPlayer().getBookshelf().getMaxHeight()-1][0].getType());
        assertEquals(1, gameController2P.getGame().getTilePack().getSize());
        assertEquals(1, gameController2P.getGame().getCurrentPlayer().getBookshelf().getNumberOfTiles());
        assertEquals(1, gameController2P.getGame().getSubscribers().get(0).getBookshelf().getNumberOfTiles());
        assertEquals(0, gameController2P.getGame().getSubscribers().get(1).getBookshelf().getNumberOfTiles());

        //same, but for a second tile
        gameController2P.update(client1, bookshelfColumnMessage2);
        gameController2P.update(client1, itemTileIndexMessage2);

        assertEquals(0, gameController2P.getGame().getTilePack().getSize());

        assertEquals(2, gameController2P.getGame().getCurrentPlayer().getBookshelf().getNumberOfTiles());
        assertEquals(2, gameController2P.getGame().getSubscribers().get(0).getBookshelf().getNumberOfTiles());
        assertEquals(0, gameController2P.getGame().getSubscribers().get(1).getBookshelf().getNumberOfTiles());

    }

    @Test
    public void endGameMessage2PLayersTest() throws IOException {
        //initialize game
        gameController2P.update(client1, gameCreationMessage2P);
        gameController2P.update(client2, gameChoiceMessage2P);
        gameController2P.update(client1, gameStartMessage2P);

        //check that at the start of the game the bookshelves of all the players are empty and their score is equal to zero
        for(int i = 0; i< gameController2P.getGame().getSubscribers().size(); i++){
            assertEquals(0, gameController2P.getGame().getSubscribers().get(i).getScore());
            assertEquals(0, gameController2P.getGame().getSubscribers().get(i).getBookshelf().getNumberOfTiles());
        }

        //fill the player's bookshelf, so that once his turn ends, the game will go in endgame mode
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
        //send endTurn message, and check that final turn is triggered correctly
        EndTurnMessage endGame = new EndTurnMessage(gameController2P.getGame().getCurrentPlayer().getName());
        gameController2P.update(client1, endGame);
        assertTrue(gameController2P.getGame().isFinalTurn());

        //the other player sends an endTurnMessage, so the game will be over
        EndTurnMessage endGame2 = new EndTurnMessage(gameController2P.getGame().getCurrentPlayer().getName());
        gameController2P.update(client2, endGame2);

        //check if scores and winner are correct
        assertTrue(gameController2P.getGame().getSubscribers().get(0).getScore()>0);
        assertEquals(0, gameController2P.getGame().getSubscribers().get(1).getScore());
        assertEquals(gameController2P.getGame().getSubscribers().get(0), gameController2P.getWinner());

        //check that the game has ended correctly
        assertTrue(gameController2P.getGame().isEnded());
        for(int i = 0; i< gameController2P.getGame().getSubscribers().size(); i++){
            assertEquals(PlayerStatus.INACTIVE, gameController2P.getGame().getSubscribers().get(i).getStatus());
        }
    }

    @Test
    public void endGameMessage3PLayersTest() throws IOException {
        //initialize game
        gameController3P.update(client1, gameCreationMessage3P);
        gameController3P.update(client2, gameNameChoiceMessage3P1);
        gameController3P.update(client3, gameNameChoiceMessage3P2);
        gameController3P.update(client1, startGameMessage3P);


        //check that at the start of the game the bookshelves of all the players are empty and their score is equal to zero
        for(int i = 0; i< gameController3P.getGame().getSubscribers().size(); i++){
            assertEquals(0, gameController3P.getGame().getSubscribers().get(i).getScore());
            assertEquals(0, gameController3P.getGame().getSubscribers().get(i).getBookshelf().getNumberOfTiles());
        }


        //first and second player send endturn message, so that we can check if the bookshelf is filled first by the last
        //player in the rotation, the game will be finished immediately after his turn
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

        //send endTurn message, and check that the game has correctly ended, since the bookshelf was filled by the last
        //player in the turn
        EndTurnMessage endGame3 = new EndTurnMessage(gameController3P.getGame().getCurrentPlayer().getName());
        gameController3P.update(client3, endGame3);
        assertTrue(gameController3P.getGame().isEnded());
        for(int i = 0; i< gameController3P.getGame().getSubscribers().size(); i++){
            assertEquals(PlayerStatus.INACTIVE, gameController3P.getGame().getSubscribers().get(i).getStatus());
        }

        //check if scores and winner are correct!
        assertEquals(0, gameController3P.getGame().getSubscribers().get(0).getScore());
        assertEquals(0, gameController3P.getGame().getSubscribers().get(1).getScore());
        assertTrue(gameController3P.getGame().getSubscribers().get(2).getScore()>0);
        assertEquals(gameController3P.getGame().getSubscribers().get(2), gameController3P.getWinner());
    }

}