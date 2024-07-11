package kata.tennis.services.impl;

import java.util.Map;

import kata.tennis.domain.TennisScore;
import kata.tennis.domain.state.TennisScoreState;
import kata.tennis.services.TennisScoreStateService;

public class TennisScoreStateServiceImpl implements TennisScoreStateService {

    private final Map<TennisScore, TennisScore> defaultScoreWinMutations;
    private final Map<TennisScore, TennisScore> defaultScoreLoseMutations;

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

    @Override
    public TennisScoreState getNextScore(TennisScoreState currentScore) {
        TennisScore winnerScore = currentScore.winnerScore();
        TennisScore loserScore = currentScore.loserScore();
        TennisScore newWinnerScore = newWinnerScore(winnerScore, loserScore);
        TennisScore newLoserScore = newLoserScore(loserScore, newWinnerScore);
        return new TennisScoreState(newWinnerScore, newLoserScore);
    }

    private TennisScore newWinnerScore(TennisScore winnerScore, TennisScore loserScore) {
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

    private TennisScore newLoserScore(TennisScore loserScore, TennisScore newWinnerScore) {
        if (newWinnerScore.equals(TennisScore.WIN)) {
            return TennisScore.LOSE;
        }
        if (newWinnerScore.equals(TennisScore.DEUCE)) {
            return TennisScore.DEUCE;
        }
        return defaultScoreLoseMutations.getOrDefault(loserScore, loserScore);
    }

}
