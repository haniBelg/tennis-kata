package kata.tennis.services;

import java.util.List;

import kata.tennis.domain.state.TennisGameState;

/**
 * This interface defines the service for printing the score from a tennis game
 * history.
 */
public interface TennisGameHistoryPrinterService {
    /**
     * Prints the score from the given list of tennis game states.
     *
     * @param tennisGameStates the list of game states representing the history of
     *                         the game,
     *                         which will be printed.
     */
    public void printScoreFromGameStates(List<TennisGameState> tennisGameStates);
}
