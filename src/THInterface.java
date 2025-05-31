import java.rmi.Remote;
import java.rmi.RemoteException;

public interface THInterface extends Remote {
    public StringBuilder listInfo() throws RemoteException;
}


