package kata.tennis.domain.state;

import kata.tennis.domain.TennisScore;

public record TennisScoreState(
        TennisScore winnerScore,
        TennisScore loserScore) {
}
