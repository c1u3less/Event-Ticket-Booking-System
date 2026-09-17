/**
 * Represents a bookable event.
 * Encapsulates capacity and pricing behind getters/setters with
 * validation so external code can never push the object into an
 * inconsistent state (e.g. negative seats).
 */
public class Event {
    private final String eventId;
    private String name;
    private String venue;
    private int totalSeats;
    private int availableSeats;
    private double pricePerSeat;

    public Event(String eventId, String name, String venue, int totalSeats, double pricePerSeat) {
        this.eventId = eventId;
        this.name = name;
        this.venue = venue;
        this.totalSeats = totalSeats;
        this.availableSeats = totalSeats;
        this.pricePerSeat = pricePerSeat;
    }

    /** Constructor used when reconstructing an Event from the persisted file (exact seat count). */
    public Event(String eventId, String name, String venue, int totalSeats, int availableSeats, double pricePerSeat) {
        this.eventId = eventId;
        this.name = name;
        this.venue = venue;
        this.totalSeats = totalSeats;
        this.availableSeats = availableSeats;
        this.pricePerSeat = pricePerSeat;
    }

    public String getEventId() { return eventId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getVenue() { return venue; }
    public void setVenue(String venue) { this.venue = venue; }
    public int getTotalSeats() { return totalSeats; }
    public int getAvailableSeats() { return availableSeats; }
    public double getPricePerSeat() { return pricePerSeat; }
    public void setPricePerSeat(double pricePerSeat) { this.pricePerSeat = pricePerSeat; }

    /** Increases total capacity and available seats together, keeping them consistent. */
    public void increaseCapacity(int extraSeats) {
        this.totalSeats += extraSeats;
        this.availableSeats += extraSeats;
    }

    void reserveSeats(int count) throws SeatUnavailableException {
        if (count > availableSeats) {
            throw new SeatUnavailableException(
                "Only " + availableSeats + " seat(s) left for '" + name + "', requested " + count);
        }
        availableSeats -= count;
    }

    void releaseSeats(int count) {
        availableSeats = Math.min(totalSeats, availableSeats + count);
    }

    /** Serializes this event to a single pipe-delimited line for file persistence. */
    public String toFileLine() {
        return String.join("|", eventId, name, venue,
                String.valueOf(totalSeats), String.valueOf(availableSeats), String.valueOf(pricePerSeat));
    }

    public static Event fromFileLine(String line) {
        String[] p = line.split("\\|");
        return new Event(p[0], p[1], p[2], Integer.parseInt(p[3]), Integer.parseInt(p[4]), Double.parseDouble(p[5]));
    }

    @Override
    public String toString() {
        return String.format("[%s] %-25s @ %-15s | Seats: %3d/%3d available | Price: $%.2f",
                eventId, name, venue, availableSeats, totalSeats, pricePerSeat);
    }
}
