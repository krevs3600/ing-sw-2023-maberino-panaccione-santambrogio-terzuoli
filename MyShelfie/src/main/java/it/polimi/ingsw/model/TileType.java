package it.polimi.ingsw.model;

public enum TileType {

    CAT("C", "\033[42m"),
    BOOK("B", "\033[47m"),
    GAME("G", "\033[43m"),
    FRAME("F", "\033[44m"),
    TROPHY("T", "\033[46m"),
    PLANT("P", "\033[45m");

    String abbreviation;
    String colorBackground;

    private TileType(String abbreviation, String colorBackground){
        this.abbreviation = abbreviation;
        this.colorBackground = colorBackground;
    }

    public String getAbbreviation() {
        return abbreviation;
    }
}
