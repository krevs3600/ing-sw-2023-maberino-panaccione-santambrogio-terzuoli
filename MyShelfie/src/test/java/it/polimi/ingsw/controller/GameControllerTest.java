package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.ItemTile;
import it.polimi.ingsw.model.PlayerStatus;
import it.polimi.ingsw.model.utils.NumberOfPlayers;
import it.polimi.ingsw.model.utils.Position;
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
    private GameCreationMessage gameCreationMessage = new GameCreationMessage("Franz", 2, "twoPlayersGameName");
    private GameNameChoiceMessage gameChoiceMessage = new GameNameChoiceMessage("Senofonte", "twoPlayersGameName");
    private GameSpecsMessage gameSpecsMessage = new GameSpecsMessage("Senofonte", "TwoPLayersGameName", 2);
    private StartGameMessage gameStartMessage = new StartGameMessage("Franz");
    private TilePositionMessage tilePositionMessage1;
    private TilePositionMessage tilePositionMessage2;
    private TilePositionMessage tilePositionMessage3;
    private TilePositionMessage tilePositionMessage4;
    private TilePositionMessage tilePositionMessage5;
    private TilePositionMessage tilePositionMessage6;
    private TilePositionMessage tilePositionMessage7;
    private EndTurnMessage endTurnMessage;

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
        gameCreationMessage = new GameCreationMessage("Franz", 2, "twoPlayersGameName");
        gameChoiceMessage = new GameNameChoiceMessage("Senofonte", "twoPlayersGameName");
        gameStartMessage = new StartGameMessage("Franz");
        tilePositionMessage1 = new TilePositionMessage("Senofonte", new Position(1,3));
        tilePositionMessage2 = new TilePositionMessage("Senofonte", new Position(1,3));
        tilePositionMessage3 = new TilePositionMessage("Senofonte", new Position(1,4));
        tilePositionMessage4 = new TilePositionMessage("Senofonte", new Position(0,0));
        tilePositionMessage5 = new TilePositionMessage("Franz", new Position(2,3));
        tilePositionMessage6 = new TilePositionMessage("Franz", new Position(2,4));
        tilePositionMessage7 = new TilePositionMessage("Franz", new Position(2,5));
        endTurnMessage = new EndTurnMessage("Senofonte");

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
        assertEquals("Franz", gameController.getGame().getSubscribers().get(0).getName());
    }

    @Test
    public void gameChoiceMessageTest() throws IOException {
        gameController.update(client1, gameCreationMessage);
        assertEquals(1, gameController.getGame().getSubscribers().size());
        gameController.update(client2, gameChoiceMessage);
        assertEquals(2, gameController.getGame().getSubscribers().size());
        assertEquals("Franz", gameController.getGame().getSubscribers().get(0).getName());
        assertEquals("Senofonte", gameController.getGame().getSubscribers().get(1).getName());
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


    @Test
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
    }


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
        gameController.update(client2, endTurnMessage);

        /*gameController.update(client1, tilePositionMessage5);
        gameController.update(client1, tilePositionMessage6);
        System.out.println(gameController.getGame().getBuffer().size());
        System.out.println(gameController.getGame().getLivingRoomBoard().getDrawableTiles().size())
        */

    }

}