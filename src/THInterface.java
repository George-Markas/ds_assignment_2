import java.rmi.Remote;
import java.rmi.RemoteException;

public interface THInterface extends Remote {
    // List available seats
    StringBuilder listSeats() throws RemoteException;

    // Make bookings
    String bookInitial(String id, int pieces, String name) throws RemoteException;
    int getAvailableSeats(String id) throws RemoteException;
    String bookInsufficientResponse(String id, int pieces, String name) throws RemoteException;

    // List bookings and their respective info
    StringBuilder listGuests() throws RemoteException;

    // Cancel a booking
    String cancelBooking(String id, int pieces, String name) throws RemoteException;

    // Add name to mailing list in case seats become available
    String addToMailingList(String id, String name) throws RemoteException;
//    void notify(String message) throws RemoteException;
}


