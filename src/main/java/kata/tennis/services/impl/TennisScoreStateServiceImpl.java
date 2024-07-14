package kata.tennis.services.impl;

import java.util.Map;
import java.util.Optional;

import kata.tennis.domain.TennisScore;
import kata.tennis.domain.state.TennisScoreState;
import kata.tennis.services.TennisScoreStateService;
import kata.tennis.services.exceptions.UnsupportedGameStatusException;

/**
 * Implementation of {@link TennisScoreStateService} that manages the scoring
 * logic of a tennis game.
 */
public class TennisScoreStateServiceImpl implements TennisScoreStateService {

        private final Map<TennisScore, TennisScore> scoreWinMutations;
        private final Map<TennisScoreState, TennisScore> scoreWinMutationsBasedOnCurrentTennisScoreState;
        private final Map<TennisScore, TennisScore> scoreLoseMutations;
        private final Map<TennisScore, TennisScore> scoreLoseMutationsBasedOnOpponentWinStatus;

        /**
         * Constructs a new instance of {@code TennisScoreStateServiceImpl}.
         * Initializes the default score mutation maps for winning and losing scenarios.
         */
        public TennisScoreStateServiceImpl() {
                // win mutations based on current winner status
                this.scoreWinMutations = Map.of(
                                TennisScore.ZERO, TennisScore.FIFTEEN,
                                TennisScore.FIFTEEN, TennisScore.THIRTY,
                                TennisScore.THIRTY, TennisScore.FORTY,
                                TennisScore.FORTY, TennisScore.WIN,
                                TennisScore.DEUCE, TennisScore.ADVANTAGE,
                                TennisScore.ADVANTAGE, TennisScore.WIN);
                // win mutation based on both loser and winner current statuses
                this.scoreWinMutationsBasedOnCurrentTennisScoreState = Map.of(
                                new TennisScoreState(TennisScore.THIRTY, TennisScore.FORTY), TennisScore.DEUCE,
                                new TennisScoreState(TennisScore.FORTY, TennisScore.ADVANTAGE), TennisScore.DEUCE);
                // loser mutations based on current loser status
                this.scoreLoseMutations = Map.of(
                                TennisScore.DEUCE, TennisScore.FORTY,
                                TennisScore.ADVANTAGE, TennisScore.DEUCE);
                // loser mutation based on the new winner's status
                this.scoreLoseMutationsBasedOnOpponentWinStatus = Map.of(
                                TennisScore.ADVANTAGE, TennisScore.FORTY,
                                TennisScore.WIN, TennisScore.LOSE,
                                TennisScore.DEUCE, TennisScore.DEUCE);
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
        public TennisScoreState getNextScoreState(TennisScoreState currentScore) throws UnsupportedGameStatusException {
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
        private TennisScore computeNewWinnerScore(TennisScore winnerScore, TennisScore loserScore)
                        throws UnsupportedGameStatusException {
                TennisScoreState currentTennisScoreState = new TennisScoreState(winnerScore, loserScore);
                return Optional
                                .of(scoreWinMutationsBasedOnCurrentTennisScoreState.getOrDefault(
                                                currentTennisScoreState,
                                                scoreWinMutations.get(winnerScore)))
                                .orElseThrow(() -> {
                                        return new UnsupportedGameStatusException(String.format(
                                                        "unsupported current game status for winner status : "
                                                                        + "[%s] and loser status : [%s]; could not be processed !",
                                                        winnerScore.name(), loserScore.name()));
                                });
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
                return scoreLoseMutationsBasedOnOpponentWinStatus.getOrDefault(newWinnerScore,
                                scoreLoseMutations.getOrDefault(loserScore, loserScore));
        }

}
