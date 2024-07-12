package kata.tennis.services.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import kata.tennis.domain.TennisPlayer;
import kata.tennis.domain.TennisScore;
import kata.tennis.domain.state.TennisGameState;
import kata.tennis.services.TennisGameHistoryPrinterService;

public class TennisGameHistoryPrinterServiceImplTests {

    // Tests are designed around the SOLID principle of Dependency Inversion,
    // focusing on the TennisGameHistoryPrinterService interface rather than its
    // specific implementation.
    // This approach ensures that tests remain stable against changes in the
    // interface, promoting robustness.

    TennisGameHistoryPrinterService tennisGameHistoryPrinterService;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        tennisGameHistoryPrinterService = new TennisGameHistoryPrinterServiceImpl();
    }

    @Test
    public void test_single_player_A_win() {
        // given
        List<TennisGameState> states = List.of(
                state('A', TennisScore.FIFTEEN, TennisScore.ZERO),
                state('A', TennisScore.THIRTY, TennisScore.ZERO),
                state('A', TennisScore.FORTY, TennisScore.ZERO),
                state('A', TennisScore.WIN, TennisScore.LOSE));
        String expectedOutput = """
                A > Player A: 15 / Player B: 0
                A > Player A: 30 / Player B: 0
                A > Player A: 40 / Player B: 0
                A > Player A wins the game
                """;
        // when
        tennisGameHistoryPrinterService.printScoreFromGameStates(states);
        // then
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void test_single_player_A_without_win() {
        // given
        List<TennisGameState> states = List.of(
                state('A', TennisScore.FIFTEEN, TennisScore.ZERO),
                state('A', TennisScore.THIRTY, TennisScore.ZERO),
                state('A', TennisScore.FORTY, TennisScore.ZERO));
        String expectedOutput = """
                A > Player A: 15 / Player B: 0
                A > Player A: 30 / Player B: 0
                A > Player A: 40 / Player B: 0
                """;
        // when
        tennisGameHistoryPrinterService.printScoreFromGameStates(states);
        // then
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void test_single_player_B_win() {
        // given
        List<TennisGameState> states = List.of(
                state('B', 'B', TennisScore.FIFTEEN, 'C', TennisScore.ZERO),
                state('B', 'B', TennisScore.THIRTY, 'C', TennisScore.ZERO),
                state('B', 'B', TennisScore.FORTY, 'C', TennisScore.ZERO),
                state('B', 'B', TennisScore.WIN, 'C', TennisScore.LOSE));
        String expectedOutput = """
                B > Player B: 15 / Player C: 0
                B > Player B: 30 / Player C: 0
                B > Player B: 40 / Player C: 0
                B > Player B wins the game
                """;
        // when
        tennisGameHistoryPrinterService.printScoreFromGameStates(states);
        // then
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void test_single_player_B_without_win() {
        // given
        List<TennisGameState> states = List.of(
                state('B', 'B', TennisScore.FIFTEEN, 'C', TennisScore.ZERO),
                state('B', 'B', TennisScore.THIRTY, 'C', TennisScore.ZERO),
                state('B', 'B', TennisScore.FORTY, 'C', TennisScore.ZERO));
        String expectedOutput = """
                B > Player B: 15 / Player C: 0
                B > Player B: 30 / Player C: 0
                B > Player B: 40 / Player C: 0
                """;
        // when
        tennisGameHistoryPrinterService.printScoreFromGameStates(states);
        // then
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void test_both_players_without_win() {
        // given
        List<TennisGameState> states = List.of(
                state('A', TennisScore.FIFTEEN, TennisScore.ZERO),
                state('A', TennisScore.THIRTY, TennisScore.ZERO),
                state('B', TennisScore.THIRTY, TennisScore.FIFTEEN),
                state('A', TennisScore.FORTY, TennisScore.FIFTEEN));
        String expectedOutput = """
                A > Player A: 15 / Player B: 0
                A > Player A: 30 / Player B: 0
                B > Player A: 30 / Player B: 15
                A > Player A: 40 / Player B: 15
                """;
        // when
        tennisGameHistoryPrinterService.printScoreFromGameStates(states);
        // then
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void test_both_players_with_DEUCE() {
        // given
        List<TennisGameState> states = List.of(
                state('A', TennisScore.FIFTEEN, TennisScore.ZERO),
                state('A', TennisScore.THIRTY, TennisScore.ZERO),
                state('B', TennisScore.THIRTY, TennisScore.FIFTEEN),
                state('A', TennisScore.FORTY, TennisScore.FIFTEEN),
                state('B', TennisScore.FORTY, TennisScore.THIRTY),
                state('B', TennisScore.DEUCE, TennisScore.DEUCE));
        String expectedOutput = """
                A > Player A: 15 / Player B: 0
                A > Player A: 30 / Player B: 0
                B > Player A: 30 / Player B: 15
                A > Player A: 40 / Player B: 15
                B > Player A: 40 / Player B: 30
                B > Player A: DEUCE / Player B: DEUCE
                """;
        // when
        tennisGameHistoryPrinterService.printScoreFromGameStates(states);
        // then
        originalOut.println(outContent.toString());
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void test_player_A_win() {
        // given
        List<TennisGameState> states = List.of(
                state('A', TennisScore.FIFTEEN, TennisScore.ZERO),
                state('A', TennisScore.THIRTY, TennisScore.ZERO),
                state('B', TennisScore.THIRTY, TennisScore.FIFTEEN),
                state('A', TennisScore.FORTY, TennisScore.FIFTEEN),
                state('B', TennisScore.FORTY, TennisScore.THIRTY),
                state('A', TennisScore.WIN, TennisScore.LOSE));
        String expectedOutput = """
                A > Player A: 15 / Player B: 0
                A > Player A: 30 / Player B: 0
                B > Player A: 30 / Player B: 15
                A > Player A: 40 / Player B: 15
                B > Player A: 40 / Player B: 30
                A > Player A wins the game
                """;
        // when
        tennisGameHistoryPrinterService.printScoreFromGameStates(states);
        // then
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void test_player_B_gain_ADVANTAGE() {
        // given
        List<TennisGameState> states = List.of(
                state('A', TennisScore.FIFTEEN, TennisScore.ZERO),
                state('A', TennisScore.THIRTY, TennisScore.ZERO),
                state('B', TennisScore.THIRTY, TennisScore.FIFTEEN),
                state('A', TennisScore.FORTY, TennisScore.FIFTEEN),
                state('B', TennisScore.FORTY, TennisScore.THIRTY),
                state('B', TennisScore.DEUCE, TennisScore.DEUCE),
                state('B', TennisScore.FORTY, TennisScore.ADVANTAGE));
        String expectedOutput = """
                A > Player A: 15 / Player B: 0
                A > Player A: 30 / Player B: 0
                B > Player A: 30 / Player B: 15
                A > Player A: 40 / Player B: 15
                B > Player A: 40 / Player B: 30
                B > Player A: DEUCE / Player B: DEUCE
                B > Player A: 40 / Player B: ADVANTAGE
                """;
        // when
        tennisGameHistoryPrinterService.printScoreFromGameStates(states);
        // then
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void test_player_B_gain_ADVANTAGE_and_win() {
        // given
        List<TennisGameState> states = List.of(
                state('A', TennisScore.FIFTEEN, TennisScore.ZERO),
                state('A', TennisScore.THIRTY, TennisScore.ZERO),
                state('B', TennisScore.THIRTY, TennisScore.FIFTEEN),
                state('A', TennisScore.FORTY, TennisScore.FIFTEEN),
                state('B', TennisScore.FORTY, TennisScore.THIRTY),
                state('B', TennisScore.DEUCE, TennisScore.DEUCE),
                state('B', TennisScore.FORTY, TennisScore.ADVANTAGE),
                state('B', TennisScore.LOSE, TennisScore.WIN));
        String expectedOutput = """
                A > Player A: 15 / Player B: 0
                A > Player A: 30 / Player B: 0
                B > Player A: 30 / Player B: 15
                A > Player A: 40 / Player B: 15
                B > Player A: 40 / Player B: 30
                B > Player A: DEUCE / Player B: DEUCE
                B > Player A: 40 / Player B: ADVANTAGE
                B > Player B wins the game
                """;
        // when
        tennisGameHistoryPrinterService.printScoreFromGameStates(states);
        // then
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void test_player_A_gain_ADVANTAGE() {
        // given
        List<TennisGameState> states = List.of(
                state('A', TennisScore.FIFTEEN, TennisScore.ZERO),
                state('A', TennisScore.THIRTY, TennisScore.ZERO),
                state('B', TennisScore.THIRTY, TennisScore.FIFTEEN),
                state('A', TennisScore.FORTY, TennisScore.FIFTEEN),
                state('B', TennisScore.FORTY, TennisScore.THIRTY),
                state('B', TennisScore.DEUCE, TennisScore.DEUCE),
                state('A', TennisScore.ADVANTAGE, TennisScore.FORTY));
        String expectedOutput = """
                A > Player A: 15 / Player B: 0
                A > Player A: 30 / Player B: 0
                B > Player A: 30 / Player B: 15
                A > Player A: 40 / Player B: 15
                B > Player A: 40 / Player B: 30
                B > Player A: DEUCE / Player B: DEUCE
                A > Player A: ADVANTAGE / Player B: 40
                """;
        // when
        tennisGameHistoryPrinterService.printScoreFromGameStates(states);
        // then
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void test_player_A_gain_ADVANTAGE_and_win() {
        // given
        List<TennisGameState> states = List.of(
                state('A', TennisScore.FIFTEEN, TennisScore.ZERO),
                state('A', TennisScore.THIRTY, TennisScore.ZERO),
                state('B', TennisScore.THIRTY, TennisScore.FIFTEEN),
                state('A', TennisScore.FORTY, TennisScore.FIFTEEN),
                state('B', TennisScore.FORTY, TennisScore.THIRTY),
                state('B', TennisScore.DEUCE, TennisScore.DEUCE),
                state('A', TennisScore.ADVANTAGE, TennisScore.FORTY),
                state('A', TennisScore.WIN, TennisScore.LOSE));
        String expectedOutput = """
                A > Player A: 15 / Player B: 0
                A > Player A: 30 / Player B: 0
                B > Player A: 30 / Player B: 15
                A > Player A: 40 / Player B: 15
                B > Player A: 40 / Player B: 30
                B > Player A: DEUCE / Player B: DEUCE
                A > Player A: ADVANTAGE / Player B: 40
                A > Player A wins the game
                """;
        // when
        tennisGameHistoryPrinterService.printScoreFromGameStates(states);
        // then
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void test_player_B_lose_ADVANTAGE() {
        // given
        List<TennisGameState> states = List.of(
                state('A', TennisScore.FIFTEEN, TennisScore.ZERO),
                state('A', TennisScore.THIRTY, TennisScore.ZERO),
                state('B', TennisScore.THIRTY, TennisScore.FIFTEEN),
                state('A', TennisScore.FORTY, TennisScore.FIFTEEN),
                state('B', TennisScore.FORTY, TennisScore.THIRTY),
                state('B', TennisScore.DEUCE, TennisScore.DEUCE),
                state('B', TennisScore.FORTY, TennisScore.ADVANTAGE),
                state('A', TennisScore.DEUCE, TennisScore.DEUCE));
        String expectedOutput = """
                A > Player A: 15 / Player B: 0
                A > Player A: 30 / Player B: 0
                B > Player A: 30 / Player B: 15
                A > Player A: 40 / Player B: 15
                B > Player A: 40 / Player B: 30
                B > Player A: DEUCE / Player B: DEUCE
                B > Player A: 40 / Player B: ADVANTAGE
                A > Player A: DEUCE / Player B: DEUCE
                """;
        // when
        tennisGameHistoryPrinterService.printScoreFromGameStates(states);
        // then
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void test_player_B_lose_ADVANTAGE_A_gain_advantage() {
        // given
        List<TennisGameState> states = List.of(
                state('A', TennisScore.FIFTEEN, TennisScore.ZERO),
                state('A', TennisScore.THIRTY, TennisScore.ZERO),
                state('B', TennisScore.THIRTY, TennisScore.FIFTEEN),
                state('A', TennisScore.FORTY, TennisScore.FIFTEEN),
                state('B', TennisScore.FORTY, TennisScore.THIRTY),
                state('B', TennisScore.DEUCE, TennisScore.DEUCE),
                state('B', TennisScore.FORTY, TennisScore.ADVANTAGE),
                state('A', TennisScore.DEUCE, TennisScore.DEUCE),
                state('A', TennisScore.ADVANTAGE, TennisScore.FORTY));
        String expectedOutput = """
                A > Player A: 15 / Player B: 0
                A > Player A: 30 / Player B: 0
                B > Player A: 30 / Player B: 15
                A > Player A: 40 / Player B: 15
                B > Player A: 40 / Player B: 30
                B > Player A: DEUCE / Player B: DEUCE
                B > Player A: 40 / Player B: ADVANTAGE
                A > Player A: DEUCE / Player B: DEUCE
                A > Player A: ADVANTAGE / Player B: 40
                """;
        // when
        tennisGameHistoryPrinterService.printScoreFromGameStates(states);
        // then
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void test_player_B_lose_ADVANTAGE_A_gain_advantage_and_win() {
        // given
        List<TennisGameState> states = List.of(
                state('A', TennisScore.FIFTEEN, TennisScore.ZERO),
                state('A', TennisScore.THIRTY, TennisScore.ZERO),
                state('B', TennisScore.THIRTY, TennisScore.FIFTEEN),
                state('A', TennisScore.FORTY, TennisScore.FIFTEEN),
                state('B', TennisScore.FORTY, TennisScore.THIRTY),
                state('B', TennisScore.DEUCE, TennisScore.DEUCE),
                state('B', TennisScore.FORTY, TennisScore.ADVANTAGE),
                state('A', TennisScore.DEUCE, TennisScore.DEUCE),
                state('A', TennisScore.ADVANTAGE, TennisScore.FORTY),
                state('A', TennisScore.WIN, TennisScore.LOSE));
        String expectedOutput = """
                A > Player A: 15 / Player B: 0
                A > Player A: 30 / Player B: 0
                B > Player A: 30 / Player B: 15
                A > Player A: 40 / Player B: 15
                B > Player A: 40 / Player B: 30
                B > Player A: DEUCE / Player B: DEUCE
                B > Player A: 40 / Player B: ADVANTAGE
                A > Player A: DEUCE / Player B: DEUCE
                A > Player A: ADVANTAGE / Player B: 40
                A > Player A wins the game
                """;
        // when
        tennisGameHistoryPrinterService.printScoreFromGameStates(states);
        // then
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void test_complex_scenario() {
        // given
        List<TennisGameState> states = List.of(
                state('A', TennisScore.FIFTEEN, TennisScore.ZERO),
                state('B', TennisScore.FIFTEEN, TennisScore.FIFTEEN),
                state('B', TennisScore.FIFTEEN, TennisScore.THIRTY),
                state('A', TennisScore.THIRTY, TennisScore.THIRTY),
                state('A', TennisScore.FORTY, TennisScore.THIRTY),
                state('B', TennisScore.DEUCE, TennisScore.DEUCE),
                state('A', TennisScore.ADVANTAGE, TennisScore.FORTY),
                state('B', TennisScore.DEUCE, TennisScore.DEUCE),
                state('B', TennisScore.FORTY, TennisScore.ADVANTAGE),
                state('A', TennisScore.DEUCE, TennisScore.DEUCE),
                state('A', TennisScore.ADVANTAGE, TennisScore.FORTY),
                state('B', TennisScore.DEUCE, TennisScore.DEUCE),
                state('B', TennisScore.FORTY, TennisScore.ADVANTAGE),
                state('B', TennisScore.LOSE, TennisScore.WIN));
        String expectedOutput = """
                A > Player A: 15 / Player B: 0
                B > Player A: 15 / Player B: 15
                B > Player A: 15 / Player B: 30
                A > Player A: 30 / Player B: 30
                A > Player A: 40 / Player B: 30
                B > Player A: DEUCE / Player B: DEUCE
                A > Player A: ADVANTAGE / Player B: 40
                B > Player A: DEUCE / Player B: DEUCE
                B > Player A: 40 / Player B: ADVANTAGE
                A > Player A: DEUCE / Player B: DEUCE
                A > Player A: ADVANTAGE / Player B: 40
                B > Player A: DEUCE / Player B: DEUCE
                B > Player A: 40 / Player B: ADVANTAGE
                B > Player B wins the game
                """;
        // when
        tennisGameHistoryPrinterService.printScoreFromGameStates(states);
        // then
        assertEquals(expectedOutput, outContent.toString());
    }

    // helpers to create expected values easily
    private TennisGameState state(Character currentWinnerId, TennisScore aScore, TennisScore bScore) {
        return new TennisGameState(currentWinnerId,
                new TennisPlayer('A', aScore),
                new TennisPlayer('B', bScore));
    }

    private TennisGameState state(
            Character currentWinnerId,
            Character id1,
            TennisScore aScore,
            Character id2,
            TennisScore bScore) {
        return new TennisGameState(currentWinnerId,
                new TennisPlayer(id1, aScore),
                new TennisPlayer(id2, bScore));
    }
}
