# Event Ticket Booking System

A simple console-based Java application for creating and managing event ticket
bookings. Booking data is stored in a local `bookings.txt` file so that it can
be loaded again when the application starts.

## Features

- Add a booking with a customer name, phone number, and seat number
- Validate seats from `A1` to `E6`
- Prevent duplicate seat bookings
- Automatically generate ticket IDs, starting at `TCK101`
- Display all current bookings
- Search by ticket ID or phone number
- Remove a booking and make its seat available again
- Check whether a seat is available or already booked
- Load and save bookings using a comma-separated text file

## Project Structure

| File | Description |
| --- | --- |
| `EventTicketBookingSystem.java` | Main program, menu, booking operations, and file handling |
| `Booking.java` | Stores booking details and displays a booking |
| `OnlineBooking.java` | Booking subtype with online-booking display output |
| `SeatAlreadyBookedException.java` | Custom exception for duplicate seats |
| `bookings.txt` | Runtime data file created after the first booking |

## Requirements

- Java Development Kit (JDK) 8 or later
- A command prompt, terminal, or Java-compatible IDE

## Compile and Run

Open a terminal in the project directory and run:

```bash
javac *.java
java EventTicketBookingSystem
```

On Windows PowerShell, the same commands can be used from the folder containing
the `.java` files.

## Menu Options

1. **Add Booking** - Enter customer details and a seat number such as `A1`.
2. **Read Bookings** - Display every saved booking.
3. **Search Booking** - Find a booking using its ticket ID or phone number.
4. **Remove Booking** - Delete a booking using its ticket ID.
5. **Check Seat** - Check whether a seat is available.
6. **Exit** - Close the application.

## Data Storage

Bookings are stored in `bookings.txt` using this format:

```text
ticketId,customerName,phone,seatNumber,price
```

The file is created in the current working directory when the first booking is
saved. Keep the file with the project if you want bookings to persist between
runs.

## Notes

- The application uses a fixed ticket price of `Rs. 500.0`.
- Seat numbers are case-insensitive when creating a booking.
- Avoid commas in customer names or phone numbers because the storage format is
	comma-separated.
