public class Booking {
    private String ticketId;
    private String customerName;
    private String phone;
    private String seatNumber;
    private double price;

    public Booking(String ticketId, String customerName, String phone, String seatNumber, double price) {
        this.ticketId = ticketId;
        this.customerName = customerName;
        this.phone = phone;
        this.seatNumber = seatNumber;
        this.price = price;
    }

    public String getTicketId() {
        return ticketId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getPhone() {
        return phone;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public double getPrice() {
        return price;
    }

    public void displayBooking() {
        System.out.println("-----------------------------");
        System.out.println("Ticket ID     : " + ticketId);
        System.out.println("Customer Name : " + customerName);
        System.out.println("Phone         : " + phone);
        System.out.println("Seat Number   : " + seatNumber);
        System.out.println("Price         : Rs. " + price);
        System.out.println("-----------------------------");
    }

    public void displayBooking(String message) {
        System.out.println("\n" + message);
        displayBooking();
    }
}
