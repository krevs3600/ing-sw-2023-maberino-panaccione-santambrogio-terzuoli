package it.polimi.ingsw.model;

import it.polimi.ingsw.model.utils.NumberOfPlayers;
import it.polimi.ingsw.model.utils.Position;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class PlayerTest {

    private Player player;
    private LivingRoomBoard livingRoomBoard;
    private Position pos1, pos2;
    private TilePack tp;
    private ScoringToken token;
    private PersonalGoalCardDeck personalGoalCardDeck;

    @Before
    public void setUp() {

        player = new Player("Test");
        livingRoomBoard = new LivingRoomBoard(NumberOfPlayers.TWO_PLAYERS);
        pos1 = new Position(1,3);
        pos2 = new Position(1, 4);
        tp = new TilePack();
        token  = new ScoringToken(RomanNumber.TWO, 8);
        personalGoalCardDeck = new PersonalGoalCardDeck();

    }

    @Test
    public void pickUpTileTest() {
        assertEquals(livingRoomBoard.getSpace(pos1).getTile(), player.pickUpTile(livingRoomBoard, pos1));
        assertTrue(livingRoomBoard.getSpace(pos1).isFree());
    }

    @Test
    public void insertTileTest() {
        ItemTile tile1 = player.pickUpTile(livingRoomBoard, pos1);
        ItemTile tile2 = player.pickUpTile(livingRoomBoard, pos2);
        tp.insertTile(tile1);
        tp.insertTile(tile2);

        // before insertion
        assertNull(player.getBookshelf().getGrid()[player.getBookshelf().getMaxHeight()-1][2]);
        assertEquals(2, tp.getSize());

        // after insertion
        player.insertTile(tp, 2, 0);
        assertEquals(player.getBookshelf().getGrid()[player.getBookshelf().getMaxHeight()-1][2], tile1);
        assertEquals(1, tp.getSize());

        // exception about inserting in an invalid column: no tile is inserted, the tile pack does not empty
        player.insertTile(tp, player.getBookshelf().getMaxWidth(), 0);
        assertEquals(1, tp.getSize());
    }



    @Test
    public void testWinToken(){

        // before winning the token
        assertEquals(0, player.getTokens().size());

        // after winning the token
        player.winToken(token);
        assertEquals(token, player.getTokens().get(0));
        assertEquals(1, player.getTokens().size());
    }

    @Test
    public void setStatusTest(){

        // setting the first status
        player.setStatus(PlayerStatus.PICKING_TILES);
        assertEquals(player.getStatus(), PlayerStatus.PICKING_TILES);

        // changing the status
        player.setStatus(PlayerStatus.INACTIVE);
        assertEquals(player.getStatus(), PlayerStatus.INACTIVE);
    }

    @Test
    public void setPersonalGoalCardTest(){

        // setting the first personal goal card
        PersonalGoalCard personalGoalCard1 = (PersonalGoalCard) personalGoalCardDeck.draw();
        player.setPersonalGoalCard(personalGoalCard1);
        assertEquals(personalGoalCard1, player.getPersonalGoalCard());

        // changing the personal goal card
        PersonalGoalCard personalGoalCard2 = (PersonalGoalCard) personalGoalCardDeck.draw();
        player.setPersonalGoalCard(personalGoalCard2);
        assertEquals(personalGoalCard2, player.getPersonalGoalCard());
    }

    @Test
    public void setScoreTest(){

        // initial score: 0
        assertEquals(0, player.getScore());

        // after changing the player's score
        player.setScore(8);
        assertEquals(8, player.getScore());
    }

    @Test
    public void hasAchievedCommonGoalsTest(){

        // initial state: common goals not achieved yet
        assertFalse(player.isFirstCommonGoalAchieved());
        assertFalse(player.isSecondCommonGoalAchieved());

        // after achieving the first common goal
        player.hasAchievedFirstGoal();
        assertTrue(player.isFirstCommonGoalAchieved());
        assertFalse(player.isSecondCommonGoalAchieved());

        // after achieving also the second common goal
        player.hasAchievedSecondGoal();
        assertTrue(player.isFirstCommonGoalAchieved());
        assertTrue(player.isSecondCommonGoalAchieved());
    }

    @Test
    public void setPersonalGoalCardScoreTest() {

        // initial state: no score
        assertEquals(0, player.getPersonalGoalCardScore());

        // after obstaining a score
        player.setPersonalGoalCardScore(9);
        assertEquals(9, player.getPersonalGoalCardScore());
    }

    @Test
    public void setAdjacentTilesScoreTest() {

        // initial state: no score
        assertEquals(0, player.getAdjacentTilesScore());

        // after obtaining a score
        player.setAdjacentTilesScore(16);
        assertEquals(16, player.getAdjacentTilesScore());
    }


    @Test
    public void setFirstToFinishTest(){

        // initial state: player has not finished yet
        assertFalse(player.isFirstToFinish());

        // after finishing
        player.setFirstToFinish();
        assertTrue(player.isFirstToFinish());
    }

}
