import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;
import java.util.Hashtable;

public class THServer {
    private static final Logger logger = Logger.getLogger("THServer");
    static {
        try {
            FileHandler fileHandler = new FileHandler("server.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Hashtable<String, Seat_t> seats = new Hashtable<>();
        seats.put("PA", new Seat_t("Pit - Zone A", 100, 50.00));
        seats.put("PB", new Seat_t("Pit - Zone B", 200, 40.00));
        seats.put("PC", new Seat_t("Pit - Zone C", 300, 30.00));
        seats.put("CB", new Seat_t("Center Balcony", 250, 35.00));
        seats.put("SB", new Seat_t("Side Balconies", 50, 25.00));

        Hashtable<String, Booking_t> bookings = new Hashtable<>();

        // Debug entries to not have to manually book from the client side
        bookings.put("Fritz Schwarz", new Booking_t("PB", 4));
        bookings.put("Hans Zimmer", new Booking_t("PA", 2));
        bookings.put("Karl Weiss", new Booking_t("SB", 1));

        try {
            THImpl obj = new THImpl(seats, bookings);
            Registry registry = LocateRegistry.createRegistry(2222);
            registry.rebind("THInterface", obj);
            System.out.println("Server's bootstrapped!");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error has occurred", e);
        }
    }
}
