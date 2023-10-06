package mbp.booking.exception;

public class SeatNotAvailableException extends Exception {

    SeatNotAvailableException() {
        super();
    }

    public SeatNotAvailableException(String message) {
        super(message);
    }

    public SeatNotAvailableException(String message, Throwable cause) {
        super(message, cause);
    }

    public SeatNotAvailableException(Throwable cause) {
        super(cause);
    }
}
