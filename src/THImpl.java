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
    public String[] bookInitial(String id, int pieces, String name) {
        String requestedType = "";
        int index = 0;
        String[] transactionResult = {"Unknown error; please contact the service provider, sorry for the inconvenience",
                "", ""};

        for(Seat_t type : seats) {
            if(type.getId().equals(id)) {
                requestedType = type.getId();
                if(type.getAvailable() >= pieces) {
                    bookings.add(new Booking_t(id, pieces, name));
                    type.updateAvailable(type.getAvailable() - pieces);
                    transactionResult[0] = "Successful booking for " + type.getPrettyName() + "; " + pieces + " seat(s)";
                } else if (type.getAvailable() > 0 ) {
                    transactionResult[1] = String.valueOf(index);
                    transactionResult[2] = String.valueOf(type.getAvailable());
                }
            }
            index++;
        }

        if(requestedType.isEmpty()) {
            transactionResult[0] = "Error: the given ID doesn't correspond to any seat type";
        }

        return transactionResult;
    }

    @Override
    public String bookInsufficientResponse(String id, int pieces, String name, String[] transactionResult) {
        bookings.add(new Booking_t(id, Integer.parseInt(transactionResult[1]), name));
        Seat_t type = seats[Integer.parseInt(transactionResult[1])];
        String transactionResponse = "Successful booking for " + type.getPrettyName() + "; "
                + type.getAvailable() + " seat(s)";
        type.updateAvailable(0);

        return transactionResponse;
    }
}

//transactionResult = "There aren't enough seats available, would you like to book the remaining " + type.getPrettyName() + " seat(s)?";
