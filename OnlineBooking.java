public class OnlineBooking extends Booking {
    public OnlineBooking(String ticketId, String customerName, String phone, String seatNumber, double price) {
        super(ticketId, customerName, phone, seatNumber, price);
    }

    @Override
    public void displayBooking() {
        System.out.println("----- ONLINE BOOKING -----");
        super.displayBooking();
    }
}
