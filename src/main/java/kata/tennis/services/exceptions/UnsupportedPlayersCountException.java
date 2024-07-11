package kata.tennis.services.exceptions;

public class UnsupportedPlayersCountException extends IllegalArgumentException {
    public UnsupportedPlayersCountException(String message) {
        super(message);
    }
}
