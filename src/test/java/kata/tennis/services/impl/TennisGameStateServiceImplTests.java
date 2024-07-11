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

    TennisGameStateService service = new TennisGameStateServiceImpl();

    @ParameterizedTest
    @MethodSource("provideScoreStates")
    void testGameStateMutations(
            // having
            TennisScore aTennisScore,
            TennisScore bTennisScore,
            Character currentPointWinner,
            TennisScore aExpectedTennisScore,
            TennisScore bExpectedTennisScore) {
        TennisPlayer aPlayer = new TennisPlayer('A', aTennisScore);
        TennisPlayer bPlayer = new TennisPlayer('B', bTennisScore);
        TennisGameState lastGameState = new TennisGameState(null, aPlayer, bPlayer);

        TennisPlayer aExpectedPlayer = new TennisPlayer('A', aExpectedTennisScore);
        TennisPlayer bExpectedPlayer = new TennisPlayer('B', bExpectedTennisScore);

        TennisGameState expectedGameState = new TennisGameState(currentPointWinner, aExpectedPlayer,
                bExpectedPlayer);

        // when
        TennisGameState newGameState = service.getNextGameState(lastGameState, currentPointWinner);
        // then
        assertEquals(expectedGameState, newGameState);
    }

    static Stream<Arguments> provideScoreStates() {
        // return Stream.of(
        // Arguments.of(null, TennisScore.ZERO, TennisScore.ZERO,
        // 'A', TennisScore.FIFTEEN, TennisScore.ZERO));

        return Stream.of(
                Arguments.of(TennisScore.ZERO, TennisScore.ZERO,
                        'A', TennisScore.FIFTEEN, TennisScore.ZERO),
                Arguments.of(TennisScore.FIFTEEN, TennisScore.ZERO,
                        'A', TennisScore.THIRTY, TennisScore.ZERO),
                Arguments.of(TennisScore.THIRTY, TennisScore.ZERO,
                        'A', TennisScore.FORTY, TennisScore.ZERO),
                Arguments.of(TennisScore.FORTY, TennisScore.ZERO,
                        'A', TennisScore.WIN, TennisScore.LOSE),

                Arguments.of(TennisScore.ZERO, TennisScore.FIFTEEN,
                        'A', TennisScore.FIFTEEN, TennisScore.FIFTEEN),
                Arguments.of(TennisScore.FIFTEEN, TennisScore.FIFTEEN,
                        'A', TennisScore.THIRTY, TennisScore.FIFTEEN),
                Arguments.of(TennisScore.THIRTY, TennisScore.FIFTEEN,
                        'A', TennisScore.FORTY, TennisScore.FIFTEEN),
                Arguments.of(TennisScore.FORTY, TennisScore.FIFTEEN,
                        'A', TennisScore.WIN, TennisScore.LOSE),

                Arguments.of(TennisScore.ZERO, TennisScore.THIRTY,
                        'A', TennisScore.FIFTEEN, TennisScore.THIRTY),
                Arguments.of(TennisScore.FIFTEEN, TennisScore.THIRTY,
                        'A', TennisScore.THIRTY, TennisScore.THIRTY),
                Arguments.of(TennisScore.THIRTY, TennisScore.THIRTY,
                        'A', TennisScore.FORTY, TennisScore.THIRTY),
                Arguments.of(TennisScore.FORTY, TennisScore.THIRTY,
                        'A', TennisScore.WIN, TennisScore.LOSE),

                Arguments.of(TennisScore.ZERO, TennisScore.FORTY,
                        'A', TennisScore.FIFTEEN, TennisScore.FORTY),
                Arguments.of(TennisScore.FIFTEEN, TennisScore.FORTY,
                        'A', TennisScore.THIRTY, TennisScore.FORTY),
                Arguments.of(TennisScore.THIRTY, TennisScore.FORTY,
                        'A', TennisScore.DEUCE, TennisScore.DEUCE),
                Arguments.of(TennisScore.ADVANTAGE, TennisScore.FORTY,
                        'A', TennisScore.WIN, TennisScore.LOSE),

                Arguments.of(TennisScore.DEUCE, TennisScore.DEUCE,
                        'A', TennisScore.ADVANTAGE, TennisScore.FORTY),

                Arguments.of(TennisScore.FORTY, TennisScore.ADVANTAGE,
                        'A', TennisScore.DEUCE, TennisScore.DEUCE)

        );
    }
}
