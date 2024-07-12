package kata.tennis.services;

import kata.tennis.domain.state.TennisGameState;
import kata.tennis.services.exceptions.GameAlreadyFinishedException;

/**
 * This interface defines the service for managing and updating the state of a
 * tennis game.
 */
public interface TennisGameStateService {
    /**
     * Determines the next state of the game based on the last game state and the
     * current point winner.
     *
     * @param lastGameState   the last recorded state of the tennis game.
     * @param currentWinnerId the identifier of the player who won the current
     *                        point.
     * @return the next {@link TennisGameState} representing the updated state of
     *         the game.
     * @throws GameAlreadyFinishedException if the game has already finished.
     */
    TennisGameState getNextGameState(TennisGameState lastGameState, Character currentWinnerId)
            throws GameAlreadyFinishedException;
}
