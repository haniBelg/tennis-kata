package kata.tennis.services;

import kata.tennis.services.exceptions.GameAlreadyFinishedException;
import kata.tennis.services.exceptions.UnsupportedPlayersCountException;

/**
 * This interface defines the service for printing the score from a tennis game
 * history.
 */
public interface TennisGameHistoryPrinterService {
    /**
     * Prints the score from the given game history.
     *
     * @param gameHistory the history of the game in a specific format that will be
     *                    processed
     *                    to extract and print the current score.
     * @throws UnsupportedPlayersCountException if the game history indicates an
     *                                          unsupported number of players.
     * @throws GameAlreadyFinishedException     if the game history indicates that
     *                                          the game has already finished.
     */
    public void printScoreFromGameHistory(String gameHistory)
            throws UnsupportedPlayersCountException, GameAlreadyFinishedException;
}
