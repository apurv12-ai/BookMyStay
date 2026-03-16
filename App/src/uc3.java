import java.util.HashMap;
import java.util.Map;

abstract class Room3 {

    protected int numberOfBeds;
    protected int squareFeet;
    protected double pricePerNight;

    public Room3(int numberOfBeds, int squareFeet, double pricePerNight) {
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

class SingleRoom3 extends Room3 {
    public SingleRoom3() { super(1, 250, 1500.0); }
}

class DoubleRoom3 extends Room3 {
    public DoubleRoom3() { super(2, 400, 2500.0); }
}

class SuiteRoom3 extends Room3 {
    public SuiteRoom3() { super(3, 750, 5000.0); }
}

class RoomInventory {

    private Map<String, Integer> roomAvailability;

    public RoomInventory() {
        roomAvailability = new HashMap<>();
        initializeInventory();
    }

    private void initializeInventory() {
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

public class uc3 {

    public static void main(String[] args) {

        Room3 singleRoom = new SingleRoom3();
        Room3 doubleRoom = new DoubleRoom3();
        Room3 suiteRoom  = new SuiteRoom3();

        RoomInventory inventory = new RoomInventory();

        System.out.println("============================================================");
        System.out.println("       Book My Stay - Centralized Room Inventory");
        System.out.println("============================================================");

        System.out.println("\n[Single Room]");
        singleRoom.displayRoomDetails();
        System.out.println("  Available   : " + inventory.getRoomAvailability().get("Single"));

        System.out.println("\n[Double Room]");
        doubleRoom.displayRoomDetails();
        System.out.println("  Available   : " + inventory.getRoomAvailability().get("Double"));

        System.out.println("\n[Suite Room]");
        suiteRoom.displayRoomDetails();
        System.out.println("  Available   : " + inventory.getRoomAvailability().get("Suite"));

        System.out.println("\n------------------------------------------------------------");
        System.out.println("Updating Suite availability to 5...");
        inventory.updateAvailability("Suite", 5);
        System.out.println("Suite Available (after update) : " +
                inventory.getRoomAvailability().get("Suite"));

        System.out.println("\n============================================================");
        System.out.println("Inventory setup complete.");
        System.out.println("============================================================");
    }
}