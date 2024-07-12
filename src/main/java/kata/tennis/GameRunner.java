package kata.tennis;

import java.util.List;

import kata.tennis.domain.state.TennisGameState;
import kata.tennis.services.TennisGameHistoryPrinterService;
import kata.tennis.services.TennisGameHistoryProcessorService;
import kata.tennis.services.impl.TennisGameHistoryPrinterServiceImpl;
import kata.tennis.services.impl.TennisGameHistoryProcessorServiceImpl;

public class GameRunner {
    private final TennisGameHistoryProcessorService tennisGameHistoryProcessorService;
    private final TennisGameHistoryPrinterService tennisGameHistoryPrinterService;

    public GameRunner() {
        tennisGameHistoryProcessorService = new TennisGameHistoryProcessorServiceImpl();
        tennisGameHistoryPrinterService = new TennisGameHistoryPrinterServiceImpl();
    }

    /**
     * Prints the score from the given game history.
     * 
     * this is the method asked for on the kata
     * Here we want you to develop a java method that will take a String as input
     * containing the character ‘A’ or ‘B’. The character ‘A’ corresponding to
     * “player A won the ball”, and ‘B’ corresponding to “player B won the ball”.
     * The java method should print the score after each won ball (for example :
     * “Player A : 15 / Player B : 30”) and print the winner of the game.
     * 
     * @param gameHistory the history of the game in a specific format that will be
     *                    processed
     *                    to generate the sequence of game states.
     */

    public void printGameScoresFromHistory(String gameHistory) {
        List<TennisGameState> tennisGameStates = tennisGameHistoryProcessorService
                .generateGameStatesFromHistory(gameHistory);
        tennisGameHistoryPrinterService.printScoreFromGameStates(tennisGameStates);
    }
}
