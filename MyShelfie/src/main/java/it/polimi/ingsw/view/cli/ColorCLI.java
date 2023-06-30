package it.polimi.ingsw.view.cli;


/**
     * This class contains all colors used in Cli.
     */
    public enum ColorCLI {
        //Color end string, color reset
        RESET("\033[0m"),
        CLEAR("\033[H\033[2J"),

        // Background
        RED("\033[41m"),
        GREEN("\033[42m"),
        WHITE("\033[47m"),
        YELLOW("\033[43m"),
        BLUE("\033[44m"),
        CYAN("\033[46m"),
        PURPLE("\033[45m"),

        // text
        YELLOW_T("\033[0;33m"),
        RED_T("\033[0;31m"),
        GREEN_T("\033[0;32m"),
        CYAN_T("\033[0;36m"),
        WHITE_T("\033[0;37m"),
        BLUE_T("\033[0;34m"),
        PURPLE_T("\033[0;35m");




        private final String code;

        ColorCLI(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
}
