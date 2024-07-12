package kata;

import kata.tennis.GameRunner;

//this class is used to offer a command line entry point
public class TennisGameApplication {
    public static void main(String[] args) {
        GameRunner gameRunner = new GameRunner();
        gameRunner.printGameScoresFromHistory(args[0]);
    }
}
