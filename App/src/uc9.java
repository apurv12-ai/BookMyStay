import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) { super(message); }
}

class Reservation9 {
    private String guestName;
    private String roomType;

    public Reservation9(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType  = roomType;
    }

    public String getGuestName() { return guestName; }
    public String getRoomType()  { return roomType; }
}

class RoomInventory9 {
    private Map<String, Integer> roomAvailability;

    public RoomInventory9() {
        roomAvailability = new HashMap<>();
        roomAvailability.put("Single", 3);
        roomAvailability.put("Double", 2);
        roomAvailability.put("Suite",  1);
    }

    public Map<String, Integer> getRoomAvailability() { return roomAvailability; }
}

class BookingRequestQueue9 {
    private Queue<Reservation9> requestQueue;

    public BookingRequestQueue9() { requestQueue = new LinkedList<>(); }

    public void addRequest(Reservation9 reservation) { requestQueue.offer(reservation); }

    public Reservation9 getNextRequest() { return requestQueue.poll(); }

    public boolean hasPendingRequests() { return !requestQueue.isEmpty(); }
}

class ReservationValidator {
    public void validate(String guestName, String roomType, RoomInventory9 inventory)
            throws InvalidBookingException {

        if (guestName == null || guestName.trim().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty.");
        }

        if (roomType == null || roomType.trim().isEmpty()) {
            throw new InvalidBookingException("Room type cannot be empty.");
        }

        Map<String, Integer> availability = inventory.getRoomAvailability();

        if (!availability.containsKey(roomType)) {
            throw new InvalidBookingException("Invalid room type: '" + roomType +
                    "'. Valid types are: Single, Double, Suite.");
        }

        if (availability.get(roomType) <= 0) {
            throw new InvalidBookingException("No rooms available for type: " + roomType);
        }
    }
}

public class uc9 {
    public static void main(String[] args) {

        System.out.println("============================================================");
        System.out.println("       Book My Stay - Booking Validation");
        System.out.println("============================================================");

        RoomInventory9        inventory   = new RoomInventory9();
        ReservationValidator  validator   = new ReservationValidator();
        BookingRequestQueue9  bookingQueue = new BookingRequestQueue9();

        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Enter Guest Name  : ");
            String guestName = scanner.nextLine();

            System.out.print("Enter Room Type (Single / Double / Suite) : ");
            String roomType = scanner.nextLine();

            // Validate input
            validator.validate(guestName, roomType, inventory);

            // If valid, add to queue
            Reservation9 reservation = new Reservation9(guestName, roomType);
            bookingQueue.addRequest(reservation);

            System.out.println("\n------------------------------------------------------------");
            System.out.println("[SUCCESS] Booking request accepted!");
            System.out.println("  Guest Name : " + guestName);
            System.out.println("  Room Type  : " + roomType);
            System.out.println("  Status     : Added to booking queue.");

        } catch (InvalidBookingException e) {
            System.out.println("\n------------------------------------------------------------");
            System.out.println("Booking failed: " + e.getMessage());

        } finally {
            scanner.close();
        }

        System.out.println("\n============================================================");
        System.out.println("Validation process complete.");
        System.out.println("============================================================");
    }
}
