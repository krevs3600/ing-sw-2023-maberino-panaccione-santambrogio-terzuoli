package it.polimi.ingsw.model;

public class TwoSquaresCommonGoalCard extends CommonGoalCard{
    public TwoSquaresCommonGoalCard(NumberOfPlayers nop, RomanNumber roman)  {
        super(nop,roman);
    }

    public boolean toBeChecked (Bookshelf b) {
        return b.getNumberOfTiles()>=8;
    }

    public boolean CheckPattern (Bookshelf b) {
        TileType tipeFound = TileType.CAT; // random initialitation
        int squares = 0;
        int[][] auxiliary = new int[b.getMaxHeight()][b.getMaxWidth()];
        for (int i = 0; i < b.getMaxHeight(); i++) {
            for (int j = 0; j < b.getMaxWidth(); j++) {
                auxiliary[i][j] = 0;
            }
        }

        if (toBeChecked(b)) {
            for (int i = 0; i < b.getMaxHeight() - 1 && squares < 2; i++) {
                for (int j = 0; j < b.getMaxWidth() - 1 && squares < 2; j++) {
                    if (b.getGrid()[i][j] != null && b.getGrid()[i + 1][j] != null
                            && b.getGrid()[i][j + 1] != null && b.getGrid()[i + 1][j + 1] != null) {
                        if (squares == 0 && b.getGrid()[i][j].getType().equals(b.getGrid()[i + 1][j].getType()) && auxiliary[i][j] == 0 &&
                                b.getGrid()[i][j].getType().equals(b.getGrid()[i][j + 1].getType()) && auxiliary[i][j + 1] == 0 &&
                                b.getGrid()[i][j].getType().equals(b.getGrid()[i + 1][j + 1].getType()) && auxiliary[i + 1][j] == 0) {
                            auxiliary[i][j] = 1;
                            auxiliary[i + 1][j] = 1;
                            auxiliary[i][j + 1] = 1;
                            auxiliary[i + 1][j + 1] = 1;
                            tipeFound = b.getGrid()[i][j].getType();
                            squares++;
                        } else if
                        (squares > 0 && b.getGrid()[i][j].getType().equals(tipeFound) && b.getGrid()[i][j].getType().equals(b.getGrid()[i + 1][j].getType()) && auxiliary[i][j] == 0 &&
                                b.getGrid()[i][j].getType().equals(b.getGrid()[i][j + 1].getType()) && auxiliary[i][j + 1] == 0 &&
                                b.getGrid()[i][j].getType().equals(b.getGrid()[i + 1][j + 1].getType()) && auxiliary[i + 1][j] == 0) {
                            auxiliary[i][j] = 1;
                            auxiliary[i + 1][j] = 1;
                            auxiliary[i][j + 1] = 1;
                            auxiliary[i + 1][j + 1] = 1;
                            squares++;

                        }

                    }
                }
            }
        }

            return (squares== 2);

        }
    }


