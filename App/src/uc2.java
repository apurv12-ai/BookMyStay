/**
 * ============================================================
 * MAIN CLASS - UseCase2RoomInitialization
 * ============================================================
 * Use Case 2: Basic Room Types & Static Availability
 * @version 2.1
 */

// ============================================================
// ABSTRACT CLASS - Room
// ============================================================
abstract class Room {

    protected int numberOfBeds;
    protected int squareFeet;
    protected double pricePerNight;

    public Room(int numberOfBeds, int squareFeet, double pricePerNight) {
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

// ============================================================
// CLASS - SingleRoom
// ============================================================
class SingleRoom extends Room {
    public SingleRoom() {
        super(1, 250, 1500.0);
    }
}

// ============================================================
// CLASS - DoubleRoom
// ============================================================
class DoubleRoom extends Room {
    public DoubleRoom() {
        super(2, 400, 2500.0);
    }
}

// ============================================================
// CLASS - SuiteRoom
// ============================================================
class SuiteRoom extends Room {
    public SuiteRoom() {
        super(3, 750, 5000.0);
    }
}

// ============================================================
// MAIN CLASS
// ============================================================
public class uc2 {

    public static void main(String[] args) {

        // Create room objects using polymorphism
        Room singleRoom = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suiteRoom  = new SuiteRoom();

        // Static availability variables
        boolean isSingleAvailable = true;
        boolean isDoubleAvailable = true;
        boolean isSuiteAvailable  = false;

        // Display
        System.out.println("============================================================");
        System.out.println("       Book My Stay - Room Inventory Overview");
        System.out.println("============================================================");

        System.out.println("\n[Single Room]");
        singleRoom.displayRoomDetails();
        System.out.println("  Available   : " + isSingleAvailable);

        System.out.println("\n[Double Room]");
        doubleRoom.displayRoomDetails();
        System.out.println("  Available   : " + isDoubleAvailable);

        System.out.println("\n[Suite Room]");
        suiteRoom.displayRoomDetails();
        System.out.println("  Available   : " + isSuiteAvailable);

        System.out.println("\n============================================================");
        System.out.println("Room initialization complete.");
        System.out.println("============================================================");
    }
}
