package kata.tennis.services.impl;

import java.util.ArrayList;
import java.util.List;

import kata.tennis.domain.TennisPlayer;
import kata.tennis.domain.TennisScore;
import kata.tennis.domain.state.TennisGameState;
import kata.tennis.services.TennisGameHistoryProcessorService;
import kata.tennis.services.TennisGameStateService;
import kata.tennis.services.exceptions.GameAlreadyFinishedException;
import kata.tennis.services.exceptions.UnsupportedPlayersCountException;

public class TennisGameHistoryProcessorServiceImpl implements TennisGameHistoryProcessorService {
    private final TennisGameStateService tennisGameStateService;

    public TennisGameHistoryProcessorServiceImpl() {
        this.tennisGameStateService = new TennisGameStateServiceImpl();
    }

    @Override
    public List<TennisGameState> generateGameStatesFromHistory(String gameHistory)
            throws UnsupportedPlayersCountException, GameAlreadyFinishedException {
        List<TennisGameState> states = new ArrayList<>();
        TennisGameState initialGameState = generateInitialGameStateFromHistory(gameHistory);
        TennisGameState lastGameState = initialGameState;
        states.add(lastGameState);
        for (var currentWinnerId : gameHistory.toCharArray()) {
            lastGameState = tennisGameStateService.getNextGameState(lastGameState, currentWinnerId);
            states.add(lastGameState);
        }
        return states;
    }

    private TennisGameState generateInitialGameStateFromHistory(String gameHistory)
            throws UnsupportedPlayersCountException {
        List<Character> playerIds = gameHistory.chars()
                .mapToObj((c) -> (char) c)
                .distinct()
                .sorted()
                .toList();
        if (playerIds.size() > 2 || playerIds.isEmpty()) {
            throw new UnsupportedPlayersCountException(
                    String.format("'%s' not supported, should contain only two player identifiers", gameHistory));
        }
        if (playerIds.size() == 1) {
            Character uniqueChar = playerIds.get(0);
            Character nextCharacter = uniqueChar == 'Z' ? 'A' : (char) (uniqueChar + 1);
            playerIds = List.of(uniqueChar, nextCharacter).stream()
                    .sorted()
                    .toList();
        }

        TennisPlayer player1 = new TennisPlayer(playerIds.get(0), TennisScore.ZERO);
        TennisPlayer player2 = new TennisPlayer(playerIds.get(1), TennisScore.ZERO);
        return new TennisGameState(null, player1, player2);
    }
}
