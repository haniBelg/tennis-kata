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

/**
 * Implementation of {@link TennisGameHistoryProcessorService} that processes
 * the history of a tennis game
 * and generates a sequence of game states.
 */
public class TennisGameHistoryProcessorServiceImpl implements TennisGameHistoryProcessorService {
    private final TennisGameStateService tennisGameStateService;

    /**
     * Constructs a new instance of {@code TennisGameHistoryProcessorServiceImpl}.
     * Initializes the {@link TennisGameStateService} to adhere to the Dependency
     * Inversion Principle (DIP),
     * where this service relies on the abstraction of
     * {@link TennisGameStateService}.
     *
     * @param tennisGameStateService The service responsible for managing the state
     *                               of a tennis game.
     */
    public TennisGameHistoryProcessorServiceImpl(TennisGameStateService tennisGameStateService) {
        this.tennisGameStateService = tennisGameStateService;
    }

    /**
     * Constructs a new instance of {@code TennisGameHistoryProcessorServiceImpl}.
     * Initializes the {@link TennisGameStateService}.
     */
    public TennisGameHistoryProcessorServiceImpl() {
        this.tennisGameStateService = new TennisGameStateServiceImpl();
    }

    /**
     * {@inheritDoc}
     * <p>
     * This implementation generates a sequence of game states from the game history
     * string.
     * </p>
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
    @Override
    public List<TennisGameState> generateGameStatesFromHistory(String gameHistory)
            throws UnsupportedPlayersCountException, GameAlreadyFinishedException {
        List<TennisGameState> states = new ArrayList<>();
        TennisGameState initialGameState = generateInitialGameStateFromHistory(gameHistory);
        TennisGameState lastGameState = initialGameState;
        for (var currentWinnerId : gameHistory.toCharArray()) {
            lastGameState = tennisGameStateService.getNextGameState(lastGameState, currentWinnerId);
            states.add(lastGameState);
        }
        return states;
    }

    /**
     * Generates the initial state of the game from the game history string.
     * 
     * @param gameHistory the history of the game in a specific format that will be
     *                    processed
     *                    to generate the initial game state.
     * @return the initial {@link TennisGameState} representing the starting state
     *         of the game.
     * @throws UnsupportedPlayersCountException if the game history indicates an
     *                                          unsupported number of players.
     */
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
