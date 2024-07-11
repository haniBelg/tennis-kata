package kata.tennis.domain;

public enum TennisScore {
    ZERO("0"),
    FIFTEEN("15"),
    THIRTY("30"),
    FORTY("40"),
    DEUCE("DEUCE"),
    ADVANTAGE("ADVANTAGE"),
    WIN("WIN"),
    LOSE("LOSE");

    private final String scoreLibelee;

    public String getLibelle() {
        return this.scoreLibelee;
    }

    TennisScore(String scoreLibelee) {
        this.scoreLibelee = scoreLibelee;
    }
}
