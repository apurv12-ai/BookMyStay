import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class AddOnService {
    private String serviceName;
    private double cost;

    public AddOnService(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() { return serviceName; }
    public double getCost() { return cost; }
}

class AddOnServiceManager {
    private Map<String, List<AddOnService>> servicesByReservation;

    public AddOnServiceManager() { servicesByReservation = new HashMap<>(); }

    public void addService(String reservationId, AddOnService service) {
        servicesByReservation.computeIfAbsent(reservationId, k -> new ArrayList<>()).add(service);
    }

    public double calculateTotalServiceCost(String reservationId) {
        List<AddOnService> services = servicesByReservation.getOrDefault(reservationId, new ArrayList<>());
        double total = 0;
        for (AddOnService s : services) total += s.getCost();
        return total;
    }

    public List<AddOnService> getServices(String reservationId) {
        return servicesByReservation.getOrDefault(reservationId, new ArrayList<>());
    }
}

public class uc7 {
    public static void main(String[] args) {

        AddOnServiceManager manager = new AddOnServiceManager();

        // Available add-on services
        AddOnService breakfast     = new AddOnService("Breakfast",      500.0);
        AddOnService spa           = new AddOnService("Spa",           1500.0);
        AddOnService airportPickup = new AddOnService("Airport Pickup", 800.0);
        AddOnService laundry       = new AddOnService("Laundry",        300.0);

        // Attach services to reservations
        manager.addService("S-101", breakfast);
        manager.addService("S-101", spa);
        manager.addService("D-101", airportPickup);
        manager.addService("D-101", breakfast);
        manager.addService("D-101", laundry);
        manager.addService("S-102", spa);

        System.out.println("============================================================");
        System.out.println("       Book My Stay - Add-On Service Selection");
        System.out.println("============================================================\n");

        String[] reservations = {"S-101", "D-101", "S-102"};

        for (String resId : reservations) {
            System.out.println("Reservation ID : " + resId);
            System.out.println("Selected Add-On Services:");

            List<AddOnService> services = manager.getServices(resId);
            if (services.isEmpty()) {
                System.out.println("  No services selected.");
            } else {
                for (AddOnService s : services) {
                    System.out.printf("  %-20s : Rs. %.2f%n", s.getServiceName(), s.getCost());
                }
            }

            System.out.printf("Total Service Cost     : Rs. %.2f%n", manager.calculateTotalServiceCost(resId));
            System.out.println("------------------------------------------------------------");
        }

        System.out.println("\n============================================================");
        System.out.println("Add-on service selection complete.");
        System.out.println("============================================================");
    }
}
