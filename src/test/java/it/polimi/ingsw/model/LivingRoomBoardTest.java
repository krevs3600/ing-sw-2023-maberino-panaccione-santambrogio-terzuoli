package it.polimi.ingsw.model;

import it.polimi.ingsw.model.utils.NumberOfPlayers;
import it.polimi.ingsw.model.utils.Position;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


public class LivingRoomBoardTest {

    private LivingRoomBoard testLivingRoomBoard1;
    private LivingRoomBoard testLivingRoomBoard2;
    private LivingRoomBoard testLivingRoomBoard3;
    private List<LivingRoomBoard> testLivingRoomBoards;

    @Before
    public void setUp() {

        testLivingRoomBoards = new ArrayList<>();
        testLivingRoomBoard1 = new LivingRoomBoard(NumberOfPlayers.TWO_PLAYERS);
        testLivingRoomBoards.add(testLivingRoomBoard1);
        testLivingRoomBoard2 = new LivingRoomBoard(NumberOfPlayers.THREE_PLAYERS);
        testLivingRoomBoards.add(testLivingRoomBoard2);
        testLivingRoomBoard3 = new LivingRoomBoard(NumberOfPlayers.FOUR_PLAYERS);
        testLivingRoomBoards.add(testLivingRoomBoard3);

    }

    @Test
    public void constructorTest(){

        // check if the living room boards have only two common goal cards
        // and if the common goal cards are different
        for (LivingRoomBoard livingRoomBoard: testLivingRoomBoards) {
            assertEquals(2, livingRoomBoard.getCommonGoalCards().size());
            assertNotEquals(livingRoomBoard.getCommonGoalCards().get(0), livingRoomBoard.getCommonGoalCards().get(1));
        }

        // check if the number of tiles is correct
        int numOfTiles1=0;
        for (int i=0; i<testLivingRoomBoard1.getMaxWidth(); i++) {
            for (int j=0; j<testLivingRoomBoard1.getMaxWidth(); j++) {
                if (testLivingRoomBoard1.getSpace(new Position(i, j)).getTile()!=null) numOfTiles1++;
            }
        }
        assertEquals(29, numOfTiles1);

        int numOfTiles2=0;
        for (int i=0; i<testLivingRoomBoard2.getMaxWidth(); i++) {
            for (int j=0; j<testLivingRoomBoard2.getMaxWidth(); j++) {
                if (testLivingRoomBoard2.getSpace(new Position(i, j)).getTile()!=null) numOfTiles2++;
            }
        }
        assertEquals(37, numOfTiles2);

        int numOfTiles3=0;
        for (int i=0; i<testLivingRoomBoard3.getMaxWidth(); i++) {
            for (int j=0; j<testLivingRoomBoard3.getMaxWidth(); j++) {
                if (testLivingRoomBoard3.getSpace(new Position(i, j)).getTile()!=null) numOfTiles3++;
            }
        }
        assertEquals(45, numOfTiles3);
    }

    @Test
    public void getSpaceTest () {

        // check if there is a tile in a playable space for the living room board for two players
        Position position1 = new Position(5, 5);
        assertNotNull(testLivingRoomBoard1.getSpace(position1));

        // check if there is not a tile in a forbidden space for the living room board for two players
        Position position2 = new Position(2, 2);
        assertNull(testLivingRoomBoard1.getSpace(position2).getTile());


        // check if there is a tile in a playable space only for the living room board for three players
        assertNotNull(testLivingRoomBoard2.getSpace(position2));

        // check if there is not a tile in a forbidden space for the living room board for three players
        Position position3 = new Position(3, 1);
        assertNull(testLivingRoomBoard2.getSpace(position3).getTile());

        // check if there is a tile in a playable space only for the living room board for four players
        assertNotNull(testLivingRoomBoard3.getSpace(position3));

        // check if there is not a tile in a forbidden space for the living room board for four players
        // exception about space without a tile
        Position position4 = new Position(0, 0);
        assertNull(testLivingRoomBoard3.getSpace(position4).getTile());
    }

    @Test
    public void getAllFreeTilesTest() {

        // initial status: full living room boards, none of the tiles are free
        for (LivingRoomBoard livingRoomBoard : testLivingRoomBoards) {
            assertEquals(0, livingRoomBoard.getAllFreeTiles().size());
        }

        // after drawing all the tiles around a specific tile of a certain position: here (5, 5)
        List<Position> positions1 = new ArrayList<>();
        Position position1 = new Position(5, 4);
        positions1.add(position1);
        Position position2 = new Position(4, 5);
        positions1.add(position2);
        Position position3 = new Position(3, 4);
        positions1.add(position3);
        Position position4 = new Position(4, 3);
        positions1.add(position4);
        for (LivingRoomBoard livingRoomBoard : testLivingRoomBoards) {
            for (Position positionToDraw : positions1) {
                livingRoomBoard.getSpace(positionToDraw).drawTile();
            }
            assertEquals(1, livingRoomBoard.getAllFreeTiles().size());
        }

        // after isolating the tiles on the borders of the board
        List<Position> positions2 = new ArrayList<>();
        Position position5 = new Position(3, 7);
        positions2.add(position5);
        Position position6 = new Position(1, 3);
        positions2.add(position6);
        Position position7 = new Position(5, 1);
        positions2.add(position7);
        Position position8 = new Position(7, 5);
        positions2.add(position8);
        for (Position positionToDraw : positions2) {
            testLivingRoomBoard2.getSpace(positionToDraw).drawTile();
        }
        assertEquals(4+1, testLivingRoomBoard2.getAllFreeTiles().size());


    }

    @Test
    public void getDrawableTilesTest() {

        // initial status: full living room boards
        int[] testNumOfDrawableTiles = {16, 20, 20};
        int i = 0;
        for (LivingRoomBoard livingRoomBoard: testLivingRoomBoards) {
            assertEquals(testNumOfDrawableTiles[i], livingRoomBoard.getDrawableTiles().size());
            i++;
        }

        // after taking a tile
        Position position = new Position(4, 4);
        for (LivingRoomBoard livingRoomBoard: testLivingRoomBoards) {
            livingRoomBoard.getSpace(position).drawTile();
        }
        i=0;
        for (LivingRoomBoard livingRoomBoard: testLivingRoomBoards) {
            assertEquals(testNumOfDrawableTiles[i]+4, livingRoomBoard.getDrawableTiles().size());
            i++;
        }
    }

    @Test
    public void refillTest() {

        // keeping in the living room boards only free tiles
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
            testLivingRoomBoard1.getSpace(position).drawTile();
        }

        positions.add(new Position(0, 3));
        positions.add(new Position(3, 8));
        positions.add(new Position(2, 6));
        positions.add(new Position(2, 2));
        positions.add(new Position(5, 0));
        positions.add(new Position(6, 2));
        positions.add(new Position(8, 5));
        positions.add(new Position(6, 6));

        for (Position position: positions) {
            testLivingRoomBoard2.getSpace(position).drawTile();
        }

        positions.add(new Position(0, 4));
        positions.add(new Position(1, 5));
        positions.add(new Position(3, 1));
        positions.add(new Position(4, 0));
        positions.add(new Position(4, 8));
        positions.add(new Position(5, 7));
        positions.add(new Position(7, 3));
        positions.add(new Position(8, 4));

        for (Position position: positions) {
            testLivingRoomBoard3.getSpace(position).drawTile();
        }
        int k=0;
        for (LivingRoomBoard livingRoomBoard: testLivingRoomBoards) {
            assertEquals(16, livingRoomBoard.getAllFreeTiles().size());
            livingRoomBoard.refill();
            int numOfTiles=0;
            for (int i=0; i<testLivingRoomBoard1.getMaxWidth(); i++) {
                for (int j=0; j<testLivingRoomBoard1.getMaxWidth(); j++) {
                    if (livingRoomBoard.getSpace(new Position(i, j)).getTile()!=null) numOfTiles++;
                }
            }
            assertEquals(29 + 8L *k, numOfTiles);
            k++;
        }

    }


}
