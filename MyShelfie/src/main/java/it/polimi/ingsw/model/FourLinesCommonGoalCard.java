package it.polimi.ingsw.model;

public class FourLinesCommonGoalCard extends CommonGoalCard  {
    public FourLinesCommonGoalCard(NumberOfPlayers nop, RomanNumber roman)  {
        super(nop,roman);
    }

    public boolean toBeChecked (Bookshelf b) {
        return b.getNumberOfTiles()>=20 && b.getNumberInsertableTiles()<=2;
    }
    public boolean AtMostThreeDifferentTypesInOneline(Bookshelf b, int i) {
        boolean found = false;
        boolean empty=false;
        int counter = 0;
        for(int j=0;j<b.getMaxWidth() && !empty;j++){
            if(b.getGrid()[i][j]==null){
                empty=true;
            }

        }
        if(!empty) {
            for (int j = 0; j < b.getMaxWidth() && !found; j++) {
                if (b.getGrid()[i][j].getType().equals(TileType.CAT)) {
                    counter++;
                    found = true;

                }
            }

            found = false;
            for (int j = 0; j < b.getMaxWidth() && !found; j++) {
                if (b.getGrid()[i][j].getType().equals(TileType.BOOK)) {
                    counter++;
                    found = true;

                }
            }

            found = false;
            for (int j = 0; j < b.getMaxWidth() && !found; j++) {
                if (b.getGrid()[i][j].getType().equals(TileType.GAME)) {
                    counter++;
                    found = true;

                }
            }

            found = false;
            for (int j = 0; j < b.getMaxWidth() && !found; j++) {
                if (b.getGrid()[i][j].getType().equals(TileType.FRAME)) {
                    counter++;
                    found = true;

                }
            }

            found = false;
            for (int j = 0; j < b.getMaxWidth() && !found; j++) {
                if (b.getGrid()[i][j].getType().equals(TileType.TROPHY)) {
                    counter++;
                    found = true;

                }
            }

            found = false;
            for (int j = 0; j < b.getMaxWidth() && !found; j++) {
                if (b.getGrid()[i][j].getType().equals(TileType.PLANT)) {
                    counter++;
                    found = true;

                }
            }


            return counter <= 3;
        }
        return false;


    }


    public boolean CheckPattern (Bookshelf b) {
        int lines=0;
        if (toBeChecked(b)) {
            for (int i = 0; i < b.getMaxHeight(); i++) {
                if(AtMostThreeDifferentTypesInOneline(b,i)){
                    lines++;
                }
            }
            return lines>=4;

        }

        return false;

    }



}




