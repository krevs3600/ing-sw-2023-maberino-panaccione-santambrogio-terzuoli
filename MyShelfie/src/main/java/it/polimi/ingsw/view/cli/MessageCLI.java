package it.polimi.ingsw.view.cli;

public enum MessageCLI {
    TITLE,
    GAME_MODE,
    MENU;

    @Override
    public String toString() {
        switch(this){
            case TITLE -> {
                return
                        """
                                                                                                                                                                                     \s
                                                                                                                                                                                     \s
                                                                                                  .:^:                                                                               \s
                                                                                               :7YGBB#G?.                                                                            \s
                                            ~77!!!:      ~!!777.                              ~YB#57?YB&G:                                        :!!!^.                             \s
                                            ?B#&&&?      5&&&&G~                             :YB&5.!PBB#&?   7JJJJ7                     ~5PY^  .7PBPJJPG7                            \s
                                             75B#G^      ~###G~.                             !5##7 ~5B##G~   ~PB&G?.                    .P&Y: ^P&5^.~!:7&5                           \s
                                             :YB##!      J##&5.                              ~5B&5. .~!~:     !G#P:                     .5#Y.:5&P: 5&&P?&G:                          \s
                                             :YB##B^    !###&5.       .:^~7^   .777!~.        ?G&&P!          !G#P:                     .5#Y.!P&? .JGBBBP!                           \s
                                             :YBB5#P.  ^BBPB&5.     ~5GB#&5^   ^P&&5~.         7B&&&G?:       7P#P: .::.                .5#J 75&?   :^^^:::                          \s
                                             :YG#!Y#J .5#?JG&Y.     .^~YB&G:  ~5&#J:            ^YB#&&BY^     7P#G5PGBG5J^              .5#J.:YBP.     :YB#?                         \s
                                             :YG#!.5#!?#Y:JG#Y.        .?G&G~!P&#?.               :7P####5:   7P###BP5G#BP^   .^7JYJ?^  .Y#J. ^P#J.:^: .7P5!.   :!JYYJ!.             \s
                                             .JP#? ^PBBP^ YG#Y.          !5B#B#G!.                   ^5#B#G:  7P##5~. .5##!  ~YG?~^^7G5. YBJ.!5PB#G5PJ.!??J!  ^?PY!^^!5P^            \s
                                             .JP#J  7BB7 .5P#J.    ...    7YB#P~             ~???!:    5BBGJ  75BG^    JBB~ ~5#P?777!5#7 YBJ.:~:7BB! . ~PBB7 :JGGJ777!JBY            \s
                                             .JPBY :5GG5.:5PBJ  .~J5PP5~  ?P#5^             YGGBBG5~   ?BBP5: !5BP:   .YGY. ?GBJ777777!:.JG?.   .5B5.  :YGG~ !YBY77777?7~.           \s
                                             .J5B5. :::: ^5PBJ  ?5BGGBBJ.7PBJ:             :GBGGP55Y  ^PGG5J. !5G5:   :5G?  ?PG!   !55Y^ JG?.    YGP^  :YGG~ !5GJ   :YP5!            \s
                                             ^JPBG!     .?5GB5: 7YGGYYYYPGG?.               ?GGG555??YGGPY?^  75G5:   75GY. ^PG5~::JGGG7.JG?.   :5GP^  :YGP~ .YGG7:.7PBB5.           \s
                                            ~Y5PPPP!    ?55PPPY:.7YGGGGGG5!.                 ^?Y5PPGGG5YJ!:  ~55PPJ. ~5P5P?. :?5PPPP5Y7.^YPY:  .J5557  ~5557  .7YPPPPP5?^            \s
                                             .......    ......:.  .^~!!~^.                     ..::^^^::.    .:::::.  :::::.   .:::::.  .:::.   .::::. .....     .::::.              \s
                                                                                                                                                                                     \s
                                                                                                                                                                                     \s
                                                                                                                                                                                      \
                                """;
            }

            case GAME_MODE -> {
                return
                        """
                                Please select the game mode, cli or gui
                                 - A) Retro mode from textual user interface
                                 - B) Graphical User Interface""";
            }

            case MENU -> {
                return
                        """

                                 Please choose what you'd like to do
                                 - 1) resume game
                                 - 2) create game
                                 - 3) join game
                                 - 4) exit\
                                """;
            }
            default -> {
                return "";
            }
        }
    }
}
