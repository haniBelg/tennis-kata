package kata.tennis.services;

import java.util.List;

import kata.tennis.domain.state.TennisGameState;
import kata.tennis.services.exceptions.GameAlreadyFinishedException;
import kata.tennis.services.exceptions.UnsupportedPlayersCountException;

/**
 * This interface defines the service for processing the history of a tennis
 * game and generating game states.
 */
public interface TennisGameHistoryProcessorService {
    /**
     * Generates a list of game states from the given game history.
     *
     * @param gameHistory the history of the game in a specific format that will be
     *                    processed
     *                    to generate the sequence of game states.
     * @return a list of {@link TennisGameState} representing the states of the game
     *         at various points in the history.
     * @throws UnsupportedPlayersCountException if the game history indicates an
     *                                          unsupported number of players.
     * @throws GameAlreadyFinishedException     if the game history indicates that
     *                                          the game has already finished.
     */
    List<TennisGameState> generateGameStatesFromHistory(String gameHistory)
            throws UnsupportedPlayersCountException, GameAlreadyFinishedException;
}
