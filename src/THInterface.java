import java.rmi.Remote;
import java.rmi.RemoteException;

public interface THInterface extends Remote {
    // List available seats
    public StringBuilder listSeats() throws RemoteException;

    // Make bookings
    public String bookInitial(String id, int pieces, String name) throws RemoteException;
    public int getAvailableSeats(String id) throws RemoteException;
    public String bookInsufficientResponse(String id, int pieces, String name) throws RemoteException;

    // List bookings and their respective info
    public StringBuilder listGuests() throws RemoteException;

    // Cancel a booking
    public String cancelBooking(String id, int pieces, String name) throws RemoteException;
}


