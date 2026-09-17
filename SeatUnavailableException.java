/**
 * Thrown when a booking request asks for more seats than an event
 * currently has available.
 */
public class SeatUnavailableException extends Exception {
    public SeatUnavailableException(String message) {
        super(message);
    }
}
