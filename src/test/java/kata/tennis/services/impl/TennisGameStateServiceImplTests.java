package kata.tennis.services.impl;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import kata.tennis.domain.TennisPlayer;
import kata.tennis.domain.TennisScore;
import kata.tennis.domain.state.TennisGameState;
import kata.tennis.services.TennisGameStateService;

public class TennisGameStateServiceImplTests {

        // Tests are designed around the SOLID principle of Dependency Inversion,
        // focusing on the TennisGameHistoryPrinterService interface rather than its
        // specific implementation.
        // This approach ensures that tests remain stable against changes in the
        // interface, promoting robustness.
        TennisGameStateService service = new TennisGameStateServiceImpl();

        @ParameterizedTest
        @MethodSource("provideScoreStates")
        void test_Game_State_Mutations_For_A(
                        // given
                        TennisScore aTennisScore,
                        TennisScore bTennisScore,
                        TennisScore aExpectedScore,
                        TennisScore bExpectedScore) {
                Character currentPointWinner = 'A';
                TennisPlayer player1 = new TennisPlayer('A', aTennisScore);
                TennisPlayer player2 = new TennisPlayer('B', bTennisScore);
                TennisGameState lastGameState = new TennisGameState(null, player1, player2);

                TennisPlayer aExpectedPlayer = new TennisPlayer('A', aExpectedScore);
                TennisPlayer bExpectedPlayer = new TennisPlayer('B', bExpectedScore);

                TennisGameState expectedGameState = new TennisGameState(currentPointWinner, aExpectedPlayer,
                                bExpectedPlayer);

                // when
                TennisGameState newGameState = service.getNextGameState(lastGameState, currentPointWinner);
                // then
                assertEquals(expectedGameState, newGameState);
        }

        @ParameterizedTest
        @MethodSource("provideScoreStates")
        void test_Game_State_Mutations_For_B(
                        // given
                        TennisScore bTennisScore,
                        TennisScore aTennisScore,
                        TennisScore bExpectedScore,
                        TennisScore aExpectedScore) {
                Character currentPointWinner = 'B';
                TennisPlayer player1 = new TennisPlayer('A', aTennisScore);
                TennisPlayer player2 = new TennisPlayer('B', bTennisScore);
                TennisGameState lastGameState = new TennisGameState(null, player1, player2);

                TennisPlayer aExpectedPlayer = new TennisPlayer('A', aExpectedScore);
                TennisPlayer bExpectedPlayer = new TennisPlayer('B', bExpectedScore);

                TennisGameState expectedGameState = new TennisGameState(currentPointWinner, aExpectedPlayer,
                                bExpectedPlayer);

                // when
                TennisGameState newGameState = service.getNextGameState(lastGameState, currentPointWinner);
                // then
                assertEquals(expectedGameState, newGameState);
        }

        static Stream<Arguments> provideScoreStates() {
                return Stream.of(
                                Arguments.of(TennisScore.ZERO, TennisScore.ZERO,
                                                TennisScore.FIFTEEN, TennisScore.ZERO),
                                Arguments.of(TennisScore.FIFTEEN, TennisScore.ZERO,
                                                TennisScore.THIRTY, TennisScore.ZERO),
                                Arguments.of(TennisScore.THIRTY, TennisScore.ZERO,
                                                TennisScore.FORTY, TennisScore.ZERO),
                                Arguments.of(TennisScore.FORTY, TennisScore.ZERO,
                                                TennisScore.WIN, TennisScore.LOSE),

                                Arguments.of(TennisScore.ZERO, TennisScore.FIFTEEN,
                                                TennisScore.FIFTEEN, TennisScore.FIFTEEN),
                                Arguments.of(TennisScore.FIFTEEN, TennisScore.FIFTEEN,
                                                TennisScore.THIRTY, TennisScore.FIFTEEN),
                                Arguments.of(TennisScore.THIRTY, TennisScore.FIFTEEN,
                                                TennisScore.FORTY, TennisScore.FIFTEEN),
                                Arguments.of(TennisScore.FORTY, TennisScore.FIFTEEN,
                                                TennisScore.WIN, TennisScore.LOSE),

                                Arguments.of(TennisScore.ZERO, TennisScore.THIRTY,
                                                TennisScore.FIFTEEN, TennisScore.THIRTY),
                                Arguments.of(TennisScore.FIFTEEN, TennisScore.THIRTY,
                                                TennisScore.THIRTY, TennisScore.THIRTY),
                                Arguments.of(TennisScore.THIRTY, TennisScore.THIRTY,
                                                TennisScore.FORTY, TennisScore.THIRTY),
                                Arguments.of(TennisScore.FORTY, TennisScore.THIRTY,
                                                TennisScore.WIN, TennisScore.LOSE),

                                Arguments.of(TennisScore.ZERO, TennisScore.FORTY,
                                                TennisScore.FIFTEEN, TennisScore.FORTY),
                                Arguments.of(TennisScore.FIFTEEN, TennisScore.FORTY,
                                                TennisScore.THIRTY, TennisScore.FORTY),
                                Arguments.of(TennisScore.THIRTY, TennisScore.FORTY,
                                                TennisScore.DEUCE, TennisScore.DEUCE),
                                Arguments.of(TennisScore.ADVANTAGE, TennisScore.FORTY,
                                                TennisScore.WIN, TennisScore.LOSE),

                                Arguments.of(TennisScore.DEUCE, TennisScore.DEUCE,
                                                TennisScore.ADVANTAGE, TennisScore.FORTY),

                                Arguments.of(TennisScore.FORTY, TennisScore.ADVANTAGE,
                                                TennisScore.DEUCE, TennisScore.DEUCE));
        }
}
