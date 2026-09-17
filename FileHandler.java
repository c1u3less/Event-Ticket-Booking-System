import java.io.*;
import java.util.*;

/**
 * Persistence and File Handling Module.
 * Reads and writes events.txt / bookings.txt as flat, pipe-delimited
 * text files so application state survives a restart without needing
 * a database server.
 */
public class FileHandler {
    private static final String EVENTS_FILE = "events.txt";
    private static final String BOOKINGS_FILE = "bookings.txt";

    public Map<String, Event> loadEvents() {
        Map<String, Event> events = new HashMap<>();
        File file = new File(EVENTS_FILE);
        if (!file.exists()) return events;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) continue;
                Event e = Event.fromFileLine(line);
                events.put(e.getEventId(), e);
            }
        } catch (IOException e) {
            System.out.println("Warning: could not read " + EVENTS_FILE + " (" + e.getMessage() + ")");
        }
        return events;
    }

    public Map<String, Booking> loadBookings() {
        Map<String, Booking> bookings = new HashMap<>();
        File file = new File(BOOKINGS_FILE);
        if (!file.exists()) return bookings;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) continue;
                Booking b = Booking.fromFileLine(line);
                bookings.put(b.getBookingId(), b);
            }
        } catch (IOException e) {
            System.out.println("Warning: could not read " + BOOKINGS_FILE + " (" + e.getMessage() + ")");
        }
        return bookings;
    }

    public void saveEvents(Collection<Event> events) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(EVENTS_FILE))) {
            for (Event e : events) {
                writer.println(e.toFileLine());
            }
        } catch (IOException e) {
            System.out.println("Warning: could not save " + EVENTS_FILE + " (" + e.getMessage() + ")");
        }
    }

    public void saveBookings(Collection<Booking> bookings) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(BOOKINGS_FILE))) {
            for (Booking b : bookings) {
                writer.println(b.toFileLine());
            }
        } catch (IOException e) {
            System.out.println("Warning: could not save " + BOOKINGS_FILE + " (" + e.getMessage() + ")");
        }
    }
}
