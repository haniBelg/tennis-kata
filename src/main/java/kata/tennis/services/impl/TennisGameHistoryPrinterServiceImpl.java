package kata.tennis.services.impl;

import java.util.List;
import java.util.function.Consumer;

import kata.tennis.domain.TennisPlayer;
import kata.tennis.domain.state.TennisGameState;
import kata.tennis.services.TennisGameHistoryPrinterService;
import kata.tennis.services.TennisGameHistoryProcessorService;
import kata.tennis.services.exceptions.GameAlreadyFinishedException;
import kata.tennis.services.exceptions.UnsupportedPlayersCountException;

/**
 * Implementation of {@link TennisGameHistoryPrinterService} that prints the
 * score from a tennis game history.
 * <p>
 * This class applies the Decorator design pattern to ensure adherence to the
 * Single Responsibility Principle (SRP). It separates the responsibility of
 * printing game history scores from processing game states, thereby improving
 * maintainability and readability of the code.
 * </p>
 */
public class TennisGameHistoryPrinterServiceImpl implements TennisGameHistoryPrinterService {
    private final Consumer<String> printer;
    private final TennisGameHistoryProcessorService tennisGameHistoryProcessorService;

    /**
     * Constructs a new instance of {@code TennisGameHistoryPrinterServiceImpl}.
     * Initializes the {@link TennisGameHistoryProcessorService} to adhere to the
     * Dependency Inversion Principle (DIP),
     * where this service relies on the abstraction of
     * {@link TennisGameHistoryProcessorService}.
     * Sets the default printer to {@link System#out}.
     *
     * @param tennisGameHistoryProcessorService The service responsible for
     *                                          processing tennis game history.
     */
    public TennisGameHistoryPrinterServiceImpl(TennisGameHistoryProcessorService tennisGameHistoryProcessorService) {
        this.tennisGameHistoryProcessorService = tennisGameHistoryProcessorService;
        this.printer = System.out::println;
    }

    /**
     * Constructs a new instance of {@code TennisGameHistoryPrinterServiceImpl}.
     * Initializes the {@link TennisGameHistoryProcessorService} and sets the
     * default printer to {@link System#out}.
     */
    public TennisGameHistoryPrinterServiceImpl() {
        this.tennisGameHistoryProcessorService = new TennisGameHistoryProcessorServiceImpl();
        this.printer = System.out::println;
    }

    /**
     * {@inheritDoc}
     * <p>
     * This implementation processes the game history to generate states and then
     * prints the score or the game winner as appropriate.
     * </p>
     * 
     * @param gameHistory the history of the tennis game represented as a string.
     * @throws UnsupportedPlayersCountException if the game history indicates an
     *                                          unsupported number of players.
     * @throws GameAlreadyFinishedException     if the game history indicates that
     *                                          the game has already finished.
     */
    @Override
    public void printScoreFromGameHistory(String gameHistory) {
        List<TennisGameState> tennisGameStates = tennisGameHistoryProcessorService
                .generateGameStatesFromHistory(gameHistory);
        for (var lastGameState : tennisGameStates) {
            if (lastGameState.winner().isEmpty()) {
                print(lastGameState);
            } else {
                print(lastGameState.winner().get());
            }
        }
    }

    /**
     * Prints the winner of the game.
     * 
     * @param tennisPlayer the player who won the game.
     */
    private void print(TennisPlayer tennisPlayer) {
        printer.accept(String.format("%c > Player %c wins the game", tennisPlayer.id(), tennisPlayer.id()));
    }

    /**
     * Prints the current score state of the game.
     * 
     * @param lastGameState the last recorded state of the game.
     */
    private void print(TennisGameState lastGameState) {
        printer.accept(String.format(
                "%c > Player %c: %s / Player %c: %s",
                lastGameState.currentWinnerId(),
                lastGameState.player1().id(),
                lastGameState.player1().tennisScore().getLibelle(),
                lastGameState.player2().id(),
                lastGameState.player2().tennisScore().getLibelle()));
    }
}
