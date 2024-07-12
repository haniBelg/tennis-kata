package kata.tennis.services.impl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import kata.tennis.domain.TennisPlayer;
import kata.tennis.domain.TennisScore;
import kata.tennis.domain.state.TennisGameState;
import kata.tennis.services.TennisGameHistoryProcessorService;

public class TennisGameHistoryProcessorServiceImplTests {
    TennisGameHistoryProcessorService service = new TennisGameHistoryProcessorServiceImpl();

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

    @Test
    public void test_single_player_A_win() {
        // having
        String gameHistory = "AAAA";
        List<TennisGameState> expected = List.of(
                state(null, TennisScore.ZERO, TennisScore.ZERO),
                state('A', TennisScore.FIFTEEN, TennisScore.ZERO),
                state('A', TennisScore.THIRTY, TennisScore.ZERO),
                state('A', TennisScore.FORTY, TennisScore.ZERO),
                state('A', TennisScore.WIN, TennisScore.LOSE));
        // when
        List<TennisGameState> gameStates = service.generateGameStatesFromHistory(gameHistory);
        // then
        assertEquals(expected, gameStates);
    }

    @Test
    public void test_single_player_A_without_win() {
        // having
        String gameHistory = "AAA";
        List<TennisGameState> expected = List.of(
                state(null, TennisScore.ZERO, TennisScore.ZERO),
                state('A', TennisScore.FIFTEEN, TennisScore.ZERO),
                state('A', TennisScore.THIRTY, TennisScore.ZERO),
                state('A', TennisScore.FORTY, TennisScore.ZERO));
        // when
        List<TennisGameState> gameStates = service.generateGameStatesFromHistory(gameHistory);
        // then
        assertEquals(expected, gameStates);
    }

    @Test
    public void test_single_player_B_win() {
        // having
        String gameHistory = "BBBB";
        List<TennisGameState> expected = List.of(
                state(null, 'B', TennisScore.ZERO, 'C', TennisScore.ZERO),
                state('B', 'B', TennisScore.FIFTEEN, 'C', TennisScore.ZERO),
                state('B', 'B', TennisScore.THIRTY, 'C', TennisScore.ZERO),
                state('B', 'B', TennisScore.FORTY, 'C', TennisScore.ZERO),
                state('B', 'B', TennisScore.WIN, 'C', TennisScore.LOSE));
        // when
        List<TennisGameState> gameStates = service.generateGameStatesFromHistory(gameHistory);
        // then
        assertEquals(expected, gameStates);
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
    List<TennisGameState> expected = List.of(
                state(null, 'B', TennisScore.ZERO, 'C', TennisScore.ZERO),
                state('B', 'B', TennisScore.FIFTEEN, 'C', TennisScore.ZERO),
                state('B', 'B', TennisScore.THIRTY, 'C', TennisScore.ZERO),
                state('B', 'B', TennisScore.FORTY, 'C', TennisScore.ZERO));
        // when
        List<TennisGameState> gameStates = service.generateGameStatesFromHistory(gameHistory);
        // then
        assertEquals(expected, gameStates);
    }

    @Test
    public void test_both_players_without_win() {
        // having
        String gameHistory = "AABA";
        List<TennisGameState> expected = List.of(
                state(null, TennisScore.ZERO, TennisScore.ZERO),
                state('A', TennisScore.FIFTEEN, TennisScore.ZERO),
                state('A', TennisScore.THIRTY, TennisScore.ZERO),
                state('B', TennisScore.THIRTY, TennisScore.FIFTEEN),
                state('A', TennisScore.FORTY, TennisScore.FIFTEEN));
        // when
        List<TennisGameState> gameStates = service.generateGameStatesFromHistory(gameHistory);
        // then
        assertEquals(expected, gameStates);
    }

    @Test
    public void test_both_players_with_DEUCE() {
        // having
        String gameHistory = "AABABB";
        List<TennisGameState> expected = List.of(
                state(null, TennisScore.ZERO, TennisScore.ZERO),
                state('A', TennisScore.FIFTEEN, TennisScore.ZERO),
                state('A', TennisScore.THIRTY, TennisScore.ZERO),
                state('B', TennisScore.THIRTY, TennisScore.FIFTEEN),
                state('A', TennisScore.FORTY, TennisScore.FIFTEEN),
                state('B', TennisScore.FORTY, TennisScore.THIRTY),
                state('B', TennisScore.DEUCE, TennisScore.DEUCE));
        // when
        List<TennisGameState> gameStates = service.generateGameStatesFromHistory(gameHistory);
        // then
        assertEquals(expected, gameStates);
    }

    @Test
    public void test_player_A_win() {
        // having
        String gameHistory = "AABABA";
        List<TennisGameState> expected = List.of(
                state(null, TennisScore.ZERO, TennisScore.ZERO),
                state('A', TennisScore.FIFTEEN, TennisScore.ZERO),
                state('A', TennisScore.THIRTY, TennisScore.ZERO),
                state('B', TennisScore.THIRTY, TennisScore.FIFTEEN),
                state('A', TennisScore.FORTY, TennisScore.FIFTEEN),
                state('B', TennisScore.FORTY, TennisScore.THIRTY),
                state('A', TennisScore.WIN, TennisScore.LOSE));
        // when
        List<TennisGameState> gameStates = service.generateGameStatesFromHistory(gameHistory);
        // then
        assertEquals(expected, gameStates);
    }

    @Test
    public void test_player_B_gain_ADVANTAGE() {
        // having
        String gameHistory = "AABABBB";
        List<TennisGameState> expected = List.of(
                state(null, TennisScore.ZERO, TennisScore.ZERO),
                state('A', TennisScore.FIFTEEN, TennisScore.ZERO),
                state('A', TennisScore.THIRTY, TennisScore.ZERO),
                state('B', TennisScore.THIRTY, TennisScore.FIFTEEN),
                state('A', TennisScore.FORTY, TennisScore.FIFTEEN),
                state('B', TennisScore.FORTY, TennisScore.THIRTY),
                state('B', TennisScore.DEUCE, TennisScore.DEUCE),
                state('B', TennisScore.FORTY, TennisScore.ADVANTAGE));
        // when
        List<TennisGameState> gameStates = service.generateGameStatesFromHistory(gameHistory);
        // then
        assertEquals(expected, gameStates);
    }

    @Test
    public void test_player_B_gain_ADVANTAGE_and_win() {
        // having
        String gameHistory = "AABABBBB";
        List<TennisGameState> expected = List.of(
                state(null, TennisScore.ZERO, TennisScore.ZERO),
                state('A', TennisScore.FIFTEEN, TennisScore.ZERO),
                state('A', TennisScore.THIRTY, TennisScore.ZERO),
                state('B', TennisScore.THIRTY, TennisScore.FIFTEEN),
                state('A', TennisScore.FORTY, TennisScore.FIFTEEN),
                state('B', TennisScore.FORTY, TennisScore.THIRTY),
                state('B', TennisScore.DEUCE, TennisScore.DEUCE),
                state('B', TennisScore.FORTY, TennisScore.ADVANTAGE),
                state('B', TennisScore.LOSE, TennisScore.WIN));
        // when
        List<TennisGameState> gameStates = service.generateGameStatesFromHistory(gameHistory);
        // then
        assertEquals(expected, gameStates);
    }

    @Test
    public void test_player_A_gain_ADVANTAGE() {
        // having
        String gameHistory = "AABABBA";
        List<TennisGameState> expected = List.of(
                state(null, TennisScore.ZERO, TennisScore.ZERO),
                state('A', TennisScore.FIFTEEN, TennisScore.ZERO),
                state('A', TennisScore.THIRTY, TennisScore.ZERO),
                state('B', TennisScore.THIRTY, TennisScore.FIFTEEN),
                state('A', TennisScore.FORTY, TennisScore.FIFTEEN),
                state('B', TennisScore.FORTY, TennisScore.THIRTY),
                state('B', TennisScore.DEUCE, TennisScore.DEUCE),
                state('A', TennisScore.ADVANTAGE, TennisScore.FORTY));
        // when
        List<TennisGameState> gameStates = service.generateGameStatesFromHistory(gameHistory);
        // then
        assertEquals(expected, gameStates);
    }

    @Test
    public void test_player_A_gain_ADVANTAGE_and_win() {
        // having
        String gameHistory = "AABABBAA";
        List<TennisGameState> expected = List.of(
                state(null, TennisScore.ZERO, TennisScore.ZERO),
                state('A', TennisScore.FIFTEEN, TennisScore.ZERO),
                state('A', TennisScore.THIRTY, TennisScore.ZERO),
                state('B', TennisScore.THIRTY, TennisScore.FIFTEEN),
                state('A', TennisScore.FORTY, TennisScore.FIFTEEN),
                state('B', TennisScore.FORTY, TennisScore.THIRTY),
                state('B', TennisScore.DEUCE, TennisScore.DEUCE),
                state('A', TennisScore.ADVANTAGE, TennisScore.FORTY),
                state('A', TennisScore.WIN, TennisScore.LOSE));
        // when
        List<TennisGameState> gameStates = service.generateGameStatesFromHistory(gameHistory);
        // then
        assertEquals(expected, gameStates);
    }

    @Test
    public void test_player_B_lose_ADVANTAGE() {
        // having
        String gameHistory = "AABABBBA";
        List<TennisGameState> expected = List.of(
                state(null, TennisScore.ZERO, TennisScore.ZERO),
                state('A', TennisScore.FIFTEEN, TennisScore.ZERO),
                state('A', TennisScore.THIRTY, TennisScore.ZERO),
                state('B', TennisScore.THIRTY, TennisScore.FIFTEEN),
                state('A', TennisScore.FORTY, TennisScore.FIFTEEN),
                state('B', TennisScore.FORTY, TennisScore.THIRTY),
                state('B', TennisScore.DEUCE, TennisScore.DEUCE),
                state('B', TennisScore.FORTY, TennisScore.ADVANTAGE),
                state('A', TennisScore.DEUCE, TennisScore.DEUCE));
        // when
        List<TennisGameState> gameStates = service.generateGameStatesFromHistory(gameHistory);
        // then
        assertEquals(expected, gameStates);
    }

    @Test
    public void test_player_B_lose_ADVANTAGE_A_gain_advantage() {
        // having
        String gameHistory = "AABABBBAA";
        List<TennisGameState> expected = List.of(
                state(null, TennisScore.ZERO, TennisScore.ZERO),
                state('A', TennisScore.FIFTEEN, TennisScore.ZERO),
                state('A', TennisScore.THIRTY, TennisScore.ZERO),
                state('B', TennisScore.THIRTY, TennisScore.FIFTEEN),
                state('A', TennisScore.FORTY, TennisScore.FIFTEEN),
                state('B', TennisScore.FORTY, TennisScore.THIRTY),
                state('B', TennisScore.DEUCE, TennisScore.DEUCE),
                state('B', TennisScore.FORTY, TennisScore.ADVANTAGE),
                state('A', TennisScore.DEUCE, TennisScore.DEUCE),
                state('A', TennisScore.ADVANTAGE, TennisScore.FORTY));
        // when
        List<TennisGameState> gameStates = service.generateGameStatesFromHistory(gameHistory);
        // then
        assertEquals(expected, gameStates);
    }

    @Test
    public void test_player_B_lose_ADVANTAGE_A_gain_advantage_and_win() {
        // having
        String gameHistory = "AABABBBAAA";
        List<TennisGameState> expected = List.of(
                state(null, TennisScore.ZERO, TennisScore.ZERO),
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
        // when
        List<TennisGameState> gameStates = service.generateGameStatesFromHistory(gameHistory);
        // then
        assertEquals(expected, gameStates);
    }

    @Test
    public void test_complex_scenario() {
        // having
        String gameHistory = "ABBAABABBAABBB";
        List<TennisGameState> expected = List.of(
                state(null, TennisScore.ZERO, TennisScore.ZERO),
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
        // when
        List<TennisGameState> gameStates = service.generateGameStatesFromHistory(gameHistory);
        // then
        assertEquals(expected, gameStates);
    }
}
