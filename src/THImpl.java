import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

public class THImpl extends UnicastRemoteObject implements THInterface {
    private final Seat_t[] seats;

    protected THImpl(Seat_t[] seats) throws RemoteException {
        super();
        this.seats = seats;
    }

    @Override
    public StringBuilder listInfo() {
        StringBuilder info = new StringBuilder();
        for(Seat_t type : seats) {
            info.append(type.getAvailable())
                    .append(" θέσεις ")
                    .append(type.getPrettyName())
                    .append(" (κωδικός: ")
                    .append(type.getId())
                    .append(") ")
                    .append("- τιμή: ")
                    .append(type.getPricePerPiece())
                    .append("\n");
        }
        return info;
    }
}