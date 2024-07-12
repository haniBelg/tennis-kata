package kata.tennis.services.impl;

import java.util.Map;

import kata.tennis.domain.TennisScore;
import kata.tennis.domain.state.TennisScoreState;
import kata.tennis.services.TennisScoreStateService;

/**
 * Implementation of {@link TennisScoreStateService} that manages the scoring
 * logic of a tennis game.
 */
public class TennisScoreStateServiceImpl implements TennisScoreStateService {

    private final Map<TennisScore, TennisScore> defaultScoreWinMutations;
    private final Map<TennisScore, TennisScore> defaultScoreLoseMutations;

    /**
     * Constructs a new instance of {@code TennisScoreStateServiceImpl}.
     * Initializes the default score mutation maps for winning and losing scenarios.
     */
    public TennisScoreStateServiceImpl() {
        this.defaultScoreWinMutations = Map.of(
                TennisScore.ZERO, TennisScore.FIFTEEN,
                TennisScore.FIFTEEN, TennisScore.THIRTY,
                TennisScore.THIRTY, TennisScore.FORTY,
                TennisScore.FORTY, TennisScore.WIN,
                TennisScore.DEUCE, TennisScore.ADVANTAGE,
                TennisScore.ADVANTAGE, TennisScore.WIN);

        this.defaultScoreLoseMutations = Map.of(
                TennisScore.DEUCE, TennisScore.FORTY,
                TennisScore.ADVANTAGE, TennisScore.DEUCE);
    }

    /**
     * {@inheritDoc}
     * <p>
     * This implementation computes the next score state based on the current score
     * state.
     * </p>
     * 
     * @param currentScore the current score state of the tennis game.
     * @return the next {@link TennisScoreState} representing the updated score
     *         state.
     */
    @Override
    public TennisScoreState getNextScoreState(TennisScoreState currentScore) {
        // TODO: Consider throwing an exception for illegal game states as input.
        // For example, if the input state is "40 / 40", it should be "DEUCE / DEUCE"
        // instead. This state should never occur in valid game progression.
        TennisScore winnerScore = currentScore.winnerScore();
        TennisScore loserScore = currentScore.loserScore();
        TennisScore newWinnerScore = computeNewWinnerScore(winnerScore, loserScore);
        TennisScore newLoserScore = computeNewLoserScore(loserScore, newWinnerScore);
        return new TennisScoreState(newWinnerScore, newLoserScore);
    }

    /**
     * Computes the new score of the winner based on the current scores.
     * 
     * @param winnerScore the current score of the player who won the point.
     * @param loserScore  the current score of the player who lost the point.
     * @return the new score of the winner after the point.
     */
    private TennisScore computeNewWinnerScore(TennisScore winnerScore, TennisScore loserScore) {
        boolean bothWillBeForty = (winnerScore.equals(TennisScore.THIRTY) &&
                loserScore.equals(TennisScore.FORTY));
        boolean bothWillBeAdvantage = (winnerScore.equals(TennisScore.FORTY) &&
                loserScore.equals(TennisScore.ADVANTAGE));
        boolean shouldBeDeuce = bothWillBeForty || bothWillBeAdvantage;
        if (shouldBeDeuce) {
            return TennisScore.DEUCE;
        }
        return defaultScoreWinMutations.get(winnerScore);
    }

    /**
     * Computes the new score of the loser based on the current loser score and the
     * new winner score.
     * 
     * @param loserScore     the current score of the player who lost the point.
     * @param newWinnerScore the new score of the player who won the point.
     * @return the new score of the loser after the point.
     */
    private TennisScore computeNewLoserScore(TennisScore loserScore, TennisScore newWinnerScore) {
        if (newWinnerScore.equals(TennisScore.WIN)) {
            return TennisScore.LOSE;
        }
        if (newWinnerScore.equals(TennisScore.DEUCE)) {
            return TennisScore.DEUCE;
        }
        return defaultScoreLoseMutations.getOrDefault(loserScore, loserScore);
    }

}
