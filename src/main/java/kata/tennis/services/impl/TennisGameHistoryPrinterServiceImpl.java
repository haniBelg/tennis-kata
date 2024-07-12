package kata.tennis.services.impl;

import java.util.List;
import java.util.function.Consumer;

import kata.tennis.domain.TennisPlayer;
import kata.tennis.domain.state.TennisGameState;
import kata.tennis.services.TennisGameHistoryPrinterService;

/**
 * Implementation of {@link TennisGameHistoryPrinterService} that prints the
 * score from a tennis game history.
 * <p>
 * This class ensures adherence to the Single Responsibility Principle (SRP).
 * It separates the responsibility of printing game history scores from
 * processing game states,
 * thereby improving maintainability and readability of the code.
 * </p>
 */
public class TennisGameHistoryPrinterServiceImpl implements TennisGameHistoryPrinterService {
    private final Consumer<String> printer;

    /**
     * Constructs a new instance of {@code TennisGameHistoryPrinterServiceImpl}.
     * Sets the default printer to {@link System#out}.
     */
    public TennisGameHistoryPrinterServiceImpl() {
        this.printer = System.out::println;
    }

    /**
     * Processes the provided list of tennis game states and prints the score or
     * the game winner as appropriate.
     * <p>
     * This implementation iterates through the list of game states and prints the
     * score for each state unless a winner is identified, in which case it prints
     * the winner of the game.
     * </p>
     * 
     * @param tennisGameStates the list of game states representing the history of
     *                         the tennis game.
     */
    @Override
    public void printScoreFromGameStates(List<TennisGameState> tennisGameStates) {
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
