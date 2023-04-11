package it.polimi.ingsw.model;

public class EightCardsCommonGoalCard extends CommonGoalCard {

    public EightCardsCommonGoalCard(NumberOfPlayers nop, RomanNumber roman)  {
        super(nop,roman);
    }

    public boolean toBeChecked (Bookshelf b) {
        return b.getNumberOfTiles()>=8;
    }

    public boolean CheckPattern (Bookshelf b) {
        boolean found=false;
        TileType[] tts = {TileType.CAT, TileType.BOOK, TileType.GAME, TileType.FRAME, TileType.TROPHY, TileType.PLANT};
        int counter=0;
        if (toBeChecked(b)) {
                for (TileType tt : tts) {
                    for (int i = 0; i < b.getMaxWidth() && !found; i++) {
                        for (int j = 0; j < b.getMaxHeight() && !found; j++) {
                            if (b.getGrid()[i][j].getType().equals(tt)) {
                                counter++;
                            }
                            if (counter == 8) {
                                found = true;
                            }
                        }
                    }

                }
                return found;


            }
        else return false;
        }



    }


