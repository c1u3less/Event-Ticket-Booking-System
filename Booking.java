/**
 * Represents a confirmed booking transaction: a customer, an event,
 * a seat count, and the computed total price.
 */
public class Booking {
    private final String bookingId;
    private final String eventId;
    private final String customerName;
    private final int numberOfSeats;
    private final double totalPrice;

    public Booking(String bookingId, String eventId, String customerName, int numberOfSeats, double totalPrice) {
        this.bookingId = bookingId;
        this.eventId = eventId;
        this.customerName = customerName;
        this.numberOfSeats = numberOfSeats;
        this.totalPrice = totalPrice;
    }

    public String getBookingId() { return bookingId; }
    public String getEventId() { return eventId; }
    public String getCustomerName() { return customerName; }
    public int getNumberOfSeats() { return numberOfSeats; }
    public double getTotalPrice() { return totalPrice; }

    public String toFileLine() {
        return String.join("|", bookingId, eventId, customerName,
                String.valueOf(numberOfSeats), String.valueOf(totalPrice));
    }

    public static Booking fromFileLine(String line) {
        String[] p = line.split("\\|");
        return new Booking(p[0], p[1], p[2], Integer.parseInt(p[3]), Double.parseDouble(p[4]));
    }

    @Override
    public String toString() {
        return String.format("Booking ID: %-8s | Event: %-6s | Customer: %-15s | Seats: %2d | Total: $%.2f",
                bookingId, eventId, customerName, numberOfSeats, totalPrice);
    }
}
