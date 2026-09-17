# Event Ticket Booking System

A console-based Java application for creating events and managing ticket
bookings. Event and booking state is stored in local text files and loaded when
the application starts.

## Features

- Create events with an event name, venue, total capacity, and price per seat
- View all events with their available and total seat counts
- Book one or more seats in a single transaction
- Prevent bookings that request more seats than are available
- Automatically generate event IDs such as `EVT001` and booking IDs such as
	`BKG0001`
- Cancel bookings and return their seats to the event's available capacity
- Search for a booking by booking ID
- Display all bookings sorted by booking ID
- Increase an event's capacity and update its price through the management
	classes
- Load and save events and bookings using pipe-delimited text files

## Project Structure

| File | Description |
| --- | --- |
| `Main.java` | Console menu and application entry point; delegates work to the managers |
| `Event.java` | Stores event details, capacity, availability, and price |
| `EventManager.java` | Creates, finds, lists, and updates events |
| `Booking.java` | Stores booking details and serializes booking data |
| `BookingManager.java` | Books, cancels, searches, lists, and prepares bookings for saving |
| `FileHandler.java` | Loads and saves events and bookings from local text files |
| `InvalidBookingException.java` | Signals missing event or booking IDs and invalid booking requests |
| `SeatUnavailableException.java` | Signals that an event does not have enough available seats |
| `events.txt` | Runtime event data file created when application state is saved |
| `bookings.txt` | Runtime booking data file created when application state is saved |

## Requirements

- Java Development Kit (JDK) 14 or later
- A command prompt, terminal, or Java-compatible IDE

## Compile and Run

Open a terminal in the project directory and run:

```bash
javac *.java
java Main
```

On Windows PowerShell, the same commands can be used from the folder containing
the `.java` files.

## Menu Options

1. **Create Event** - Enter an event name, venue, total seats, and price per
	seat. The event receives an ID such as `EVT001`.
2. **View Events & Seat Availability** - Display every event and its current
	available capacity.
3. **Book Seat(s)** - Enter an event ID, customer name, and number of seats.
	The application calculates the total price and creates a booking ID.
4. **Cancel Booking** - Cancel a booking using its booking ID and return its
	seats to the event.
5. **Search Booking by ID** - Find and display one booking by its booking ID.
6. **View All Bookings** - Display all bookings sorted by booking ID.
0. **Exit & Save** - Save events and bookings, then close the application.

## Data Storage

Events are stored in `events.txt` using this format:

```text
eventId|name|venue|totalSeats|availableSeats|pricePerSeat
```

Bookings are stored in `bookings.txt` using this format:

```text
bookingId|eventId|customerName|numberOfSeats|totalPrice
```

Both files are read from and written to the application's current working
directory. State is saved when option `0` is selected. Keep the generated files
with the project if you want events and bookings to persist between runs.

## Notes

- Prices are configured per event and displayed in US dollar format.
- A booking can contain multiple seats, but all requested seats must be
	available for the selected event.
- Event and booking names, venues, and customer names should not contain the
	pipe character (`|`), because it is used as the storage delimiter.
- The application uses in-memory `HashMap` collections while running and
	persists their contents through `FileHandler` when it exits normally.
