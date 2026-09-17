import java.util.*;

/**
 * Seat Booking, Multi-ticket Booking, Ticket Return/Cancellation,
 * and Search modules combined: they all operate on the same
 * bookings map, so keeping them together avoids splitting a single
 * responsibility (booking lifecycle) across unrelated classes.
 */
public class BookingManager {
    private final Map<String, Booking> bookings;
    private final EventManager eventManager;
    private int nextBookingNumber;

    public BookingManager(Map<String, Booking> loadedBookings, EventManager eventManager) {
        this.bookings = loadedBookings;
        this.eventManager = eventManager;
        this.nextBookingNumber = bookings.size() + 1;
    }

    /** Books one or more seats in a single transaction and returns the created Booking. */
    public Booking bookSeats(String eventId, String customerName, int numberOfSeats)
            throws SeatUnavailableException, InvalidBookingException {

        if (numberOfSeats <= 0) {
            throw new InvalidBookingException("Number of seats must be at least 1.");
        }
        Event event = eventManager.getEvent(eventId);
        event.reserveSeats(numberOfSeats);

        double total = numberOfSeats * event.getPricePerSeat();
        String bookingId = "BKG" + String.format("%04d", nextBookingNumber++);
        Booking booking = new Booking(bookingId, eventId, customerName, numberOfSeats, total);
        bookings.put(bookingId, booking);
        return booking;
    }

    /** Cancels a booking, returning its seats to the event's available pool. */
    public Booking cancelBooking(String bookingId) throws InvalidBookingException {
        Booking booking = bookings.remove(bookingId);
        if (booking == null) {
            throw new InvalidBookingException("No booking found with ID: " + bookingId);
        }
        try {
            eventManager.getEvent(booking.getEventId()).releaseSeats(booking.getNumberOfSeats());
        } catch (InvalidBookingException ignored) {
            // Event was removed independently; booking is still cancelled either way.
        }
        return booking;
    }

    /** Search and Retrieve Module: O(1) HashMap lookup by booking ID. */
    public Booking findBooking(String bookingId) throws InvalidBookingException {
        Booking booking = bookings.get(bookingId);
        if (booking == null) {
            throw new InvalidBookingException("No booking found with ID: " + bookingId);
        }
        return booking;
    }

    public List<Booking> findBookingsByCustomer(String customerName) {
        List<Booking> results = new ArrayList<>();
        for (Booking b : bookings.values()) {
            if (b.getCustomerName().equalsIgnoreCase(customerName)) {
                results.add(b);
            }
        }
        return results;
    }

    public List<Booking> listAllBookings() {
        List<Booking> list = new ArrayList<>(bookings.values());
        list.sort(Comparator.comparing(Booking::getBookingId));
        return list;
    }

    public Collection<Booking> getBookingsForSaving() {
        return bookings.values();
    }
}
