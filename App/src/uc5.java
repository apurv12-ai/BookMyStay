import java.util.LinkedList;
import java.util.Queue;

class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }
}

class BookingRequestQueue {
    private Queue<Reservation> requestQueue;

    public BookingRequestQueue() { requestQueue = new LinkedList<>(); }

    public void addRequest(Reservation reservation) { requestQueue.offer(reservation); }

    public Reservation getNextRequest() { return requestQueue.poll(); }

    public boolean hasPendingRequests() { return !requestQueue.isEmpty(); }
}

public class uc5 {
    public static void main(String[] args) {

        BookingRequestQueue queue = new BookingRequestQueue();

        queue.addRequest(new Reservation("Alice", "Single"));
        queue.addRequest(new Reservation("Bob",   "Double"));
        queue.addRequest(new Reservation("Carol", "Suite"));
        queue.addRequest(new Reservation("David", "Single"));

        System.out.println("============================================================");
        System.out.println("       Book My Stay - Booking Request Queue (FIFO)");
        System.out.println("============================================================");
        System.out.println("Processing booking requests in order received:\n");

        int count = 1;
        while (queue.hasPendingRequests()) {
            Reservation r = queue.getNextRequest();
            System.out.println("Request #" + count++);
            System.out.println("  Guest Name : " + r.getGuestName());
            System.out.println("  Room Type  : " + r.getRoomType());
            System.out.println();
        }

        System.out.println("============================================================");
        System.out.println("All booking requests processed.");
        System.out.println("============================================================");
    }
}
