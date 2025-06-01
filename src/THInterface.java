import java.rmi.Remote;
import java.rmi.RemoteException;

public interface THInterface extends Remote {
    // List available seats
    public StringBuilder listSeats() throws RemoteException;

    // Make a booking
    public String bookInitial(String id, int pieces, String name) throws RemoteException;
    public int getAvailableSeats(String id) throws RemoteException;
    public String bookInsufficientResponse(String id, int pieces, String name) throws RemoteException;

    // List booking entries
    public StringBuilder listGuests() throws RemoteException;
}


