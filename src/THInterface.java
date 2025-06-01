import java.rmi.Remote;
import java.rmi.RemoteException;

public interface THInterface extends Remote {
    public StringBuilder list() throws RemoteException;
    public String book(String id, int pieces, String name) throws RemoteException;
}


