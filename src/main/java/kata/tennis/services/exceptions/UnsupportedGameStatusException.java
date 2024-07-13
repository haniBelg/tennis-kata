package kata.tennis.services.exceptions;

public class UnsupportedGameStatusException extends IllegalArgumentException {
    public UnsupportedGameStatusException(String message) {
        super(message);
    }
}
