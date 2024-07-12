package kata.tennis.services.impl;

import kata.tennis.domain.TennisPlayer;
import kata.tennis.domain.state.TennisGameState;
import kata.tennis.domain.state.TennisScoreState;
import kata.tennis.services.TennisGameStateService;
import kata.tennis.services.TennisScoreStateService;
import kata.tennis.services.exceptions.GameAlreadyFinishedException;

/**
 * Implementation of {@link TennisGameStateService} that manages and updates the
 * state of a tennis game.
 */
public class TennisGameStateServiceImpl implements TennisGameStateService {
        private final TennisScoreStateService tennisScoreService;

        /**
         * Constructs a new instance of {@code TennisGameStateServiceImpl}.
         * Initializes the {@link TennisScoreStateService}.
         */
        public TennisGameStateServiceImpl() {
                this.tennisScoreService = new TennisScoreStateServiceImpl();
        }

        /**
         * {@inheritDoc}
         * <p>
         * This implementation determines the next state of the game based on the last
         * game state and the current point winner.
         * </p>
         * 
         * @param lastGameState   the last recorded state of the tennis game.
         * @param currentWinnerId the identifier of the player who won the current
         *                        point.
         * @return the next {@link TennisGameState} representing the updated state of
         *         the game.
         * @throws GameAlreadyFinishedException if the game has already finished.
         */
        @Override
        public TennisGameState getNextGameState(TennisGameState lastGameState, Character currentWinnerId)
                        throws GameAlreadyFinishedException {
                if (lastGameState.winner().isPresent()) {
                        throw new GameAlreadyFinishedException(String.format(
                                        "Player %c already won this game, you could not proceed with this point !",
                                        lastGameState.winner().get().id()));
                }
                TennisPlayer pointWinner = lastGameState.player1().id().equals((char) currentWinnerId)
                                ? lastGameState.player1()
                                : lastGameState.player2();
                TennisPlayer pointLoser = lastGameState.player1().id().equals((char) currentWinnerId)
                                ? lastGameState.player2()
                                : lastGameState.player1();
                TennisScoreState currentScore = new TennisScoreState(pointWinner.tennisScore(),
                                pointLoser.tennisScore());
                TennisScoreState newScore = tennisScoreService.getNextScoreState(currentScore);
                TennisPlayer newPointWinner = new TennisPlayer(pointWinner.id(), newScore.winnerScore());
                TennisPlayer newPointLoser = new TennisPlayer(pointLoser.id(), newScore.loserScore());
                TennisPlayer newPlayer1 = newPointWinner.id().equals(lastGameState.player1().id()) ? newPointWinner
                                : newPointLoser;
                TennisPlayer newPlayer2 = newPointWinner.id().equals(lastGameState.player1().id()) ? newPointLoser
                                : newPointWinner;
                return new TennisGameState(currentWinnerId, newPlayer1, newPlayer2);
        }

}
