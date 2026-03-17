import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

class Reservation11 {
    private String guestName;
    private String roomType;

    public Reservation11(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType  = roomType;
    }

    public String getGuestName() { return guestName; }
    public String getRoomType()  { return roomType; }
}

class BookingRequestQueue11 {
    private Queue<Reservation11> requestQueue;

    public BookingRequestQueue11() { requestQueue = new LinkedList<>(); }

    public void addRequest(Reservation11 reservation) { requestQueue.offer(reservation); }

    public Reservation11 getNextRequest() { return requestQueue.poll(); }

    public boolean hasPendingRequests() { return !requestQueue.isEmpty(); }
}

class RoomInventory11 {
    private Map<String, Integer> roomAvailability;

    public RoomInventory11() {
        roomAvailability = new HashMap<>();
        roomAvailability.put("Single", 3);
        roomAvailability.put("Double", 2);
        roomAvailability.put("Suite",  1);
    }

    public Map<String, Integer> getRoomAvailability() { return roomAvailability; }

    public void updateAvailability(String roomType, int count) {
        roomAvailability.put(roomType, count);
    }
}

class RoomAllocationService11 {
    private Map<String, Integer> roomCounters = new HashMap<>();

    public void allocateRoom(Reservation11 reservation, RoomInventory11 inventory) {
        String roomType  = reservation.getRoomType();
        String guestName = reservation.getGuestName();

        Map<String, Integer> availability = inventory.getRoomAvailability();

        if (!availability.containsKey(roomType) || availability.get(roomType) <= 0) {
            System.out.println("  [FAILED]  " + Thread.currentThread().getName() +
                    " -> No rooms available for " + guestName + " (" + roomType + ")");
            return;
        }

        int count  = roomCounters.getOrDefault(roomType, 100) + 1;
        roomCounters.put(roomType, count);
        String roomId = roomType.substring(0, 1).toUpperCase() + "-" + count;

        inventory.updateAvailability(roomType, availability.get(roomType) - 1);

        System.out.println("  [CONFIRMED] " + Thread.currentThread().getName() +
                " -> Guest: " + guestName +
                " | Room: " + roomType +
                " | ID: " + roomId +
                " | Remaining: " + inventory.getRoomAvailability().get(roomType));
    }
}

class ConcurrentBookingProcessor implements Runnable {
    private BookingRequestQueue11    bookingQueue;
    private RoomInventory11          inventory;
    private RoomAllocationService11  allocationService;

    public ConcurrentBookingProcessor(
            BookingRequestQueue11   bookingQueue,
            RoomInventory11         inventory,
            RoomAllocationService11 allocationService) {
        this.bookingQueue      = bookingQueue;
        this.inventory         = inventory;
        this.allocationService = allocationService;
    }

    @Override
    public void run() {
        while (true) {
            Reservation11 reservation;

            synchronized (bookingQueue) {
                if (!bookingQueue.hasPendingRequests()) break;
                reservation = bookingQueue.getNextRequest();
            }

            synchronized (inventory) {
                allocationService.allocateRoom(reservation, inventory);
            }
        }
    }
}

public class uc11 {
    public static void main(String[] args) {

        BookingRequestQueue11   bookingQueue      = new BookingRequestQueue11();
        RoomInventory11         inventory         = new RoomInventory11();
        RoomAllocationService11 allocationService = new RoomAllocationService11();

        bookingQueue.addRequest(new Reservation11("Alice",  "Single"));
        bookingQueue.addRequest(new Reservation11("Bob",    "Double"));
        bookingQueue.addRequest(new Reservation11("Carol",  "Suite"));
        bookingQueue.addRequest(new Reservation11("David",  "Single"));
        bookingQueue.addRequest(new Reservation11("Eve",    "Double"));
        bookingQueue.addRequest(new Reservation11("Frank",  "Single"));
        bookingQueue.addRequest(new Reservation11("Grace",  "Suite"));

        System.out.println("============================================================");
        System.out.println("     Book My Stay - Concurrent Booking Simulation");
        System.out.println("============================================================");
        System.out.println("Two threads processing booking queue simultaneously:\n");

        Thread t1 = new Thread(new ConcurrentBookingProcessor(bookingQueue, inventory, allocationService));
        Thread t2 = new Thread(new ConcurrentBookingProcessor(bookingQueue, inventory, allocationService));

        t1.setName("Thread-1");
        t2.setName("Thread-2");

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            System.out.println("Thread execution interrupted.");
        }

        System.out.println("\n------------------------------------------------------------");
        System.out.println("Final Room Availability:");
        for (Map.Entry<String, Integer> e : inventory.getRoomAvailability().entrySet()) {
            System.out.println("  " + e.getKey() + " : " + e.getValue() + " remaining");
        }

        System.out.println("\n============================================================");
        System.out.println("Concurrent booking simulation complete.");
        System.out.println("============================================================");
    }
}
