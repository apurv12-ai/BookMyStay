import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

class Reservation6 {
    private String guestName;
    private String roomType;

    public Reservation6(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }
}

class BookingRequestQueue6 {
    private Queue<Reservation6> requestQueue;

    public BookingRequestQueue6() { requestQueue = new LinkedList<>(); }

    public void addRequest(Reservation6 reservation) { requestQueue.offer(reservation); }

    public Reservation6 getNextRequest() { return requestQueue.poll(); }

    public boolean hasPendingRequests() { return !requestQueue.isEmpty(); }
}

class RoomInventory6 {
    private Map<String, Integer> roomAvailability;

    public RoomInventory6() {
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

class RoomAllocationService {
    private Set<String> allocatedRoomIds;
    private Map<String, Set<String>> assignedRoomsByType;
    private Map<String, Integer> roomCounters;

    public RoomAllocationService() {
        allocatedRoomIds    = new HashSet<>();
        assignedRoomsByType = new HashMap<>();
        roomCounters        = new HashMap<>();
    }

    public void allocateRoom(Reservation6 reservation, RoomInventory6 inventory) {
        String roomType  = reservation.getRoomType();
        String guestName = reservation.getGuestName();

        Map<String, Integer> availability = inventory.getRoomAvailability();

        if (!availability.containsKey(roomType) || availability.get(roomType) <= 0) {
            System.out.println("  [FAILED]  No rooms available for " + guestName +
                    " (" + roomType + ")");
            return;
        }

        String roomId = generateRoomId(roomType);
        allocatedRoomIds.add(roomId);
        assignedRoomsByType.computeIfAbsent(roomType, k -> new HashSet<>()).add(roomId);
        inventory.updateAvailability(roomType, availability.get(roomType) - 1);

        System.out.println("  [CONFIRMED] Guest     : " + guestName);
        System.out.println("              Room Type  : " + roomType);
        System.out.println("              Room ID    : " + roomId);
        System.out.println("              Remaining  : " + inventory.getRoomAvailability().get(roomType));
        System.out.println();
    }

    private String generateRoomId(String roomType) {
        int count = roomCounters.getOrDefault(roomType, 100) + 1;
        roomCounters.put(roomType, count);
        return roomType.substring(0, 1).toUpperCase() + "-" + count;
    }
}

public class uc6 {
    public static void main(String[] args) {

        BookingRequestQueue6 queue = new BookingRequestQueue6();
        queue.addRequest(new Reservation6("Alice", "Single"));
        queue.addRequest(new Reservation6("Bob",   "Double"));
        queue.addRequest(new Reservation6("Carol", "Suite"));
        queue.addRequest(new Reservation6("David", "Single"));
        queue.addRequest(new Reservation6("Eve",   "Suite"));

        RoomInventory6 inventory = new RoomInventory6();
        RoomAllocationService allocationService = new RoomAllocationService();

        System.out.println("============================================================");
        System.out.println("   Book My Stay - Reservation Confirmation & Room Allocation");
        System.out.println("============================================================\n");

        while (queue.hasPendingRequests()) {
            Reservation6 reservation = queue.getNextRequest();
            allocationService.allocateRoom(reservation, inventory);
        }

        System.out.println("============================================================");
        System.out.println("Final Room Availability:");
        for (Map.Entry<String, Integer> entry : inventory.getRoomAvailability().entrySet()) {
            System.out.println("  " + entry.getKey() + " : " + entry.getValue() + " remaining");
        }
        System.out.println("============================================================");
        System.out.println("All reservations processed.");
        System.out.println("============================================================");
    }
}
