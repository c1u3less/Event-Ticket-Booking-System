/**
 * Thrown when an operation references a booking ID or event ID
 * that does not exist, or when input data fails validation.
 */
public class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}
