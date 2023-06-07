package it.polimi.ingsw.model;

import it.polimi.ingsw.model.utils.NumberOfPlayers;
import it.polimi.ingsw.model.utils.Position;
import it.polimi.ingsw.model.utils.TileType;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertThrows;

public class PlayerTest extends TestCase {

    private Player player;
    private LivingRoomBoard livingRoomBoard;
    private String name = "De Ketelaere";
    private Position pos1, pos2;
    private TilePack tp;
    private ItemTile it1, it2, it3, it4, it5, it6;
    private ScoringToken token;
    private PersonalGoalCardDeck personalGoalCardDeck;


    @Before
    public void setUp(){
        player = new Player(name);
        livingRoomBoard = new LivingRoomBoard(NumberOfPlayers.TWO_PLAYERS);
        pos1 = new Position(1,3);
        pos2 = new Position(1, 4);
        tp = new TilePack();
        it1 = new ItemTile(TileType.CAT);
        it2 = new ItemTile(TileType.BOOK);
        it3 = new ItemTile(TileType.GAME);
        it4 = new ItemTile(TileType.TROPHY);
        it5 = new ItemTile(TileType.FRAME);
        it6 = new ItemTile(TileType.PLANT);
        token  = new ScoringToken(RomanNumber.TWO, 8);
        personalGoalCardDeck = new PersonalGoalCardDeck();
    }
    public void testPickUpTile() {
        LivingRoomBoard oldLivingRoomBoard = livingRoomBoard;
        assertNotNull(player.pickUpTile(livingRoomBoard, pos1));
        assertTrue(livingRoomBoard.getSpace(pos1).isFree());
    }

    @Test
    public void testInsertTileWithIndex() {
    ItemTile tile1 = player.pickUpTile(livingRoomBoard, pos1);
    ItemTile tile2 = player.pickUpTile(livingRoomBoard, pos2);
    tp.insertTile(tile1);
    tp.insertTile(tile2);
    player.insertTile(tp, 2, 0);
    assertTrue(tp.getSize() ==1);
    assertTrue(player.getBookshelf().getNumberOfTiles() ==1);
    player.insertTile(tp, 8, 0);
    assertTrue(tp.getSize() ==1);
    assertTrue(player.getBookshelf().getNumberOfTiles() ==1);
    }

    @Test
    public void testInsertWithoutIndex(){
        tp.insertTile(player.pickUpTile(livingRoomBoard, pos2));
        player.insertTile(tp, 3);
        assertTrue(tp.getSize()==0);
        assertTrue(player.getBookshelf().getNumberOfTiles()==1);

        tp.insertTile(player.pickUpTile(livingRoomBoard, pos1));
        player.insertTile(tp, 8);
        assertTrue(tp.getSize()==1);
        assertTrue(player.getBookshelf().getNumberOfTiles()==1);
    }


    @Test
    public void testWinToken(){
        player.winToken(token);
        int score = player.getScore();
        assertTrue(player.getTokens().size()==1);
        //assertTrue(player.getScore() == score +8);
    }

    @Test
    public void testSetStatus(){
        player.setStatus(PlayerStatus.PICKING_TILES);
        player.setStatus(PlayerStatus.INACTIVE);
        assertEquals(player.getStatus(), PlayerStatus.INACTIVE);
        assertFalse(player.getStatus().equals(PlayerStatus.PICKING_TILES));
    }

    @Test
    public void testSetPersonalGoalCard(){
        player.setPersonalGoalCard(personalGoalCardDeck.draw());
        assertFalse(player.getPersonalGoalCard()==null);
        PersonalGoalCard personalGoalCard = null;
        player.setPersonalGoalCard(personalGoalCard);
        assertTrue(player.getPersonalGoalCard()==null);
    }

    @Test
    public void testSetScore(){
        assertTrue(player.getScore()==0);
        player.setScore(80082);
        assertTrue(player.getScore()==80082);
    }

    @Test
    public void testCommonGoalCards(){
        assertFalse(player.isFirstCommonGoalAchieved());
        assertFalse(player.isSecondCommonGoalAchieved());
        player.hasAchievedFirstGoal();
        player.hasAchievedSecondGoal();
        assertTrue(player.isFirstCommonGoalAchieved());
        assertTrue(player.isSecondCommonGoalAchieved());
    }

}