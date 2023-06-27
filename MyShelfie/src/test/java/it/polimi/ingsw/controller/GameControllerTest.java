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
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;




import static org.junit.Assert.*;


import java.io.IOException;
import java.rmi.RemoteException;

public class GameControllerTest {


    private GameController gameController;

    private Game twoPlayerGame;
    private Game threePlayerGame;
    private Game fourPlayerGame;

    private String twoPlayerGameName;
    private String threePlayerGameName;
    private String fourPlayerGameName;
    private Client client1;
    private Client client2;
    private Server server;
    private CLI cli1;
    private CLI cli2;



    //Messages
    private GameCreationMessage gameCreationMessage = new GameCreationMessage("Player1", 2, "twoPlayersGameName");
    private GameNameChoiceMessage gameChoiceMessage = new GameNameChoiceMessage("Player2", "twoPlayersGameName");
    private GameSpecsMessage gameSpecsMessage = new GameSpecsMessage("Player2", "TwoPLayersGameName", 2);
    private StartGameMessage gameStartMessage = new StartGameMessage("Player1");
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
        gameController = new GameController(twoPlayerGame);
        server = new ServerImplementation();
        cli1 = new CLI();
        client1 = new ClientImplementation(cli1,server);
        cli2 = new CLI();
        client2 = new ClientImplementation(cli2, server);
        gameCreationMessage = new GameCreationMessage("Player1", 2, "twoPlayersGameName");
        gameChoiceMessage = new GameNameChoiceMessage("Player2", "twoPlayersGameName");
        gameStartMessage = new StartGameMessage("Player1");
        tilePositionMessage1 = new TilePositionMessage("Player2", new Position(1,3));
        tilePositionMessage2 = new TilePositionMessage("Player2", new Position(1,3));
        tilePositionMessage3 = new TilePositionMessage("Player2", new Position(1,4));
        tilePositionMessage4 = new TilePositionMessage("Player2", new Position(0,0));
        tilePositionMessage5 = new TilePositionMessage("Player1", new Position(2,3));
        tilePositionMessage6 = new TilePositionMessage("Player1", new Position(2,4));
        tilePositionMessage7 = new TilePositionMessage("Player1", new Position(2,5));
        tilePositionMessage8 = new TilePositionMessage("Player2", new Position(4,1));
        tilePositionMessage9 = new TilePositionMessage("Player2", new Position(5,1));
        tilePositionMessage10 = new TilePositionMessage("Player1", new Position(3,2));
        tilePositionMessage11 = new TilePositionMessage("Player1", new Position(4,2));
        tilePositionMessage12 = new TilePositionMessage("Player1", new Position(5,2));
        itemTileIndexMessage1 = new ItemTileIndexMessage("Player1", 0);
        itemTileIndexMessage2 = new ItemTileIndexMessage("Player1", 0);
        bookshelfColumnMessage1 = new BookshelfColumnMessage("Player2", 0);
        bookshelfColumnMessage2 = new BookshelfColumnMessage("Player2", 0);
        endTurnMessage1 = new EndTurnMessage("Player1");
        endTurnMessage2 = new EndTurnMessage("Player2");
        switchPhaseMessage = new SwitchPhaseMessage("Player1", GamePhase.PICKING_TILES);


    }


    @Test
    public void gameCreationMessageTest() throws IOException {
        //Assert.assertNotNull(client);
        assertEquals(gameController.getGame().getSubscribers().size(), 0);
        gameController.update(client1, gameCreationMessage);
        assertNotNull(gameController.getGame());
        //System.out.println(gameController.getGame().getGameName());
        assertEquals("twoPlayersGameName", gameController.getGame().getGameName());
        assertEquals(gameController.getGame().getSubscribers().size(), 1);
        assertEquals("Player1", gameController.getGame().getSubscribers().get(0).getName());
    }

    @Test
    public void gameChoiceMessageTest() throws IOException {
        gameController.update(client1, gameCreationMessage);
        assertEquals(1, gameController.getGame().getSubscribers().size());
        gameController.update(client2, gameChoiceMessage);
        assertEquals(2, gameController.getGame().getSubscribers().size());
        assertEquals("Player1", gameController.getGame().getSubscribers().get(0).getName());
        assertEquals("Player2", gameController.getGame().getSubscribers().get(1).getName());
    }

    @Test
    public void startGameMessageTest() throws IOException {
        gameController.update(client1, gameCreationMessage);
        gameController.update(client2, gameChoiceMessage);
        assertNull(gameController.getGame().getLivingRoomBoard());
        assertEquals(0, gameController.getGame().getDrawableTiles().size());
        gameController.update(client1, gameStartMessage);
        assertNotNull(gameController.getGame().getLivingRoomBoard());
        assertEquals(16, gameController.getGame().getDrawableTiles().size());
        assertEquals(gameController.getGame().getFirstPlayer().getStatus(), PlayerStatus.PICKING_TILES);
    }


    /**@Test
    public void drawTilesTest() throws IOException {
        gameController.update(client1, gameCreationMessage);
        gameController.update(client2, gameChoiceMessage);
        gameController.update(client1, gameStartMessage);
        assertEquals(16, gameController.getGame().getLivingRoomBoard().getDrawableTiles().size());
        //System.out.println(gameController.getGame().getLivingRoomBoard().getSpace(new Position(1,3)).getTile());
        //gameController.update(client2, tilePositionMessage1);
        Assert.assertEquals(0, gameController.getGame().getBuffer().size());
        gameController.drawTileAndInsertInTilePack(tilePositionMessage1);
        assertNull(gameController.getGame().getLivingRoomBoard().getSpace(new Position(1,3)).getTile());
        assertEquals(1, gameController.getGame().getBuffer().size());
        gameController.drawTileAndInsertInTilePack((TilePositionMessage) tilePositionMessage4);
        gameController.drawTileAndInsertInTilePack(tilePositionMessage1);
        Assert.assertEquals(1, gameController.getGame().getBuffer().size());
        //Assert.assertEquals(gameController.getGame().getDrawableTiles().size(), 15);
        assertEquals(15, gameController.getGame().getLivingRoomBoard().getDrawableTiles().size());
    }   */


    @Test
    public void tilePositionMessageTest() throws IOException {
        gameController.update(client1, gameCreationMessage);
        gameController.update(client2, gameChoiceMessage);
        gameController.update(client1, gameStartMessage);
        assertEquals(0,gameController.getGame().getBuffer().size());
        gameController.update(client2, tilePositionMessage1);

        assertEquals(1,gameController.getGame().getBuffer().size());
        gameController.update(client2, tilePositionMessage3);
        assertEquals(2,gameController.getGame().getBuffer().size());
        System.out.println(gameController.getGame().getLivingRoomBoard().getDrawableTiles().size());
        gameController.getGame().getSubscribers().get(0).getBookshelf().insertTile(gameController.getGame().getTilePack(), 0);
        gameController.update(client2, endTurnMessage2);

        //attempt to pick 3 tiles in a straight horizontal line
        assertEquals(0, gameController.getGame().getBuffer().size());
        gameController.update(client1, tilePositionMessage5);
        assertEquals(1, gameController.getGame().getBuffer().size());
        gameController.update(client1, tilePositionMessage6);
        assertEquals(2, gameController.getGame().getBuffer().size());
        gameController.update(client1, tilePositionMessage7);
        assertEquals(3, gameController.getGame().getBuffer().size());
        assertEquals(3, gameController.getGame().getTilePack().getSize());
        gameController.getGame().getSubscribers().get(0).getBookshelf().insertTile(gameController.getGame().getTilePack(), 0);

        gameController.update(client1, endTurnMessage1);
        assertEquals(0, gameController.getGame().getBuffer().size());
        //preparation for picking 3 tiles in a vertical line
        gameController.update(client2, tilePositionMessage8);
        gameController.update(client2, tilePositionMessage9);
        gameController.getGame().getSubscribers().get(1).getBookshelf().insertTile(gameController.getGame().getTilePack(), 0);
        gameController.update(client2, endTurnMessage2);

        //test picking three tiles in a straight vertical line
        gameController.update(client1, tilePositionMessage10);
        gameController.update(client1, tilePositionMessage11);
        gameController.update(client1, tilePositionMessage12);
        assertEquals(3, gameController.getGame().getBuffer().size());
        assertEquals(3, gameController.getGame().getTilePack().getSize());
    }

    @Test
    public void switchPhaseMessageTest() throws IOException {;
        gameController.update(client1, gameCreationMessage);
        gameController.update(client2, gameChoiceMessage);
        gameController.update(client1, gameStartMessage);
        //Checking that upon beginning of the game, the gamephase corresponds to INIT_GAME
        assertEquals(GamePhase.INIT_GAME, gameController.getGame().getTurnPhase());
        //switching phase and testing whether the controller reacted correctly to the switch
        gameController.update(client1, switchPhaseMessage);
        assertEquals(GamePhase.PICKING_TILES, gameController.getGame().getTurnPhase());


    }

    /**
     *
     * @throws IOException

    @Before
    public void setUpForComputeScoreTests() throws IOException {
        //Starting game
        gameController.update(client1, gameCreationMessage);
        gameController.update(client2, gameChoiceMessage);
        gameController.update(client1, gameStartMessage);
        //System.out.println(gameController.getGame().getCurrentPlayer().getName());
        //first player playing first turn normally
        //gameController.update(client2, tilePositionMessage1);
        //gameController.update(client2, tilePositionMessage3);
        assertEquals(0, gameController.getGame().getSubscribers().get(0).getScore());
        assertEquals(0, gameController.getGame().getSubscribers().get(1).getScore());

    }
     */


    /**@Test
    public void bookshelfColumnAndTilePackIndexMessagesTest() throws IOException {
        gameController.update(client1, gameCreationMessage);
        gameController.update(client2, gameChoiceMessage);
        gameController.update(client1, gameStartMessage);
        gameController.update(client1, gameStartMessage);
        gameController.update(client2, tilePositionMessage1);
        gameController.update(client2, tilePositionMessage3);
        assertEquals(0, gameController.getGame().getSubscribers().get(1).getBookshelf().getNumberOfTiles());
        assertEquals(0, gameController.getGame().getSubscribers().get(0).getBookshelf().getNumberOfTiles());
        gameController.update(client2, bookshelfColumnMessage1);
        assertEquals(0, gameController.getGame().getSubscribers().get(1).getBookshelf().getNumberOfTiles());
        assertEquals(1, gameController.getGame().getSubscribers().get(0).getBookshelf().getNumberOfTiles());


    }
     */

    @Test
    public void TilePackIndexAndBookshelfMessagesTest() throws IOException {
        gameController.update(client1, gameCreationMessage);
        gameController.update(client2, gameChoiceMessage);
        gameController.update(client1, gameStartMessage);


        //picking 2 tiles from the board and inserting them in the tilepack
        assertEquals(0, gameController.getGame().getTilePack().getSize());
        assertEquals(0, gameController.getGame().getSubscribers().get(0).getBookshelf().getNumberOfTiles());
        assertEquals(0, gameController.getGame().getSubscribers().get(1).getBookshelf().getNumberOfTiles());
        gameController.update(client2, tilePositionMessage1);
        gameController.update(client2, tilePositionMessage3);
        assertEquals(2, gameController.getGame().getTilePack().getSize());

        gameController.update(client2, bookshelfColumnMessage1);
        gameController.update(client2, itemTileIndexMessage1);

        assertEquals(1, gameController.getGame().getTilePack().getSize());

        assertEquals(1, gameController.getGame().getCurrentPlayer().getBookshelf().getNumberOfTiles());
        assertEquals(1, gameController.getGame().getSubscribers().get(0).getBookshelf().getNumberOfTiles());
        assertEquals(0, gameController.getGame().getSubscribers().get(1).getBookshelf().getNumberOfTiles());

        gameController.update(client2, bookshelfColumnMessage2);
        gameController.update(client2, itemTileIndexMessage2);

        assertEquals(0, gameController.getGame().getTilePack().getSize());

        assertEquals(2, gameController.getGame().getCurrentPlayer().getBookshelf().getNumberOfTiles());
        assertEquals(2, gameController.getGame().getSubscribers().get(0).getBookshelf().getNumberOfTiles());
        assertEquals(0, gameController.getGame().getSubscribers().get(1).getBookshelf().getNumberOfTiles());

    }

    @Test
    public void endGameMessageTest() throws IOException {
        //initialize game
        gameController.update(client1, gameCreationMessage);
        gameController.update(client2, gameChoiceMessage);
        gameController.update(client1, gameStartMessage);

        assertEquals(0, gameController.getGame().getCurrentPlayer().getBookshelf().getNumberOfTiles());

        //fill the player's bookshelf, so that once his turn ends, the game will go in endgame mode
        for (int k = 0; k< gameController.getGame().getCurrentPlayer().getBookshelf().getMaxWidth(); k++) {
            while (gameController.getGame().getCurrentPlayer().getBookshelf().getNumberInsertableTilesColumn(k)>0) {
                gameController.getGame().getTilePack().insertTile(new ItemTile(TileType.CAT));
                gameController.getGame().getTilePack().insertTile(new ItemTile(TileType.BOOK));
                gameController.getGame().getTilePack().insertTile(new ItemTile(TileType.TROPHY));
                for (int i = 0; i < 3; i++) {
                    gameController.getGame().getCurrentPlayer().getBookshelf().insertTile(gameController.getGame().getTilePack(), k, 0);
                }
            }
        }

        assertTrue(gameController.getGame().getCurrentPlayer().getBookshelf().isFull());
        //send endTurn message, so that endgame will be triggered
        EndTurnMessage endGame = new EndTurnMessage(gameController.getGame().getCurrentPlayer().getName());
        gameController.update(client1, endGame);
        assertTrue(gameController.getGame().isFinalTurn());

        EndTurnMessage endGame2 = new EndTurnMessage(gameController.getGame().getCurrentPlayer().getName());
        gameController.update(client2, endGame2);

        assertTrue(gameController.getGame().getSubscribers().get(0).getScore()>0);
        assertTrue(gameController.getGame().getSubscribers().get(1).getScore()==0);
        assertEquals(gameController.getGame().getSubscribers().get(0),gameController.getWinner());
        //System.out.println(gameController.getGame().getSubscribers().get(1).getScore() + "    " + gameController.getGame().getSubscribers().get(0).getScore());
    }



}