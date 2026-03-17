import java.util.ArrayList;
import java.util.List;

class Reservation8 {
    private String guestName;
    private String roomType;
    private String roomId;

    public Reservation8(String guestName, String roomType, String roomId) {
        this.guestName = guestName;
        this.roomType  = roomType;
        this.roomId    = roomId;
    }

    public String getGuestName() { return guestName; }
    public String getRoomType()  { return roomType; }
    public String getRoomId()    { return roomId; }
}

class BookingHistory {
    private List<Reservation8> confirmedReservations;

    public BookingHistory() { confirmedReservations = new ArrayList<>(); }

    public void addReservation(Reservation8 reservation) { confirmedReservations.add(reservation); }

    public List<Reservation8> getConfirmedReservations() { return confirmedReservations; }
}

class BookingReportService {
    public void generateReport(BookingHistory history) {

        List<Reservation8> reservations = history.getConfirmedReservations();

        System.out.println("============================================================");
        System.out.println("       Book My Stay - Booking History Report");
        System.out.println("============================================================");
        System.out.println("Total Confirmed Bookings : " + reservations.size());
        System.out.println("------------------------------------------------------------");

        int count = 1;
        for (Reservation8 r : reservations) {
            System.out.println("Booking #" + count++);
            System.out.println("  Guest Name  : " + r.getGuestName());
            System.out.println("  Room Type   : " + r.getRoomType());
            System.out.println("  Room ID     : " + r.getRoomId());
            System.out.println();
        }

        System.out.println("============================================================");
        System.out.println("End of Report.");
        System.out.println("============================================================");
    }
}

public class uc8 {
    public static void main(String[] args) {

        BookingHistory history = new BookingHistory();

        history.addReservation(new Reservation8("Alice", "Single", "S-101"));
        history.addReservation(new Reservation8("Bob",   "Double", "D-101"));
        history.addReservation(new Reservation8("Carol", "Suite",  "S-101"));
        history.addReservation(new Reservation8("David", "Single", "S-102"));

        BookingReportService reportService = new BookingReportService();
        reportService.generateReport(history);
    }
}
