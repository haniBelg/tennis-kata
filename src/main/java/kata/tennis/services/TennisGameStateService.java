package kata.tennis.services;

import kata.tennis.domain.state.TennisGameState;
import kata.tennis.services.exceptions.GameAlreadyFinishedException;

public interface TennisGameStateService {
    TennisGameState getNextGameState(TennisGameState lastGameState, Character currentWinnerId) throws GameAlreadyFinishedException;
}
