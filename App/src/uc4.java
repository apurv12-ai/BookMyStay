import java.util.HashMap;
import java.util.Map;

abstract class Room4 {
    protected int numberOfBeds;
    protected int squareFeet;
    protected double pricePerNight;

    public Room4(int numberOfBeds, int squareFeet, double pricePerNight) {
        this.numberOfBeds = numberOfBeds;
        this.squareFeet = squareFeet;
        this.pricePerNight = pricePerNight;
    }

    public void displayRoomDetails() {
        System.out.println("  Beds        : " + numberOfBeds);
        System.out.println("  Square Feet : " + squareFeet);
        System.out.println("  Price/Night : $" + pricePerNight);
    }
}

class SingleRoom4 extends Room4 {
    public SingleRoom4() { super(1, 250, 1500.0); }
}

class DoubleRoom4 extends Room4 {
    public DoubleRoom4() { super(2, 400, 2500.0); }
}

class SuiteRoom4 extends Room4 {
    public SuiteRoom4() { super(3, 750, 5000.0); }
}

class RoomInventory4 {
    private Map<String, Integer> roomAvailability;

    public RoomInventory4() {
        roomAvailability = new HashMap<>();
        roomAvailability.put("Single", 5);
        roomAvailability.put("Double", 3);
        roomAvailability.put("Suite",  2);
    }

    public Map<String, Integer> getRoomAvailability() {
        return roomAvailability;
    }

    public void updateAvailability(String roomType, int count) {
        roomAvailability.put(roomType, count);
    }
}

class RoomSearchService {
    public void searchAvailableRooms(
            RoomInventory4 inventory,
            Room4 singleRoom,
            Room4 doubleRoom,
            Room4 suiteRoom) {

        Map<String, Integer> availability = inventory.getRoomAvailability();

        System.out.println("============================================================");
        System.out.println("         Book My Stay - Available Rooms");
        System.out.println("============================================================");

        if (availability.get("Single") > 0) {
            System.out.println("\n[Single Room] - AVAILABLE (" + availability.get("Single") + " rooms left)");
            singleRoom.displayRoomDetails();
        } else {
            System.out.println("\n[Single Room] - NOT AVAILABLE");
        }

        if (availability.get("Double") > 0) {
            System.out.println("\n[Double Room] - AVAILABLE (" + availability.get("Double") + " rooms left)");
            doubleRoom.displayRoomDetails();
        } else {
            System.out.println("\n[Double Room] - NOT AVAILABLE");
        }

        if (availability.get("Suite") > 0) {
            System.out.println("\n[Suite Room] - AVAILABLE (" + availability.get("Suite") + " rooms left)");
            suiteRoom.displayRoomDetails();
        } else {
            System.out.println("\n[Suite Room] - NOT AVAILABLE");
        }

        System.out.println("\n============================================================");
        System.out.println("Search complete.");
        System.out.println("============================================================");
    }
}

public class uc4 {
    public static void main(String[] args) {

        Room4 singleRoom = new SingleRoom4();
        Room4 doubleRoom = new DoubleRoom4();
        Room4 suiteRoom  = new SuiteRoom4();

        RoomInventory4 inventory = new RoomInventory4();

        inventory.updateAvailability("Suite", 0);

        RoomSearchService searchService = new RoomSearchService();
        searchService.searchAvailableRooms(inventory, singleRoom, doubleRoom, suiteRoom);
    }
}
