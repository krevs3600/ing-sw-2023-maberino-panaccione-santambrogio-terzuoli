package it.polimi.ingsw.model;

public class ThreeColumnsCommonGoalCard extends CommonGoalCard {
    public ThreeColumnsCommonGoalCard(NumberOfPlayers nop, RomanNumber roman)  {
        super(nop,roman);
    }

    public boolean toBeChecked (Bookshelf b) {
        return b.getNumberOfTiles()>=18;
    }
    public boolean AtMostThreeDifferentTypesInOneColumn(Bookshelf b, int j) {
        boolean found = false;
        int counter = 0;
        for (int i = 0; i < b.getMaxHeight() && !found; i++) {
            if (b.getGrid()[i][j]!=null && b.getGrid()[i][j].getType().equals(TileType.CAT)) {
                counter++;
                found = true;

            }
        }

        found = false;
        for (int i = 0; i < b.getMaxHeight() && !found; i++) {
            if (b.getGrid()[i][j]!=null && b.getGrid()[i][j].getType().equals(TileType.BOOK)) {
                counter++;
                found = true;

            }
        }

        found = false;
        for (int i = 0; i < b.getMaxHeight() && !found; i++) {
            if (b.getGrid()[i][j]!=null && b.getGrid()[i][j].getType().equals(TileType.GAME)) {
                counter++;
                found = true;

            }
        }

        found = false;
        for (int i = 0; i < b.getMaxHeight() && !found; i++) {
            if (b.getGrid()[i][j]!=null && b.getGrid()[i][j].getType().equals(TileType.FRAME)) {
                counter++;
                found = true;

            }
        }

        found = false;
        for (int i = 0; i < b.getMaxHeight() && !found; i++) {
            if (b.getGrid()[i][j]!=null && b.getGrid()[i][j].getType().equals(TileType.TROPHY)) {
                counter++;
                found = true;

            }
        }

        found = false;
        for (int i = 0; i < b.getMaxHeight() && !found; i++) {
            if (b.getGrid()[i][j]!=null && b.getGrid()[i][j].getType().equals(TileType.PLANT)) {
                counter++;
                found = true;

            }
        }


        return (counter >= 1) && (counter <= 3);


    }


    public boolean CheckPattern (Bookshelf b) {
        int columns=0;
        if (toBeChecked(b)) {
            for (int j = 0; j < b.getMaxWidth(); j++) {
                if(AtMostThreeDifferentTypesInOneColumn(b,j)){
                   columns++;
                }
            }
            return columns>=3;

        }

        else return false;

    }



}
