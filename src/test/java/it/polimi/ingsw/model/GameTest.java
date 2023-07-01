package it.polimi.ingsw.model;

import it.polimi.ingsw.model.utils.GamePhase;
import it.polimi.ingsw.model.utils.NumberOfPlayers;
import it.polimi.ingsw.model.utils.Position;
import it.polimi.ingsw.model.utils.TileType;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class GameTest {

    private Game game1;
    private Game game2;
    private Game game3;

    @Before
    public void setUp () {
        game1 = new Game(NumberOfPlayers.TWO_PLAYERS, "Test1");
        game2 = new Game(NumberOfPlayers.THREE_PLAYERS, "Test2");
        game3 = new Game(NumberOfPlayers.THREE_PLAYERS, "Test3");
    }

    @Test
    public void initLivingRoomBoardTest() {

        // before living room board initialization
        assertNull(game1.getLivingRoomBoard());

        // after living room board initialization
        game1.initLivingRoomBoard();
        assertNotNull(game1.getLivingRoomBoard());

    }

    @Test
    public void hasEndedTest () {

        // before Game has ended
        assertFalse(game1.isEnded());

        // after Game has ended
        game1.hasEnded();
        assertTrue(game1.isEnded());
    }

    @Test
    public void setFinalTurnTest() {

        // before the final turn has started
        assertFalse(game1.isFinalTurn());

        // after the final turn has started
        game1.subscribe(new Player("Test"));
        game1.setFinalTurn();
        assertTrue(game1.isFinalTurn());
    }

    @Test
    public void insertTileInTilePack() {

        // before insertion
        assertEquals(0, game1.getTilePack().getTiles().size());

        // after insertion
        game1.subscribe(new Player("Test"));
        ItemTile testItemTile = new ItemTile(TileType.CAT);
        game1.insertTileInTilePack(testItemTile);
        assertEquals(testItemTile, game1.getTilePack().getTiles().get(0));
        assertEquals(1, game1.getTilePack().getTiles().size());
    }

    @Test
    public void subscribeTest() {

        // before subscription
        assertEquals(0, game1.getSubscribers().size());

        // after subscription
        Player player = new Player("Test");
        game1.subscribe(player);
        assertEquals(player, game1.getSubscribers().get(0));
        assertEquals(1, game1.getSubscribers().size());
    }

    @Test
    public void setDrawableTilesTest() {

        game1.initLivingRoomBoard();

        // before setting drawable tiles
        assertNotEquals(game1.getDrawableTiles(), game1.getLivingRoomBoard().getDrawableTiles());

        // after setting drawable tiles
        game1.setDrawableTiles();
        assertEquals(game1.getDrawableTiles(), game1.getLivingRoomBoard().getDrawableTiles());
    }

    @Test
    public void startGameTest() {

        // before starting the game
        assertNull(game1.getFirstPlayer());

        // after starting the game
        game1.subscribe(new Player("Turn1"));
        game1.subscribe(new Player("Turn2"));
        game1.initLivingRoomBoard();
        game1.startGame();
        assertTrue(game1.getSubscribers().contains(game1.getFirstPlayer()));
        assertEquals(game1.getTurnPhase(), GamePhase.INIT_GAME);
        assertEquals(game1.getFirstPlayer().getStatus(), PlayerStatus.PICKING_TILES);

    }

    @Test
    public void endGameTest() {

        game1.subscribe(new Player("Test1"));
        game1.subscribe(new Player("Test2"));

        // before game has ended
        assertFalse(game1.isEnded());
        assertNotEquals(PlayerStatus.INACTIVE, game1.getSubscribers().get(0).getStatus());
        assertNotEquals(PlayerStatus.INACTIVE, game1.getSubscribers().get(1).getStatus());

        // after game has ended
        game1.endGame(game1.getSubscribers().get(0));
        assertTrue(game1.isEnded());
        assertEquals(PlayerStatus.INACTIVE, game1.getSubscribers().get(0).getStatus());
        assertEquals(PlayerStatus.INACTIVE, game1.getSubscribers().get(1).getStatus());
    }

    @Test
    public void popCommonGoalCardStackTest() {

        game1.subscribe(new Player("Test1"));
        game1.subscribe(new Player("Test2"));
        game1.initLivingRoomBoard();

        // before popping the scoring token
        assertEquals(8, game1.getLivingRoomBoard().getCommonGoalCards().get(0).getStack().get(1).getValue());

        // after popping the scoring token
        assertEquals(8, game1.popCommonGoalCardStack(0));
        assertEquals(4, game1.getLivingRoomBoard().getCommonGoalCards().get(0).getStack().get(0).getValue());
    }

    @Test
    public void incrementCursorTest() {

        game1.subscribe(new Player("Test1"));
        game1.subscribe(new Player("Test2"));
        for (Player player : game1.getSubscribers()){
            player.setStatus(PlayerStatus.ACTIVE);
        }
        // before incrementing the cursor
        assertEquals(0, game1.getCursor());

        // after incrementing the cursor
        game1.incrementCursor();
        assertEquals(1, game1.getCursor());

        // after further incrementing the cursor to come back to the initial value
        game1.incrementCursor();
        assertEquals(0, game1.getCursor());

        // if another player subscribes but not active, the cursor skips him
        game1.subscribe(new Player("Test3"));
        game1.getSubscribers().get(2).setStatus(PlayerStatus.INACTIVE);
        game1.incrementCursor();
        game1.incrementCursor();
        assertEquals(0, game1.getCursor());

    }

    @Test
    public void setFirstPlayerTest() {

        // before setting the first player
        assertNull(game1.getFirstPlayer());

        // after setting the first player
        Player player = new Player("Test");
        game1.setFirstPlayer(player);
        assertEquals(player, game1.getFirstPlayer());
    }

    @Test
    public void setFirstPlayerHasEnded() {

        game1.subscribe(new Player("Test1"));

        // before first player has finished
        assertFalse(game1.isFirstPlayerHasEnded());

        // after the first player has ended
        game1.setFirstPlayerHasEnded();
        assertTrue(game1.isFirstPlayerHasEnded());
    }

    @Test
    public void setAlongTest() {

        // before setting
        assertFalse(game1.isAlongSideRow());
        assertFalse(game1.isAlongSideColumn());

        // after setting the column boolean
        game1.setAlongSideColumn(true);
        assertFalse(game1.isAlongSideRow());
        assertTrue(game1.isAlongSideColumn());

        // after setting also the row boolean
        game1.setAlongSideRow(true);
        assertTrue(game1.isAlongSideRow());
        assertTrue(game1.isAlongSideColumn());
    }

    @Test
    public void setColumnChoice() {

        game1.subscribe(new Player("Test1"));

        // if the choice is within the bounds
        int choice = 3;
        game1.setColumnChoice(choice);
        assertEquals(choice, game1.getColumnChoice());

        // if the choice is outside the bounds: an exception is thrown
        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> game1.setColumnChoice(10));

        String expectedMessage = "invalid column, please choose another one;";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void setTurnPhaseTest () {

        game1.subscribe(new Player("Test1"));
        game1.subscribe(new Player("Test2"));
        game1.getSubscribers().get(0).setStatus(PlayerStatus.ACTIVE);
        game1.getSubscribers().get(1).setStatus(PlayerStatus.ACTIVE);
        game1.initLivingRoomBoard();

        game1.setTurnPhase(GamePhase.PLACING_TILES);
        assertEquals(game1.getTurnPhase(), GamePhase.PLACING_TILES);

        game1.setTurnPhase(GamePhase.INIT_GAME);
        assertEquals(game1.getTurnPhase(), GamePhase.INIT_GAME);

        // before setting the init turn phase
        game1.getBuffer().add(new Position(0, 0));
        game1.getTilePack().getTiles().add(new ItemTile(TileType.CAT));
        game1.setAlongSideColumn(true);
        game1.setAlongSideRow(true);
        assertEquals(1, game1.getBuffer().size());
        assertEquals(1, game1.getTilePack().getSize());
        assertTrue(game1.isAlongSideColumn());
        assertTrue(game1.isAlongSideRow());
        assertEquals(0, game1.getDrawableTiles().size());
        assertEquals(0, game1.getCursor());

        // after setting the init turn phase
        game1.setTurnPhase(GamePhase.INIT_TURN);
        assertEquals(game1.getTurnPhase(), GamePhase.INIT_TURN);

        assertEquals(0, game1.getBuffer().size());
        assertEquals(0, game1.getTilePack().getSize());
        assertFalse(game1.isAlongSideColumn());
        assertFalse(game1.isAlongSideRow());
        assertEquals(game1.getLivingRoomBoard().getDrawableTiles().size(), game1.getDrawableTiles().size());
        assertEquals(1, game1.getCursor());

        game1.setTurnPhase(GamePhase.PICKING_TILES);
        assertEquals(game1.getTurnPhase(), GamePhase.PICKING_TILES);

        game1.setTurnPhase(GamePhase.COLUMN_CHOICE);
        assertEquals(game1.getTurnPhase(), GamePhase.COLUMN_CHOICE);
    }
}
