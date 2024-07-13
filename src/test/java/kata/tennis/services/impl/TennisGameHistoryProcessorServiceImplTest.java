package kata.tennis.services.impl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import kata.tennis.domain.TennisPlayer;
import kata.tennis.domain.TennisScore;
import kata.tennis.domain.state.TennisGameState;
import kata.tennis.services.TennisGameHistoryProcessorService;
import kata.tennis.services.exceptions.GameAlreadyFinishedException;
import kata.tennis.services.exceptions.UnsupportedPlayersCountException;

public class TennisGameHistoryProcessorServiceImplTest {

    // Tests are designed around the SOLID principle of Dependency Inversion,
    // focusing on the TennisGameHistoryPrinterService interface rather than its
    // specific implementation.
    // This approach ensures that tests remain stable against changes in the
    // interface, promoting robustness.
    TennisGameHistoryProcessorService service = new TennisGameHistoryProcessorServiceImpl();

    @Test
    public void test_single_player_A_win() {
        // given
        String gameHistory = "AAAA";
        List<TennisGameState> expected = List.of(
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
        // given
        String gameHistory = "AAA";
        List<TennisGameState> expected = List.of(
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
        // given
        String gameHistory = "BBBB";
        List<TennisGameState> expected = List.of(
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
        // given
        String gameHistory = "BBB";
        List<TennisGameState> expected = List.of(
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
        // given
        String gameHistory = "AABA";
        List<TennisGameState> expected = List.of(
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
        // given
        String gameHistory = "AABABB";
        List<TennisGameState> expected = List.of(
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
        // given
        String gameHistory = "AABABA";
        List<TennisGameState> expected = List.of(
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
        // given
        String gameHistory = "AABABBB";
        List<TennisGameState> expected = List.of(
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
        // given
        String gameHistory = "AABABBBB";
        List<TennisGameState> expected = List.of(
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
        // given
        String gameHistory = "AABABBA";
        List<TennisGameState> expected = List.of(
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
        // given
        String gameHistory = "AABABBAA";
        List<TennisGameState> expected = List.of(
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
        // given
        String gameHistory = "AABABBBA";
        List<TennisGameState> expected = List.of(
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
        // given
        String gameHistory = "AABABBBAA";
        List<TennisGameState> expected = List.of(
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
        // given
        String gameHistory = "AABABBBAAA";
        List<TennisGameState> expected = List.of(
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
        // given
        String gameHistory = "ABBAABABBAABBB";
        List<TennisGameState> expected = List.of(
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

    // --------- failing cases ----------//

    @Test
    public void test_UnsupportedPlayersCountException_more_than_two() {
        // given
        String gameHistory = "ABC";
        // when
        Exception thrown = assertThrows(UnsupportedPlayersCountException.class, () -> {
            List<TennisGameState> gameStates = service.generateGameStatesFromHistory(gameHistory);
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
            List<TennisGameState> gameStates = service.generateGameStatesFromHistory(gameHistory);
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
            List<TennisGameState> gameStates = service.generateGameStatesFromHistory(gameHistory);
        });
        // then
        assertInstanceOf(GameAlreadyFinishedException.class, thrown);
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
