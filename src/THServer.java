import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;

public class THServer {
    private static final Logger logger = Logger.getLogger("THServer");
    static {
        try {
            FileHandler fileHandler = new FileHandler("error.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Seat_t[] seats = {
                new Seat_t("PA", "Pit - Zone A", 100, 50.00),
                new Seat_t("PB", "Pit - Zone B", 200, 40.00),
                new Seat_t("PC", "Pit - Zone C", 300, 30.00),
                new Seat_t("CB", "Center Balcony", 250, 35.00),
                new Seat_t("SB", "Side Balconies", 50, 25.00)
        };

        ArrayList<Booking_t> bookings = new ArrayList<>();

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
