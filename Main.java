import java.util.*;

/**
 * Menu Driver.
 * Wires the persistence, event, and booking layers together and
 * exposes them through a structured command-line menu. Kept free of
 * business logic itself — it only reads input and delegates.
 */
public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final FileHandler fileHandler = new FileHandler();
    private static EventManager eventManager;
    private static BookingManager bookingManager;

    public static void main(String[] args) {
        eventManager = new EventManager(fileHandler.loadEvents());
        bookingManager = new BookingManager(fileHandler.loadBookings(), eventManager);

        System.out.println("=======================================");
        System.out.println(" EVENT TICKET BOOKING SYSTEM");
        System.out.println("=======================================");

        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();
            try {
                switch (choice) {
                    case "1" -> createEvent();
                    case "2" -> listEvents();
                    case "3" -> bookSeats();
                    case "4" -> cancelBooking();
                    case "5" -> searchBooking();
                    case "6" -> listBookings();
                    case "0" -> running = false;
                    default -> System.out.println("Invalid option, please try again.");
                }
            } catch (SeatUnavailableException | InvalidBookingException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Error: please enter a valid number.");
            }
        }

        fileHandler.saveEvents(eventManager.getEventsForSaving());
        fileHandler.saveBookings(bookingManager.getBookingsForSaving());
        System.out.println("State saved. Goodbye!");
    }

    private static void printMenu() {
        System.out.println("\n--- MENU ---");
        System.out.println("1. Create Event (Admin)");
        System.out.println("2. View Events & Seat Availability");
        System.out.println("3. Book Seat(s)");
        System.out.println("4. Cancel Booking");
        System.out.println("5. Search Booking by ID");
        System.out.println("6. View All Bookings");
        System.out.println("0. Exit & Save");
        System.out.print("Choose an option: ");
    }

    private static void createEvent() {
        System.out.print("Event name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Venue: ");
        String venue = scanner.nextLine().trim();
        System.out.print("Total seats: ");
        int seats = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Price per seat: ");
        double price = Double.parseDouble(scanner.nextLine().trim());

        Event event = eventManager.createEvent(name, venue, seats, price);
        System.out.println("Created: " + event);
    }

    private static void listEvents() {
        List<Event> events = eventManager.listAllEvents();
        if (events.isEmpty()) {
            System.out.println("No events available yet.");
            return;
        }
        for (Event e : events) System.out.println(e);
    }

    private static void bookSeats() throws SeatUnavailableException, InvalidBookingException {
        System.out.print("Event ID: ");
        String eventId = scanner.nextLine().trim();
        System.out.print("Customer name: ");
        String customer = scanner.nextLine().trim();
        System.out.print("Number of seats: ");
        int seats = Integer.parseInt(scanner.nextLine().trim());

        Booking booking = bookingManager.bookSeats(eventId, customer, seats);
        System.out.println("Booking confirmed -> " + booking);
    }

    private static void cancelBooking() throws InvalidBookingException {
        System.out.print("Booking ID to cancel: ");
        String bookingId = scanner.nextLine().trim();
        Booking cancelled = bookingManager.cancelBooking(bookingId);
        System.out.println("Cancelled -> " + cancelled);
    }

    private static void searchBooking() throws InvalidBookingException {
        System.out.print("Booking ID to search: ");
        String bookingId = scanner.nextLine().trim();
        System.out.println(bookingManager.findBooking(bookingId));
    }

    private static void listBookings() {
        List<Booking> bookings = bookingManager.listAllBookings();
        if (bookings.isEmpty()) {
            System.out.println("No bookings yet.");
            return;
        }
        for (Booking b : bookings) System.out.println(b);
    }
}
