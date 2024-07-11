package kata.tennis.services;

import kata.tennis.services.exceptions.GameAlreadyFinishedException;
import kata.tennis.services.exceptions.UnsupportedPlayersCountException;

public interface TennisGameHistoryPrinterService {
    public void printScoreFromGameHistory(String gameHistory)
            throws UnsupportedPlayersCountException, GameAlreadyFinishedException;
}
