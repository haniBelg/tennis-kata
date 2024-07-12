package kata.tennis.services.impl;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import kata.tennis.domain.TennisPlayer;
import kata.tennis.domain.state.TennisGameState;
import kata.tennis.services.TennisGameHistoryPrinterService;
import kata.tennis.services.exceptions.GameAlreadyFinishedException;
import kata.tennis.services.exceptions.UnsupportedPlayersCountException;

/**
 * Implementation of {@link TennisGameHistoryPrinterService} that prints the
 * score from a tennis game history.
 */
public class TennisGameHistoryPrinterServiceImpl implements TennisGameHistoryPrinterService {
    private final Consumer<String> printer;
    private final TennisGameHistoryProcessorServiceImpl tennisGameHistoryProcessorService;

    /**
     * Constructs a new instance of {@code TennisGameHistoryPrinterServiceImpl}.
     * Initializes the {@link TennisGameHistoryProcessorServiceImpl} and sets the
     * default printer to {@link System#out}.
     */
    public TennisGameHistoryPrinterServiceImpl() {
        this.tennisGameHistoryProcessorService = new TennisGameHistoryProcessorServiceImpl();
        this.printer = System.out::println;
    }

    /**
     * {@inheritDoc}
     * <p>
     * This implementation filters game states with a non-null current winner ID,
     * and prints the score or the game winner as appropriate.
     * </p>
     * 
     * @throws UnsupportedPlayersCountException if the game history indicates an
     *                                          unsupported number of players.
     * @throws GameAlreadyFinishedException     if the game history indicates that
     *                                          the game has already finished.
     */
    @Override
    public void printScoreFromGameHistory(String gameHistory) {
        List<TennisGameState> tennisGameStates = tennisGameHistoryProcessorService
                .generateGameStatesFromHistory(gameHistory).stream()
                .filter(gameState -> Objects.nonNull(gameState.currentWinnerId())).toList();
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
