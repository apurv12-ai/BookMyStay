import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

class RoomInventory12 {
    private Map<String, Integer> roomAvailability;

    public RoomInventory12() {
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

class FilePersistenceService {

    public void saveInventory(RoomInventory12 inventory, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Map.Entry<String, Integer> entry : inventory.getRoomAvailability().entrySet()) {
                writer.write(entry.getKey() + "=" + entry.getValue());
                writer.newLine();
            }
            System.out.println("  [SAVED] Inventory saved to: " + filePath);
        } catch (IOException e) {
            System.out.println("  [ERROR] Failed to save inventory: " + e.getMessage());
        }
    }

    public void loadInventory(RoomInventory12 inventory, String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=");
                if (parts.length == 2) {
                    String roomType = parts[0].trim();
                    int count       = Integer.parseInt(parts[1].trim());
                    inventory.updateAvailability(roomType, count);
                }
            }
            System.out.println("  [LOADED] Inventory restored from: " + filePath);
        } catch (IOException e) {
            System.out.println("  [ERROR] Failed to load inventory: " + e.getMessage());
        }
    }
}

public class uc12 {
    public static void main(String[] args) {

        String filePath = "inventory.txt";

        FilePersistenceService persistence = new FilePersistenceService();
        RoomInventory12        inventory   = new RoomInventory12();

        System.out.println("============================================================");
        System.out.println("   Book My Stay - Data Persistence & System Recovery");
        System.out.println("============================================================");

        // Step 1: Show initial inventory
        System.out.println("\n[Step 1] Initial Inventory:");
        for (Map.Entry<String, Integer> e : inventory.getRoomAvailability().entrySet()) {
            System.out.println("  " + e.getKey() + " : " + e.getValue());
        }

        // Step 2: Simulate some bookings
        System.out.println("\n[Step 2] Simulating Bookings...");
        inventory.updateAvailability("Single", 1);
        inventory.updateAvailability("Double", 0);
        inventory.updateAvailability("Suite",  0);
        System.out.println("  Bookings applied. Updated inventory:");
        for (Map.Entry<String, Integer> e : inventory.getRoomAvailability().entrySet()) {
            System.out.println("  " + e.getKey() + " : " + e.getValue());
        }

        // Step 3: Save inventory to file
        System.out.println("\n[Step 3] Saving Inventory to File:");
        persistence.saveInventory(inventory, filePath);

        // Step 4: Simulate system restart - reset inventory to default
        System.out.println("\n[Step 4] Simulating System Restart...");
        RoomInventory12 recoveredInventory = new RoomInventory12();
        System.out.println("  Inventory reset to defaults:");
        for (Map.Entry<String, Integer> e : recoveredInventory.getRoomAvailability().entrySet()) {
            System.out.println("  " + e.getKey() + " : " + e.getValue());
        }

        // Step 5: Load inventory from file
        System.out.println("\n[Step 5] Restoring Inventory from File:");
        persistence.loadInventory(recoveredInventory, filePath);
        System.out.println("  Recovered inventory:");
        for (Map.Entry<String, Integer> e : recoveredInventory.getRoomAvailability().entrySet()) {
            System.out.println("  " + e.getKey() + " : " + e.getValue());
        }

        System.out.println("\n============================================================");
        System.out.println("Data persistence and recovery complete.");
        System.out.println("============================================================");
    }
}
