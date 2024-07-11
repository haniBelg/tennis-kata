package kata.tennis.domain.state;

import java.util.Optional;

import kata.tennis.domain.TennisPlayer;
import kata.tennis.domain.TennisScore;

public record TennisGameState(
        Character currentWinnerId,
        TennisPlayer player1,
        TennisPlayer player2) {
    public Optional<TennisPlayer> winner() {
        if (player1.tennisScore().equals(TennisScore.WIN)) {
            return Optional.of(player1);
        }
        if (player2.tennisScore().equals(TennisScore.WIN)) {
            return Optional.of(player2);
        }
        return Optional.empty();
    }
}
