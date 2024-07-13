package kata.tennis.services.impl;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import kata.tennis.domain.TennisScore;
import kata.tennis.domain.state.TennisScoreState;
import kata.tennis.services.TennisScoreStateService;

public class TennisScoreStateServiceImplTest {

        // Tests are designed around the SOLID principle of Dependency Inversion,
        // focusing on the TennisGameHistoryPrinterService interface rather than its
        // specific implementation.
        // This approach ensures that tests remain stable against changes in the
        // interface, promoting robustness.
        TennisScoreStateService service = new TennisScoreStateServiceImpl();

        @ParameterizedTest
        @MethodSource("provideScoreStates")
        void testScoreStateMutations(
                        // given
                        TennisScoreState currentScoreState,
                        TennisScoreState expected) {
                // when
                TennisScoreState newScoreState = service.getNextScoreState(currentScoreState);
                // then
                assertEquals(expected, newScoreState);
        }

        static Stream<Arguments> provideScoreStates() {
                return Stream.of(
                                Arguments.of(new TennisScoreState(TennisScore.ZERO, TennisScore.ZERO),
                                                new TennisScoreState(TennisScore.FIFTEEN, TennisScore.ZERO)),
                                Arguments.of(new TennisScoreState(TennisScore.FIFTEEN, TennisScore.ZERO),
                                                new TennisScoreState(TennisScore.THIRTY, TennisScore.ZERO)),
                                Arguments.of(new TennisScoreState(TennisScore.THIRTY, TennisScore.ZERO),
                                                new TennisScoreState(TennisScore.FORTY, TennisScore.ZERO)),
                                Arguments.of(new TennisScoreState(TennisScore.FORTY, TennisScore.ZERO),
                                                new TennisScoreState(TennisScore.WIN, TennisScore.LOSE)),

                                Arguments.of(new TennisScoreState(TennisScore.ZERO, TennisScore.FIFTEEN),
                                                new TennisScoreState(TennisScore.FIFTEEN, TennisScore.FIFTEEN)),
                                Arguments.of(new TennisScoreState(TennisScore.FIFTEEN, TennisScore.FIFTEEN),
                                                new TennisScoreState(TennisScore.THIRTY, TennisScore.FIFTEEN)),
                                Arguments.of(new TennisScoreState(TennisScore.THIRTY, TennisScore.FIFTEEN),
                                                new TennisScoreState(TennisScore.FORTY, TennisScore.FIFTEEN)),
                                Arguments.of(new TennisScoreState(TennisScore.FORTY, TennisScore.FIFTEEN),
                                                new TennisScoreState(TennisScore.WIN, TennisScore.LOSE)),

                                Arguments.of(new TennisScoreState(TennisScore.ZERO, TennisScore.THIRTY),
                                                new TennisScoreState(TennisScore.FIFTEEN, TennisScore.THIRTY)),
                                Arguments.of(new TennisScoreState(TennisScore.FIFTEEN, TennisScore.THIRTY),
                                                new TennisScoreState(TennisScore.THIRTY, TennisScore.THIRTY)),
                                Arguments.of(new TennisScoreState(TennisScore.THIRTY, TennisScore.THIRTY),
                                                new TennisScoreState(TennisScore.FORTY, TennisScore.THIRTY)),
                                Arguments.of(new TennisScoreState(TennisScore.FORTY, TennisScore.THIRTY),
                                                new TennisScoreState(TennisScore.WIN, TennisScore.LOSE)),

                                Arguments.of(new TennisScoreState(TennisScore.ZERO, TennisScore.FORTY),
                                                new TennisScoreState(TennisScore.FIFTEEN, TennisScore.FORTY)),
                                Arguments.of(new TennisScoreState(TennisScore.FIFTEEN, TennisScore.FORTY),
                                                new TennisScoreState(TennisScore.THIRTY, TennisScore.FORTY)),
                                Arguments.of(new TennisScoreState(TennisScore.THIRTY, TennisScore.FORTY),
                                                new TennisScoreState(TennisScore.DEUCE, TennisScore.DEUCE)),
                                Arguments.of(new TennisScoreState(TennisScore.ADVANTAGE, TennisScore.FORTY),
                                                new TennisScoreState(TennisScore.WIN, TennisScore.LOSE)),

                                Arguments.of(new TennisScoreState(TennisScore.DEUCE, TennisScore.DEUCE),
                                                new TennisScoreState(TennisScore.ADVANTAGE, TennisScore.FORTY)),

                                Arguments.of(new TennisScoreState(TennisScore.FORTY, TennisScore.ADVANTAGE),
                                                new TennisScoreState(TennisScore.DEUCE, TennisScore.DEUCE))

                );
        }
}
