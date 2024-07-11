package kata.tennis.services.exceptions;

public class GameAlreadyFinishedException extends IllegalArgumentException {
    public GameAlreadyFinishedException(String message) {
        super(message);
    }
}
