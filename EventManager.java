import java.util.*;

/**
 * Administrative Event Management Module.
 * Owns the in-memory catalogue of events (HashMap keyed by eventId
 * for O(1) lookup) and enforces safety checks — e.g. capacity cannot
 * be reduced below the number of seats already booked.
 */
public class EventManager {
    private final Map<String, Event> events;
    private int nextEventNumber;

    public EventManager(Map<String, Event> loadedEvents) {
        this.events = loadedEvents;
        this.nextEventNumber = events.size() + 1;
    }

    public Event createEvent(String name, String venue, int totalSeats, double pricePerSeat) {
        String id = "EVT" + String.format("%03d", nextEventNumber++);
        Event event = new Event(id, name, venue, totalSeats, pricePerSeat);
        events.put(id, event);
        return event;
    }

    public Event getEvent(String eventId) throws InvalidBookingException {
        Event event = events.get(eventId);
        if (event == null) {
            throw new InvalidBookingException("No event found with ID: " + eventId);
        }
        return event;
    }

    /** Increases an event's capacity; safe to call at any time since it never removes seats. */
    public void addCapacity(String eventId, int extraSeats) throws InvalidBookingException {
        getEvent(eventId).increaseCapacity(extraSeats);
    }

    public void updatePrice(String eventId, double newPrice) throws InvalidBookingException {
        getEvent(eventId).setPricePerSeat(newPrice);
    }

    public List<Event> listAllEvents() {
        List<Event> list = new ArrayList<>(events.values());
        list.sort(Comparator.comparing(Event::getEventId));
        return list;
    }

    public Collection<Event> getEventsForSaving() {
        return events.values();
    }
}
