package kata.tennis.services.impl;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import kata.tennis.domain.TennisPlayer;
import kata.tennis.domain.state.TennisGameState;
import kata.tennis.services.TennisGameHistoryPrinterService;

public class TennisGameHistoryPrinterServiceImpl implements TennisGameHistoryPrinterService {
    private final Consumer<String> printer;
    private final TennisGameHistoryProcessorServiceImpl tennisGameHistoryProcessorService;

    public TennisGameHistoryPrinterServiceImpl() {
        this.tennisGameHistoryProcessorService = new TennisGameHistoryProcessorServiceImpl();
        this.printer = System.out::println;
    }

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

    private void print(TennisPlayer tennisPlayer) {
        printer.accept(String.format("%c > Player %c wins the game", tennisPlayer.id(), tennisPlayer.id()));
    }

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
