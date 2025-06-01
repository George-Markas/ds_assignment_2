import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class THImpl extends UnicastRemoteObject implements THInterface {
    private final Seat_t[] seats;
    private final ArrayList<Booking_t> bookings;

    protected THImpl(Seat_t[] seats, ArrayList<Booking_t> bookings) throws RemoteException {
        super();
        this.seats = seats;
        this.bookings = bookings;
    }

    @Override
    public StringBuilder list() {
        StringBuilder info = new StringBuilder();
        for (Seat_t type : seats) {
            info.append(type.getAvailable())
                    .append(" Seats ")
                    .append(type.getPrettyName())
                    .append(" (ID: ")
                    .append(type.getId())
                    .append(") ")
                    .append("- price: ")
                    .append(type.getPricePerPiece())
                    .append("\n");
        }
        return info;
    }

    @Override
    public String book(String id, int pieces, String name) {
        String requestedType = null;
        String transactionResult = "Unknown error; please contact the service provider, sorry for the inconvenience";
        for (Seat_t type : seats) {
            if (type.getId().equals(id)) {
                requestedType = type.getId();
                if (type.getAvailable() >= pieces) {
                    bookings.add(new Booking_t(id, pieces, name));
                    type.updateAvailable(type.getAvailable() - pieces);
                    transactionResult = "Successful booking for " + type.getPrettyName() + " x" + pieces;
                } else {
                    transactionResult = "There aren't enough seats available satisfy the requested number";
                }
            }
        }

        if (requestedType == null) {
            transactionResult = "Error: the given ID doesn't correspond to any seat type";
        }

        return transactionResult;
    }
}