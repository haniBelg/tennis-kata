package kata.tennis.services;

import java.util.List;

import kata.tennis.domain.state.TennisGameState;
import kata.tennis.services.exceptions.GameAlreadyFinishedException;
import kata.tennis.services.exceptions.UnsupportedPlayersCountException;

public interface TennisGameHistoryProcessorService {
    List<TennisGameState> generateGameStatesFromHistory(String gameHistory) throws UnsupportedPlayersCountException, GameAlreadyFinishedException;
}
