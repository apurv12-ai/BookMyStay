
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

class RoomInventory10 {
    private Map<String, Integer> roomAvailability;

    public RoomInventory10() {
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

class CancellationService {
    private Stack<String> releasedRoomIds;
    private Map<String, String> reservationRoomTypeMap;

    public CancellationService() {
        releasedRoomIds       = new Stack<>();
        reservationRoomTypeMap = new HashMap<>();
    }

    public void registerBooking(String reservationId, String roomType) {
        reservationRoomTypeMap.put(reservationId, roomType);
        System.out.println("  [REGISTERED] Reservation ID : " + reservationId +
                " | Room Type : " + roomType);
    }

    public void cancelBooking(String reservationId, RoomInventory10 inventory) {
        if (!reservationRoomTypeMap.containsKey(reservationId)) {
            System.out.println("  [ERROR] Reservation ID not found: " + reservationId);
            return;
        }

        String roomType = reservationRoomTypeMap.get(reservationId);
        int current = inventory.getRoomAvailability().getOrDefault(roomType, 0);
        inventory.updateAvailability(roomType, current + 1);

        releasedRoomIds.push(reservationId);
        reservationRoomTypeMap.remove(reservationId);

        System.out.println("  [CANCELLED] Reservation ID : " + reservationId +
                " | Room Type : " + roomType +
                " | Inventory Restored : " + (current + 1));
    }

    public void showRollbackHistory() {
        System.out.println("\n  Rollback History (Most Recent First):");
        if (releasedRoomIds.isEmpty()) {
            System.out.println("  No cancellations recorded.");
            return;
        }
        Stack<String> temp = new Stack<>();
        temp.addAll(releasedRoomIds);
        int count = 1;
        while (!temp.isEmpty()) {
            System.out.println("  " + count++ + ". Reservation ID : " + temp.pop());
        }
    }
}

public class uc10 {
    public static void main(String[] args) {

        RoomInventory10    inventory    = new RoomInventory10();
        CancellationService cancellation = new CancellationService();

        System.out.println("============================================================");
        System.out.println("   Book My Stay - Booking Cancellation & Inventory Rollback");
        System.out.println("============================================================");

        // Register confirmed bookings
        System.out.println("\n[Step 1] Registering Confirmed Bookings:");
        cancellation.registerBooking("S-101", "Single");
        cancellation.registerBooking("D-101", "Double");
        cancellation.registerBooking("S-102", "Single");
        cancellation.registerBooking("SU-101","Suite");

        // Show inventory before cancellation
        System.out.println("\n[Step 2] Inventory Before Cancellation:");
        for (Map.Entry<String, Integer> e : inventory.getRoomAvailability().entrySet()) {
            System.out.println("  " + e.getKey() + " : " + e.getValue() + " available");
        }

        // Cancel bookings
        System.out.println("\n[Step 3] Cancelling Bookings:");
        cancellation.cancelBooking("D-101",  inventory);
        cancellation.cancelBooking("S-101",  inventory);
        cancellation.cancelBooking("XYZ-999",inventory); // Invalid ID test

        // Show inventory after cancellation
        System.out.println("\n[Step 4] Inventory After Cancellation:");
        for (Map.Entry<String, Integer> e : inventory.getRoomAvailability().entrySet()) {
            System.out.println("  " + e.getKey() + " : " + e.getValue() + " available");
        }

        // Show rollback history
        System.out.println("\n[Step 5] Rollback History:");
        cancellation.showRollbackHistory();

        System.out.println("\n============================================================");
        System.out.println("Booking cancellation process complete.");
        System.out.println("============================================================");
    }
}

