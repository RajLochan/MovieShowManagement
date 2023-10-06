package mbp.booking.exception;

public class InvalidActionException extends Exception {
    InvalidActionException() {
        super();
    }

    public InvalidActionException(String message) {
        super(message);
    }

    public InvalidActionException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidActionException(Throwable cause) {
        super(cause);
    }}
