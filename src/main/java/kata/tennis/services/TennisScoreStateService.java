package kata.tennis.services;

import kata.tennis.domain.state.TennisScoreState;

/**
 * This interface defines the service for managing and updating the score state
 * of a tennis game.
 */
public interface TennisScoreStateService {
    /**
     * Determines the next score state of the game based on the current score state.
     *
     * @param currentScoreState the current score state of the tennis game.
     * @return the next {@link TennisScoreState} representing the updated score
     *         state of the game.
     */
    TennisScoreState getNextScoreState(TennisScoreState currentScoreState);
}
