package kata.tennis.services.impl;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import kata.tennis.services.TennisGameHistoryPrinterService;
import kata.tennis.services.exceptions.GameAlreadyFinishedException;
import kata.tennis.services.exceptions.UnsupportedPlayersCountException;

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
        // having
        String gameHistory = "AAAA";
        String expectedOutput = """
                A > Player A: 15 / Player B: 0
                A > Player A: 30 / Player B: 0
                A > Player A: 40 / Player B: 0
                A > Player A wins the game
                """;
        // when
        tennisGameHistoryPrinterService.printScoreFromGameHistory(gameHistory);
        // then
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void test_single_player_A_without_win() {
        // having
        String gameHistory = "AAA";
        String expectedOutput = """
                A > Player A: 15 / Player B: 0
                A > Player A: 30 / Player B: 0
                A > Player A: 40 / Player B: 0
                """;
        // when
        tennisGameHistoryPrinterService.printScoreFromGameHistory(gameHistory);
        // then
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void test_single_player_B_win() {
        // having
        String gameHistory = "BBBB";
        String expectedOutput = """
                B > Player B: 15 / Player C: 0
                B > Player B: 30 / Player C: 0
                B > Player B: 40 / Player C: 0
                B > Player B wins the game
                """;
        // when
        tennisGameHistoryPrinterService.printScoreFromGameHistory(gameHistory);
        // then
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void test_single_player_B_without_win() {
        // having
        String gameHistory = "BBB";
        String expectedOutput = """
                B > Player B: 15 / Player C: 0
                B > Player B: 30 / Player C: 0
                B > Player B: 40 / Player C: 0
                """;
        // when
        tennisGameHistoryPrinterService.printScoreFromGameHistory(gameHistory);
        // then
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void test_both_players_without_win() {
        // having
        String gameHistory = "AABA";
        String expectedOutput = """
                A > Player A: 15 / Player B: 0
                A > Player A: 30 / Player B: 0
                B > Player A: 30 / Player B: 15
                A > Player A: 40 / Player B: 15
                """;
        // when
        tennisGameHistoryPrinterService.printScoreFromGameHistory(gameHistory);
        // then
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void test_both_players_with_DEUCE() {
        // having
        String gameHistory = "AABABB";
        String expectedOutput = """
                A > Player A: 15 / Player B: 0
                A > Player A: 30 / Player B: 0
                B > Player A: 30 / Player B: 15
                A > Player A: 40 / Player B: 15
                B > Player A: 40 / Player B: 30
                B > Player A: DEUCE / Player B: DEUCE
                """;
        // when
        tennisGameHistoryPrinterService.printScoreFromGameHistory(gameHistory);
        // then
        originalOut.println(outContent.toString());
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void test_player_A_win() {
        // having
        String gameHistory = "AABABA";
        String expectedOutput = """
                A > Player A: 15 / Player B: 0
                A > Player A: 30 / Player B: 0
                B > Player A: 30 / Player B: 15
                A > Player A: 40 / Player B: 15
                B > Player A: 40 / Player B: 30
                A > Player A wins the game
                """;
        // when
        tennisGameHistoryPrinterService.printScoreFromGameHistory(gameHistory);
        // then
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void test_player_B_gain_ADVANTAGE() {
        // having
        String gameHistory = "AABABBB";
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
        tennisGameHistoryPrinterService.printScoreFromGameHistory(gameHistory);
        // then
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void test_player_B_gain_ADVANTAGE_and_win() {
        // having
        String gameHistory = "AABABBBB";
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
        tennisGameHistoryPrinterService.printScoreFromGameHistory(gameHistory);
        // then
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void test_player_A_gain_ADVANTAGE() {
        // having
        String gameHistory = "AABABBA";
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
        tennisGameHistoryPrinterService.printScoreFromGameHistory(gameHistory);
        // then
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void test_player_A_gain_ADVANTAGE_and_win() {
        // having
        String gameHistory = "AABABBAA";
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
        tennisGameHistoryPrinterService.printScoreFromGameHistory(gameHistory);
        // then
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void test_player_B_lose_ADVANTAGE() {
        // having
        String gameHistory = "AABABBBA";
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
        tennisGameHistoryPrinterService.printScoreFromGameHistory(gameHistory);
        // then
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void test_player_B_lose_ADVANTAGE_A_gain_advantage() {
        // having
        String gameHistory = "AABABBBAA";
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
        tennisGameHistoryPrinterService.printScoreFromGameHistory(gameHistory);
        // then
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void test_player_B_lose_ADVANTAGE_A_gain_advantage_and_win() {
        // having
        String gameHistory = "AABABBBAAA";
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
        tennisGameHistoryPrinterService.printScoreFromGameHistory(gameHistory);
        // then
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void test_complex_scenario() {
        // having
        String gameHistory = "ABBAABABBAABBB";
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
        tennisGameHistoryPrinterService.printScoreFromGameHistory(gameHistory);
        // then
        assertEquals(expectedOutput, outContent.toString());
    }

    // --------- failing cases ----------//

    @Test
    public void test_UnsupportedPlayersCountException_more_than_two() {
        // having
        String gameHistory = "ABC";
        // when
        Exception thrown = assertThrows(UnsupportedPlayersCountException.class, () -> {
            tennisGameHistoryPrinterService.printScoreFromGameHistory(gameHistory);
        });
        // then
        assertInstanceOf(UnsupportedPlayersCountException.class, thrown);
    }

    @Test
    public void test_UnsupportedPlayersCountException_empty_history() {
        // having
        String gameHistory = "";
        // when
        Exception thrown = assertThrows(UnsupportedPlayersCountException.class, () -> {
            tennisGameHistoryPrinterService.printScoreFromGameHistory(gameHistory);
        });
        // then
        assertInstanceOf(UnsupportedPlayersCountException.class, thrown);
    }

    @Test
    public void test_GameAlreadyFinishedException() {
        // having
        String gameHistory = "AAAAB";
        // when
        Exception thrown = assertThrows(GameAlreadyFinishedException.class, () -> {
            tennisGameHistoryPrinterService.printScoreFromGameHistory(gameHistory);
        });
        // then
        assertInstanceOf(GameAlreadyFinishedException.class, thrown);
    }
}
