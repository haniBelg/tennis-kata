package kata.tennis.services;

import kata.tennis.domain.state.TennisScoreState;

public interface TennisScoreStateService {
    TennisScoreState getNextScore(TennisScoreState currentScore);
}
