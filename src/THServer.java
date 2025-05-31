import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class THServer {
    public static void main(String[] args) {
        Seat_t[] seats = {
                new Seat_t("ΠΑ", "Πλατεία - Ζώνη Α", 100, 50.00),
                new Seat_t("ΠΒ", "Πλατεία - Ζώνη B", 200, 40.00),
                new Seat_t("ΠΓ", "Πλατεία - Ζώνη Γ", 300, 30.00),
                new Seat_t("ΚΕ", "Κεντρικός Εξώστης", 250, 35.00),
                new Seat_t("ΠΘ", "Πλαϊνά Θεωρεία", 50, 25.00)
        };

        try {
            THImpl obj = new THImpl(seats);
            Registry registry = LocateRegistry.createRegistry(2222);
            registry.rebind("THInterface", obj);
            System.out.println("Server's ready...");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
