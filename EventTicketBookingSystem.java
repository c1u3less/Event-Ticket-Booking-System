import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashSet;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

public class EventTicketBookingSystem {
    static ArrayList<Booking> bookingList = new ArrayList<>();
    static HashSet<String> bookedSeats = new HashSet<>();
    static int ticketCounter = 101;
    static String fileName = "bookings.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        loadBookingsFromFile();
        int choice;
        do {
            System.out.println("\n========================================");
            System.out.println("EVENT TICKET BOOKING SYSTEM");
            System.out.println("========================================");
            System.out.println("1. Add Booking");
            System.out.println("2. Read Bookings");
            System.out.println("3. Search Booking");
            System.out.println("4. Remove Booking");
            System.out.println("5. Check Seat");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    try {
                        addBooking(scanner);
                    } catch (SeatAlreadyBookedException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 2:
                    readBookings();
                    break;
                case 3:
                    searchBooking(scanner);
                    break;
                case 4:
                    removeBooking(scanner);
                    break;
                case 5:
                    checkSeat(scanner);
                    break;
                case 6:
                    System.out.println("Thank you for using Event Ticket Booking System.");
                    break;
                default:
                    System.out.println("Invalid choice! Please select 1 to 6.");
            }
        } while (choice != 6);
        scanner.close();
    }

    public static void addBooking(Scanner scanner) throws SeatAlreadyBookedException {
        System.out.println("\n---------- ADD BOOKING ----------");
        System.out.print("Enter Customer Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Phone Number: ");
        String phone = scanner.nextLine();
        System.out.print("Enter Seat Number (A1-E6): ");
        String seat = scanner.nextLine().toUpperCase();
        if (!seat.matches("[A-E][1-6]")) {
            System.out.println("Invalid Seat Number! Please enter A1 to E6.");
            return;
        }
        if (bookedSeats.contains(seat)) {
            throw new SeatAlreadyBookedException("Sorry! This seat is already booked.");
        }
        double price = 500.0;
        String ticketId = "TCK" + ticketCounter;
        ticketCounter++;
        Booking booking = new Booking(ticketId, name, phone, seat, price);
        bookingList.add(booking);
        bookedSeats.add(seat);
        saveBookingToFile(booking);
        System.out.println("\nBooking Created Successfully!");
        booking.displayBooking();
    }

    public static void checkSeat(Scanner scanner) {
        System.out.println("\n---------- CHECK SEAT ----------");
        System.out.print("Enter Seat Number: ");
        String seat = scanner.nextLine();
        if (bookedSeats.contains(seat)) {
            System.out.println("Seat " + seat + " is already booked.");
        } else {
            System.out.println("Seat " + seat + " is available.");
        }
    }

    public static void searchBooking(Scanner scanner) {
        System.out.println("\n---------- SEARCH BOOKING ----------");
        System.out.print("Enter Ticket ID or Phone Number: ");
        String query = scanner.nextLine();
        boolean found = false;
        for (Booking booking : bookingList) {
            if (booking.getTicketId().equalsIgnoreCase(query)
                    || booking.getPhone().equals(query)) {
                System.out.println("\nBooking Found!");
                booking.displayBooking();
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Booking not found!");
        }
    }

    public static void removeBooking(Scanner scanner) {
        System.out.println("\n---------- REMOVE BOOKING ----------");
        System.out.print("Enter Ticket ID: ");
        String ticketId = scanner.nextLine();
        boolean found = false;
        for (int i = 0; i < bookingList.size(); i++) {
            Booking booking = bookingList.get(i);
            if (booking.getTicketId().equalsIgnoreCase(ticketId)) {
                bookedSeats.remove(booking.getSeatNumber());
                bookingList.remove(i);
                updateFile();
                System.out.println("\nBooking removed successfully!");
                System.out.println("Seat " + booking.getSeatNumber() + " is now available.");
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Booking not found!");
        }
    }

    public static void updateFile() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, false));
            for (Booking booking : bookingList) {
                writer.write(
                        booking.getTicketId() + "," +
                                booking.getCustomerName() + "," +
                                booking.getPhone() + "," +
                                booking.getSeatNumber() + "," +
                                booking.getPrice());
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error while updating booking file.");
        }
    }

    public static void readBookings() {
        System.out.println("\n---------- ALL BOOKINGS ----------");
        if (bookingList.isEmpty()) {
            System.out.println("No bookings found.");
        } else {
            for (Booking booking : bookingList) {
                booking.displayBooking();
            }
        }
    }

    public static void saveBookingToFile(Booking booking) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
            writer.write(
                    booking.getTicketId() + "," +
                            booking.getCustomerName() + "," +
                            booking.getPhone() + "," +
                            booking.getSeatNumber() + "," +
                            booking.getPrice());
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            System.out.println("Error while saving booking.");
        }
    }

    public static void loadBookingsFromFile() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                String ticketId = data[0];
                String name = data[1];
                String phone = data[2];
                String seat = data[3];
                double price = Double.parseDouble(data[4]);
                Booking booking = new Booking(ticketId, name, phone, seat, price);
                bookingList.add(booking);
                bookedSeats.add(seat);
                int number = Integer.parseInt(ticketId.substring(3));
                if (number >= ticketCounter) {
                    ticketCounter = number + 1;
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("No previous booking file found.");
        }
    }
}
