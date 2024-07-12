package kata.tennis;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import kata.tennis.services.exceptions.GameAlreadyFinishedException;
import kata.tennis.services.exceptions.UnsupportedPlayersCountException;

public class GameRunnerTests {

    GameRunner gameRunner;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        gameRunner = new GameRunner();
    }

    @Test
    public void test_single_player_A_win() {
        // given
        String gameHistory = "AAAA";
        String expectedOutput = """
                A > Player A: 15 / Player B: 0
                A > Player A: 30 / Player B: 0
                A > Player A: 40 / Player B: 0
                A > Player A wins the game
                """;
        // when
        gameRunner.printGameScoresFromHistory(gameHistory);
        // then
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void test_single_player_A_without_win() {
        // given
        String gameHistory = "AAA";
        String expectedOutput = """
                A > Player A: 15 / Player B: 0
                A > Player A: 30 / Player B: 0
                A > Player A: 40 / Player B: 0
                """;
        // when
        gameRunner.printGameScoresFromHistory(gameHistory);
        // then
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void test_single_player_B_win() {
        // given
        String gameHistory = "BBBB";
        String expectedOutput = """
                B > Player B: 15 / Player C: 0
                B > Player B: 30 / Player C: 0
                B > Player B: 40 / Player C: 0
                B > Player B wins the game
                """;
        // when
        gameRunner.printGameScoresFromHistory(gameHistory);
        // then
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void test_single_player_B_without_win() {
        // given
        String gameHistory = "BBB";
        String expectedOutput = """
                B > Player B: 15 / Player C: 0
                B > Player B: 30 / Player C: 0
                B > Player B: 40 / Player C: 0
                """;
        // when
        gameRunner.printGameScoresFromHistory(gameHistory);
        // then
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void test_both_players_without_win() {
        // given
        String gameHistory = "AABA";
        String expectedOutput = """
                A > Player A: 15 / Player B: 0
                A > Player A: 30 / Player B: 0
                B > Player A: 30 / Player B: 15
                A > Player A: 40 / Player B: 15
                """;
        // when
        gameRunner.printGameScoresFromHistory(gameHistory);
        // then
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void test_both_players_with_DEUCE() {
        // given
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
        gameRunner.printGameScoresFromHistory(gameHistory);
        // then
        originalOut.println(outContent.toString());
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void test_player_A_win() {
        // given
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
        gameRunner.printGameScoresFromHistory(gameHistory);
        // then
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void test_player_B_gain_ADVANTAGE() {
        // given
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
        gameRunner.printGameScoresFromHistory(gameHistory);
        // then
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void test_player_B_gain_ADVANTAGE_and_win() {
        // given
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
        gameRunner.printGameScoresFromHistory(gameHistory);
        // then
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void test_player_A_gain_ADVANTAGE() {
        // given
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
        gameRunner.printGameScoresFromHistory(gameHistory);
        // then
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void test_player_A_gain_ADVANTAGE_and_win() {
        // given
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
        gameRunner.printGameScoresFromHistory(gameHistory);
        // then
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void test_player_B_lose_ADVANTAGE() {
        // given
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
        gameRunner.printGameScoresFromHistory(gameHistory);
        // then
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void test_player_B_lose_ADVANTAGE_A_gain_advantage() {
        // given
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
        gameRunner.printGameScoresFromHistory(gameHistory);
        // then
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void test_player_B_lose_ADVANTAGE_A_gain_advantage_and_win() {
        // given
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
        gameRunner.printGameScoresFromHistory(gameHistory);
        // then
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void test_complex_scenario() {
        // given
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
        gameRunner.printGameScoresFromHistory(gameHistory);
        // then
        assertEquals(expectedOutput, outContent.toString());
    }

    // --------- failing cases ----------//

    @Test
    public void test_UnsupportedPlayersCountException_more_than_two() {
        // given
        String gameHistory = "ABC";
        // when
        Exception thrown = assertThrows(UnsupportedPlayersCountException.class, () -> {
            gameRunner.printGameScoresFromHistory(gameHistory);
        });
        // then
        assertInstanceOf(UnsupportedPlayersCountException.class, thrown);
    }

    @Test
    public void test_UnsupportedPlayersCountException_empty_history() {
        // given
        String gameHistory = "";
        // when
        Exception thrown = assertThrows(UnsupportedPlayersCountException.class, () -> {
            gameRunner.printGameScoresFromHistory(gameHistory);
        });
        // then
        assertInstanceOf(UnsupportedPlayersCountException.class, thrown);
    }

    @Test
    public void test_GameAlreadyFinishedException() {
        // given
        String gameHistory = "AAAAB";
        // when
        Exception thrown = assertThrows(GameAlreadyFinishedException.class, () -> {
            gameRunner.printGameScoresFromHistory(gameHistory);
        });
        // then
        assertInstanceOf(GameAlreadyFinishedException.class, thrown);
    }
}
